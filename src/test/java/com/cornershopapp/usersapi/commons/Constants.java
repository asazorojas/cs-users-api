package com.cornershopapp.usersapi.commons;

import java.time.Instant;
import java.util.UUID;

public final class Constants {
    private Constants() {
    }

    public static final Instant NOW = Instant.parse("2021-11-24T20:24:14.499Z");
    public static final UUID FIXED_UUID = UUID.fromString("1e432619-7f35-4c6b-b39e-d95dde5e32b7");

    public static final class Jon {
        public static final String FIRST_NAME = "Jon";
        public static final String LAST_NAME = "Snow";
        public static final String EMAIL = "jonsnow@cornershopapp.com";
    }

    public static final class Sansa {
        public static final String FIRST_NAME = "Sansa";
        public static final String LAST_NAME = "Stark";
        public static final String EMAIL = "sansastark@cornershopapp.com";
    }
}
