package com.cornershopapp.usersapi.domain.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
}
