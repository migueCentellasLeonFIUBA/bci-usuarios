package jv.com.bci.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jv.com.bci.dto.PhoneDTO;
import jv.com.bci.dto.UsuarioDTO;
import jv.com.bci.dto.UsuarioResponseDTO;
import jv.com.bci.dto.mapper.PhoneMapper;
import jv.com.bci.dto.mapper.UsuarioMapper;
import jv.com.bci.entity.Phone;
import jv.com.bci.entity.PhoneUsuario;
import jv.com.bci.entity.Usuario;
import jv.com.bci.repository.PhoneRepository;
import jv.com.bci.repository.UsuarioRepository;
import jv.com.bci.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private PhoneMapper phoneMapper;

    @Override
    @Transactional
    public UsuarioResponseDTO crearUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.fromDto(usuarioDTO);
        List<PhoneDTO> phoneDtoList = usuarioDTO.getPhoneList().stream().collect(Collectors.toList());
        List<Phone> phoneList = phoneDtoList.stream().map((p)->phoneMapper.fromDto(p)).collect(Collectors.toList());
        phoneList.stream().forEach((p)->{phoneRepository.save(p);
                            PhoneUsuario phoneUsuario = new PhoneUsuario();
                            phoneUsuario.setPhoneId(p.getId());
                            usuario.addPhoneUsuario(phoneUsuario);} );
        String token = getJWTToken(usuarioDTO.getEmail());
        usuario.setToken(token);
        usuarioRepository.save(usuario);
        UsuarioResponseDTO usuarioResponseDTO = usuarioMapper.toResponseDto(usuario);
        return usuarioResponseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> obtenerPorId(Long id) {
        Optional<Usuario> u = usuarioRepository.findById(id);
        if (u.isPresent()) {
            Usuario usuario = u.get();
            if (!usuario.getPhoneUsuarios().isEmpty()) {
                List<Long> ids = usuario.getPhoneUsuarios().stream().map(pu -> pu.getPhoneId())
                        .collect(Collectors.toList());
                List<Phone> phonesList = obtenerPhonesPorUsuario(ids);
                usuario.setPhones(phonesList);
                List<PhoneDTO> phoneList = phonesList.stream().map((p)->phoneMapper.toDto(p)).collect(Collectors.toList());
                return Optional.of(usuarioMapper.toDto(usuario,phoneList));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public UsuarioDTO update(UsuarioDTO usuarioDTO, Long idUsuarioDB) {
        Usuario u = usuarioRepository.findById(idUsuarioDB).get();
        Usuario usuario = new Usuario();
        usuario.setId(u.getId());
        usuario.setName(usuarioDTO.getName() != null ? usuarioDTO.getName() : usuario.getName());
        usuario.setPassword(usuarioDTO.getPassword() != null ? usuarioDTO.getPassword() : usuario.getPassword());
        usuario.setEmail(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : usuario.getEmail());
        usuario.setUpdateAt(new Date());
        usuario.setPhoneUsuarios(u.getPhoneUsuarios());
        usuario.setCreateAt(u.getCreateAt());
        usuario.setLastLogin(u.getLastLogin());
        usuario.setActive(u.isActive());
        usuarioRepository.save(usuario);
        return usuarioMapper.toResponseIUpdateDto(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    private List<Phone> obtenerPhonesPorUsuario(Iterable<Long> ids) {
        return (List<Phone>) phoneRepository.findAllById(ids);
    }


    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";

        String token = Jwts
                .builder()
                .setId("bciJWT")
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return token;
    }

}
