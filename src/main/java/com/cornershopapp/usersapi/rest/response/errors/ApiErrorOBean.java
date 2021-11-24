package com.cornershopapp.usersapi.rest.response.errors;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import com.cornershopapp.usersapi.rest.enums.ErrorCode;

@Data
@Builder
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApiErrorOBean {
    private int statusCode;
    private String errorMessage;
    private ErrorCode error;
    private List<PayloadFieldErrorOBean> errors;
}
