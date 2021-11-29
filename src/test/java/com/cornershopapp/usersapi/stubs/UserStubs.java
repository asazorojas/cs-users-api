package com.cornershopapp.usersapi.stubs;

import com.cornershopapp.usersapi.commons.Constants;
import com.cornershopapp.usersapi.domain.models.User;

import java.util.Date;
import java.util.UUID;

import static com.cornershopapp.usersapi.commons.Constants.NOW;

public final class UserStubs {
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
        user.setUuid(Constants.FIXED_UUID);
        user.setCreatedAt(Date.from(NOW));
        user.setUpdatedAt(Date.from(NOW));
        return user;
    }
}
