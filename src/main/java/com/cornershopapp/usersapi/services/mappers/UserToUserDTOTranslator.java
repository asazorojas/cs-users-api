package com.cornershopapp.usersapi.services.mappers;

import com.cornershopapp.usersapi.domain.dtos.PhoneDTO;
import com.cornershopapp.usersapi.domain.dtos.UserDTO;
import com.cornershopapp.usersapi.domain.models.Phone;
import com.cornershopapp.usersapi.domain.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDTOTranslator implements Translator<User, UserDTO> {
    private final Translator<Phone, PhoneDTO> phoneToPhoneDTOTranslator;

    @Autowired
    public UserToUserDTOTranslator(Translator<Phone, PhoneDTO> phoneToPhoneDTOTranslator) {
        this.phoneToPhoneDTOTranslator = phoneToPhoneDTOTranslator;
    }

    @Override
    public UserDTO translate(User input) {
        if (input == null) {
            return null;
        }
        return UserDTO.builder()
                .id(input.getId())
                .uuid(input.getUuid())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .email(input.getEmail())
                .createdAt(input.getCreatedAt().toInstant())
                .updatedAt(input.getUpdatedAt().toInstant())
                .phone(phoneToPhoneDTOTranslator.translate(input.getPhone()))
                .build();
    }
}
