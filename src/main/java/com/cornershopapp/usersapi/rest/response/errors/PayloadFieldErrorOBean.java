package com.cornershopapp.usersapi.rest.response.errors;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PayloadFieldErrorOBean {
    private String fieldName;
    private String errorMessage;
}
