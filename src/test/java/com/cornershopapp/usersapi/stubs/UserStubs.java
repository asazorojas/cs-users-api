package com.cornershopapp.usersapi.stubs;

import com.cornershopapp.usersapi.commons.Constants;
import com.cornershopapp.usersapi.domain.models.User;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public final class UserStubs {
    private static final Instant now = Instant.parse("2021-11-24T20:24:14.499Z");
    private static final UUID uuid = UUID.fromString("1e432619-7f35-4c6b-b39e-d95dde5e32b7");

    private UserStubs() {
    }

    public static User makeUserStub(Long id, String firstName, String lastName, String email,
                                    UUID uuid, Date createdAt, Date updatedAt) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setUuid(uuid);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(updatedAt);
        return user;
    }

    public static User makeSansaUserStub() {
        User user = new User();
        user.setFirstName(Constants.Sansa.FIRST_NAME);
        user.setLastName(Constants.Sansa.LAST_NAME);
        user.setEmail(Constants.Sansa.EMAIL);
        user.setUuid(uuid);
        user.setCreatedAt(Date.from(now));
        user.setUpdatedAt(Date.from(now));
        return user;
    }
}
