package jv.com.bci.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jv.com.bci.entity.Phone;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotEmpty
    @NotBlank
    @JsonProperty("email")
    private String email;

    @NotBlank
    @JsonProperty("password")
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("phones")
    private List<PhoneDTO> phoneList;

}
