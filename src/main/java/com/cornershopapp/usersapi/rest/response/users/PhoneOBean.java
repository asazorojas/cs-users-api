package com.cornershopapp.usersapi.rest.response.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PhoneOBean {
    private String number;
}
