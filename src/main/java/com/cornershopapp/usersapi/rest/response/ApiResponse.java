package com.cornershopapp.usersapi.rest.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApiResponse<T> {
    private T data;
}
