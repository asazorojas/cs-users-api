package com.cornershopapp.usersapi.shared;

import lombok.Getter;

@Getter
public enum CountryCode {
    AR("Argentina", Currency.ARS),
    CL("Chile", Currency.CLP),
    MX("Mexico", Currency.MXN),
    CO("Colombia", Currency.COP),
    PE("Peru", Currency.PEN)
    ;

    private final String name;
    private final Currency currency;

    CountryCode(String name, Currency currency) {
        this.name = name;
        this.currency = currency;
    }
}
