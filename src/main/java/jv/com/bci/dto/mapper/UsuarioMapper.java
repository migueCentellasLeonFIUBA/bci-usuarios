package jv.com.bci.dto.mapper;

import jv.com.bci.dto.PhoneDTO;
import jv.com.bci.dto.UsuarioDTO;
import jv.com.bci.dto.UsuarioResponseDTO;
import jv.com.bci.entity.Usuario;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class UsuarioMapper {

    public Usuario fromDto(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setName(usuarioDTO.getName());
        usuario.setPassword(usuarioDTO.getPassword());
        return usuario;
    }

    public UsuarioDTO toDto(Usuario usuario, List<PhoneDTO> phoneList){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setName(usuario.getName());
        usuarioDTO.setPassword(usuario.getPassword());
        usuarioDTO.setPhoneList(phoneList);
        return usuarioDTO;
    }

    public UsuarioResponseDTO toResponseDto(Usuario usuario){
        UsuarioResponseDTO usuarioDTO = new UsuarioResponseDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setCreated(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(usuario.getCreateAt()));
        usuarioDTO.setModified(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(usuario.getUpdateAt()));
        usuarioDTO.setLastLogin(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(usuario.getLastLogin()));
        usuarioDTO.setActive(usuario.isActive());
        usuarioDTO.setToken(usuario.getToken());

        return usuarioDTO;
    }

    public UsuarioDTO toResponseIUpdateDto(Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setName(usuario.getName());
        usuarioDTO.setPassword(usuario.getPassword());
        usuarioDTO.setEmail(usuario.getEmail());

        return usuarioDTO;
    }
}
