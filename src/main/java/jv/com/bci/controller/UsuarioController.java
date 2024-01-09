package jv.com.bci.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jv.com.bci.dto.UsuarioDTO;
import jv.com.bci.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Api(value = "Usuarios Controller", produces = MediaType.APPLICATION_JSON_VALUE, tags = {
        "BCI users Management" })
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final String EMAIL_FORMATO_INCORRECTO= "El email tiene formato incorrecto( ej.correcto: (aaaaaaa@dominio.cl))";
    private final String EMAIL_YA_EXISTENTE= "El correo ya esta registrado!";
    private final String PASSWORD_NO_CUMPLE_FORMATO= "El password ingresado no cumple formato requerido";

    private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$";

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(PASSWORD_REGEX);

    @Autowired
    private IUsuarioService usuarioService;

    @ApiOperation(value = "Creacion de usuarios via Json")
    @PostMapping()
    public ResponseEntity<?> crear( @Valid @RequestBody  UsuarioDTO usuarioDTO, BindingResult result) {
        ResponseEntity<Map<String, String>> errorValidador = validadores(usuarioDTO, result);
        if (errorValidador != null) return errorValidador;
        if (usuarioService.obtenerPorEmail(usuarioDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("mensaje", EMAIL_YA_EXISTENTE));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(usuarioDTO));
    }

    @ApiOperation(value = "Obtencion de usuario por Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<UsuarioDTO> o = usuarioService.obtenerPorId(id);//service.porId(id);
        if (o.isPresent()) {
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "actualizacion de Usuario con su ID y JSON de actualizacion(solo name, email o password)")
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult result, @PathVariable Long id) {
        ResponseEntity<Map<String, String>> errorValidador = validadores(usuarioDTO, result);
        if (errorValidador != null) return errorValidador;
        Optional<UsuarioDTO> o = usuarioService.obtenerPorId(id);
        if (o.isPresent()) {
            if ( usuarioService.obtenerPorEmail(usuarioDTO.getEmail()).isPresent() &
                    !usuarioDTO.getEmail().equals(o.get().getEmail() )) {
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("mensaje", EMAIL_YA_EXISTENTE));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.update(usuarioDTO,id));
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Eliminacion Fisica de usuario")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "usuario eliminado exitosamente")})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<UsuarioDTO> o = usuarioService.obtenerPorId(id);
        if (o.isPresent()) {
            usuarioService.eliminar(id);
            return new ResponseEntity<String>("usuario eliminado exitosamente",HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    private boolean matchesEmail(String email) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*."+"cl";
        Pattern pattern = Pattern.compile(regex);
        return  pattern.matcher(email).matches();
    }

    private ResponseEntity<Map<String, String>> validarBind(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo ingresado " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

    private ResponseEntity<Map<String, String>> validadores(UsuarioDTO usuarioDTO, BindingResult result) {
        if (result.hasErrors()) {
            return validarBind(result);
        }
        if(!matchesEmail(usuarioDTO.getEmail())){
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("mensaje", EMAIL_FORMATO_INCORRECTO));
        }
        if(!PASSWORD_PATTERN.matcher(usuarioDTO.getPassword()).matches()){
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("mensaje", PASSWORD_NO_CUMPLE_FORMATO ));
        }
        return null;
    }

}
