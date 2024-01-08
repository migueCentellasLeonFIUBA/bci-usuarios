package jv.com.bci.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneDTO {

    @JsonProperty("number")
    private String number;

    @JsonProperty("citycode")
    private String citycode;

    @JsonProperty("countrycode")
    private String countrycode;
}
