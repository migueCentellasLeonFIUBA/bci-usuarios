package jv.com.bci.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("created")
    private String created;

    @JsonProperty("modified")
    private String modified;

    @JsonProperty("last_login")
    private String lastLogin;

    @JsonProperty("token")
    private String token;

    @JsonProperty("isActive")
    private boolean isActive;
}
