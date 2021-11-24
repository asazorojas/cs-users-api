package com.cornershopapp.usersapi.rest.mappers;

import com.cornershopapp.usersapi.domain.dtos.PhoneDTO;
import com.cornershopapp.usersapi.domain.dtos.UserDTO;
import com.cornershopapp.usersapi.rest.response.users.PhoneOBean;
import com.cornershopapp.usersapi.rest.response.users.UserOBean;
import com.cornershopapp.usersapi.services.mappers.Translator;
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
        return UserOBean.builder()
                .uuid(input.getUuid())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .email(input.getEmail())
                .createdAt(input.getCreatedAt().toString())
                .updatedAt(input.getUpdatedAt().toString())
                .phone(phoneDTOToPhoneResponseTranslator.translate(input.getPhone()))
                .build();
    }
}
