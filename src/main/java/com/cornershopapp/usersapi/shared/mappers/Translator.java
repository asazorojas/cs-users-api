package com.cornershopapp.usersapi.shared.mappers;

public interface Translator<I, O> {
    O translate(I input);
}