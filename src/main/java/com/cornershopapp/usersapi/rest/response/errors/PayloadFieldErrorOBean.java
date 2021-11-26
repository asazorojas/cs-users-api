package com.cornershopapp.usersapi.rest.response.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PayloadFieldErrorOBean {
    private String fieldName;
    private String errorMessage;
}
