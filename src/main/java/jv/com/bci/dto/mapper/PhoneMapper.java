package jv.com.bci.dto.mapper;

import jv.com.bci.dto.PhoneDTO;
import jv.com.bci.entity.Phone;
import org.springframework.stereotype.Component;

@Component
public class PhoneMapper {

    public Phone fromDto(PhoneDTO phoneDTO){
        Phone phone = new Phone();
        phone.setCitycode(phoneDTO.getCitycode());
        phone.setCountrycode(phoneDTO.getCountrycode());
        phone.setNumber(phoneDTO.getNumber());
        return phone;
    }

    public PhoneDTO toDto(Phone phone){
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setCitycode(phone.getCitycode());
        phoneDTO.setCountrycode(phone.getCountrycode());
        phoneDTO.setNumber(phone.getNumber());
        return phoneDTO;
    }

}
