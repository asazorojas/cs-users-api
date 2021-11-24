package com.cornershopapp.usersapi.stubs;

import com.cornershopapp.usersapi.domain.models.User;
import java.util.Date;
import java.util.UUID;

public final class UserStubs {
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
}
