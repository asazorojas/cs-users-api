package com.cornershopapp.usersapi.rest.response.users;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserOBean {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String updatedAt;
    private String createdAt;
    private PhoneOBean phone;
}
