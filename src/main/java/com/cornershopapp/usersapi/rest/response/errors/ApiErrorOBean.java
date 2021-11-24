package com.cornershopapp.usersapi.rest.response.errors;

import com.cornershopapp.usersapi.rest.enums.ErrorCode;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApiErrorOBean {
    private int statusCode;
    private String errorMessage;
    private ErrorCode error;
    private List<PayloadFieldErrorOBean> errors;
}
