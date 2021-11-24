package com.cornershopapp.usersapi.services.mappers;

import com.cornershopapp.usersapi.domain.dtos.PhoneDTO;
import com.cornershopapp.usersapi.domain.models.Phone;
import org.springframework.stereotype.Component;

@Component
public class PhoneToPhoneDTOTranslator implements Translator<Phone, PhoneDTO> {
    @Override
    public PhoneDTO translate(Phone input) {
        if (input == null) {
            return null;
        }
        return PhoneDTO.builder()
                .id(input.getId())
                .number(input.getNumber())
                .build();
    }
}
