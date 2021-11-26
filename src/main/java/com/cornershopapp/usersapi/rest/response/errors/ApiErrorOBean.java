package com.cornershopapp.usersapi.rest.response.errors;

import com.cornershopapp.usersapi.rest.enums.ErrorCode;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApiErrorOBean {
    private int statusCode;
    private String errorMessage;
    private ErrorCode error;
    private List<PayloadFieldErrorOBean> errors;
}
