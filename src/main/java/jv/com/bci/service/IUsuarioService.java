package jv.com.bci.service;

import jv.com.bci.dto.UsuarioDTO;
import jv.com.bci.dto.UsuarioResponseDTO;
import jv.com.bci.entity.Usuario;

import java.util.Optional;

public interface IUsuarioService {

    UsuarioResponseDTO crearUsuario(UsuarioDTO usuario);

    Optional<UsuarioDTO> obtenerPorId(Long id);

    Optional<Usuario> obtenerPorEmail(String email);

    public UsuarioDTO update(UsuarioDTO usuarioDTO, Long idUsuario);

    void eliminar(Long id);
}
