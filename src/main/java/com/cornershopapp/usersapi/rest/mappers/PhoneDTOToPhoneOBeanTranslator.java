package com.cornershopapp.usersapi.rest.mappers;

import com.cornershopapp.usersapi.domain.dtos.PhoneDTO;
import com.cornershopapp.usersapi.rest.response.users.PhoneOBean;
import com.cornershopapp.usersapi.services.mappers.Translator;
import org.springframework.stereotype.Component;

@Component
public class PhoneDTOToPhoneOBeanTranslator implements Translator<PhoneDTO, PhoneOBean> {
    @Override
    public PhoneOBean translate(PhoneDTO input) {
        if(input == null) {
            return null;
        }
        return PhoneOBean.builder()
                .number(input.getNumber())
                .build();
    }
}
