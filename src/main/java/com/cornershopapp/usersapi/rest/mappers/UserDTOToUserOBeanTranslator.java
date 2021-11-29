package com.cornershopapp.usersapi.rest.mappers;

import com.cornershopapp.usersapi.domain.dtos.PhoneDTO;
import com.cornershopapp.usersapi.domain.dtos.UserDTO;
import com.cornershopapp.usersapi.rest.response.users.PhoneOBean;
import com.cornershopapp.usersapi.rest.response.users.UserOBean;
import com.cornershopapp.usersapi.shared.mappers.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDTOToUserOBeanTranslator implements Translator<UserDTO, UserOBean> {

    private final Translator<PhoneDTO, PhoneOBean> phoneDTOToPhoneResponseTranslator;

    @Autowired
    public UserDTOToUserOBeanTranslator(Translator<PhoneDTO, PhoneOBean> phoneDTOToPhoneResponseTranslator) {
        this.phoneDTOToPhoneResponseTranslator = phoneDTOToPhoneResponseTranslator;
    }

    @Override
    public UserOBean translate(UserDTO input) {
        if (input == null) {
            return null;
        }
        UserOBean output = new UserOBean();
        output.setUuid(input.getUuid());
        output.setFirstName(input.getFirstName());
        output.setLastName(input.getLastName());
        output.setEmail(input.getEmail());
        output.setCreatedAt(input.getCreatedAt().toString());
        output.setUpdatedAt(input.getUpdatedAt().toString());
        output.setPhone(phoneDTOToPhoneResponseTranslator.translate(input.getPhone()));
        return output;
    }
}
