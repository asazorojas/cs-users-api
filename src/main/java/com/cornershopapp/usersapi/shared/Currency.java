package com.cornershopapp.usersapi.shared;

import lombok.Getter;

@Getter
public enum Currency {
    ARS("ARS", "$"),
    CLP("CLP", "$"),
    MXN("MXB", "$"),
    COP("COP", "$"),
    PEN("PEN", "S/.");

    private final String code;
    private final String symbol;

    Currency(String code, String symbol) {
        this.code = code;
        this.symbol = symbol;
    }
}
