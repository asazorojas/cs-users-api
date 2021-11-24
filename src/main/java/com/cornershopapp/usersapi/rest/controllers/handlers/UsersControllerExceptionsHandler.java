package com.cornershopapp.usersapi.rest.controllers.handlers;

import com.cornershopapp.usersapi.rest.enums.ErrorCode;
import com.cornershopapp.usersapi.rest.response.errors.ApiErrorOBean;
import com.cornershopapp.usersapi.rest.response.errors.PayloadFieldErrorOBean;
import com.cornershopapp.usersapi.services.exceptions.FailedToCreateUserException;
import com.cornershopapp.usersapi.services.exceptions.UserAlreadyExistsException;
import com.cornershopapp.usersapi.services.exceptions.UserNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class UsersControllerExceptionsHandler {
    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorOBean handleUserNotFoundException(UserNotFoundException exception) {
        return ApiErrorOBean.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .errorMessage(exception.getMessage())
                .error(ErrorCode.USER_NOT_FOUND)
                .build();

    }

    @ExceptionHandler(value = {FailedToCreateUserException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorOBean handleFailedToCreateUserException(FailedToCreateUserException ex) {
        return ApiErrorOBean.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(ex.getMessage())
                .error(ErrorCode.FAILED_TO_CREATE_USER)
                .build();
    }

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorOBean handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ApiErrorOBean.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(ex.getMessage())
                .error(ErrorCode.FAILED_TO_CREATE_USER)
                .build();
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorOBean handle(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<PayloadFieldErrorOBean> payloadFieldErrorOBeans = fieldErrors.stream()
                .map(fieldError ->
                        PayloadFieldErrorOBean
                                .builder()
                                .fieldName(fieldError.getField())
                                .errorMessage(fieldError.getDefaultMessage())
                                .build()
                )
                .collect(Collectors.toList());
        return ApiErrorOBean.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errors(payloadFieldErrorOBeans)
                .error(ErrorCode.BAD_REQUEST)
                .build();
    }
}
