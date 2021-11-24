package com.cornershopapp.usersapi.domain.dtos;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO implements Serializable {
    private Long id;
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
    private Instant createdAt;
    private Instant updatedAt;
    private PhoneDTO phone;
}