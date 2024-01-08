package jv.com.bci.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("created")
    private Date created;

    @JsonProperty("modified")
    private Date modified;

    @JsonProperty("last_login")
    private Date lastLogin;

    @JsonProperty("token")
    private String token;

    @JsonProperty("isActive")
    private boolean isActive;
}
