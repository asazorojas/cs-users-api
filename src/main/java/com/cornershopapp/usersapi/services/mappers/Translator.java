package com.cornershopapp.usersapi.services.mappers;

public interface Translator<I, O> {
    O translate(I input);
}