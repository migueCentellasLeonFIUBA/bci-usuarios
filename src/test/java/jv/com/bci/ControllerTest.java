package jv.com.bci;

import jv.com.bci.dto.PhoneDTO;
import jv.com.bci.dto.UsuarioDTO;
import jv.com.bci.service.IUsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUsuarioService usuarioService;

    @Test
    void testConsultarUnUsuarioPorId() throws Exception{
        //given
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        List<PhoneDTO> listPhones = new ArrayList<>();
        PhoneDTO phone = new PhoneDTO();
        phone.builder().number("999").citycode("333").countrycode("87");
        listPhones.add(phone);
        usuarioDTO.builder().name("Migue").email("m@bci.cl").password("Java#@#8").phoneList(listPhones);
        given(usuarioService.obtenerPorId(1L)).willReturn(Optional.of(usuarioDTO));

        //when
        ResultActions response = mockMvc.perform(get("/api/usuarios/{id}",1L));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name",is(usuarioDTO.getName())))
                .andExpect(jsonPath("$.email",is(usuarioDTO.getEmail())));
    }

    @Test
    void testIntentarObtenerUsuarioNoExistente() throws Exception{

        //given
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        List<PhoneDTO> listPhones = new ArrayList<>();
        PhoneDTO phone = new PhoneDTO();
        phone.builder().number("999").citycode("333").countrycode("87");
        listPhones.add(phone);
        usuarioDTO.builder().name("Migue").email("m@bci.cl").password("Java#@#8").phoneList(listPhones);
        given(usuarioService.obtenerPorId(1L)).willReturn(Optional.empty());

        //when
        ResultActions response = mockMvc.perform(get("/api/usuarios/{id}",1L));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());


    }

    @Test
    void testEliminarUsuario() throws Exception{
        //given
        var usuarioId = 1L;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        List<PhoneDTO> listPhones = new ArrayList<>();
        PhoneDTO phone = new PhoneDTO();
        phone.builder().number("999").citycode("333").countrycode("87");
        listPhones.add(phone);
        usuarioDTO.builder().name("Migue").email("m@bci.cl").password("Java#@#8").phoneList(listPhones);
        given(usuarioService.obtenerPorId(1L)).willReturn(Optional.of(usuarioDTO));

        willDoNothing().given(usuarioService).eliminar(usuarioId);

        //when
        ResultActions response = mockMvc.perform(delete("/api/usuarios/{id}",usuarioId));

        //then
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testIntentoCrearUsuarioConFormatoIncorrecto_DaErrorBAdRequest() throws Exception{
        String password_erroneo="ava#@#8";
        //given
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        List<PhoneDTO> listPhones = new ArrayList<>();
        PhoneDTO phone = new PhoneDTO();
        phone.builder().number("999").citycode("333").countrycode("87");
        listPhones.add(phone);
        usuarioDTO.builder().name("Migue").email("m@bci.cl").password(password_erroneo).phoneList(listPhones);


        //when
        ResultActions response = mockMvc.perform(post("/api/usuarios/"));

        //then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void testIntentoCrearUsuarioConEmailIncorrecto_DaErrorBAdRequest() throws Exception{
        String emailIncorrecto="mbci.cl";
        //given
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        List<PhoneDTO> listPhones = new ArrayList<>();
        PhoneDTO phone = new PhoneDTO();
        phone.builder().number("999").citycode("333").countrycode("87");
        listPhones.add(phone);
        usuarioDTO.builder().name("Migue").email(emailIncorrecto).password("Java#@#8").phoneList(listPhones);


        //when
        ResultActions response = mockMvc.perform(post("/api/usuarios/"));

        //then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

}
