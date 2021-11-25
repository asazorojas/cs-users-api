package com.cornershopapp.usersapi.repository;

import com.cornershopapp.usersapi.domain.models.User;
import com.cornershopapp.usersapi.stubs.UserStubs;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import one.util.streamex.StreamEx;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UsersRepositoryTest {
    @Autowired
    private UsersRepository usersRepository;

    @AfterEach
    void tearDown() {
        usersRepository.deleteAll();
    }

    @Test
    void testFindAllWithEmptyResult() {
        List<User> users = StreamEx.of(usersRepository.findAll().spliterator()).collect(Collectors.toList());

        assertThat(users.size()).isEqualTo(0);
    }

    @Test
    void testFindAllWithOneUser() {
        Instant now = Instant.parse("2021-11-24T20:24:14.499Z");
        UUID uuid = UUID.fromString("1e432619-7f35-4c6b-b39e-d95dde5e32b7");

        User user = new User();
        user.setFirstName("Sansa");
        user.setLastName("Stark");
        user.setEmail("sansa.stark@cornershopapp.com");
        user.setUuid(uuid);
        user.setCreatedAt(Date.from(now));
        user.setUpdatedAt(Date.from(now));
        usersRepository.save(user);

        List<User> users = StreamEx.of(usersRepository.findAll().spliterator()).collect(Collectors.toList());

        assertThat(users.size()).isEqualTo(1);
        User sansa = users.get(0);
        assertThat(sansa).isNotNull();
        assertThat(sansa.getEmail()).isEqualTo("sansa.stark@cornershopapp.com");
        assertThat(sansa.getFirstName()).isEqualTo("Sansa");
        assertThat(sansa.getLastName()).isEqualTo("Stark");
        assertThat(sansa.getUuid()).isEqualTo(uuid);
        assertThat(sansa.getCreatedAt()).isEqualTo(Date.from(now));
        assertThat(sansa.getUpdatedAt()).isEqualTo(Date.from(now));
    }

    @Test
    void testFindByIdWhenTheUserExists() {
        Instant now = Instant.parse("2021-11-24T20:24:14.499Z");
        UUID uuid = UUID.fromString("1e432619-7f35-4c6b-b39e-d95dde5e32b7");

        User user = UserStubs.makeSansaUserStub();
        User createdUser = usersRepository.save(user);

        User sansa = usersRepository.findById(createdUser.getId()).orElseThrow(RuntimeException::new);
        assertThat(sansa).isNotNull();
        assertThat(sansa.getEmail()).isEqualTo("sansa.stark@cornershopapp.com");
        assertThat(sansa.getFirstName()).isEqualTo("Sansa");
        assertThat(sansa.getLastName()).isEqualTo("Stark");
        assertThat(sansa.getUuid()).isEqualTo(uuid);
        assertThat(sansa.getCreatedAt()).isEqualTo(Date.from(now));
        assertThat(sansa.getUpdatedAt()).isEqualTo(Date.from(now));
    }

    @Test
    void testFindByIdWhenTheUserDoesNotExist() {
        User user = UserStubs.makeSansaUserStub();
        User createdUser = usersRepository.save(user);
        usersRepository.delete(createdUser);

        Optional<User> optionalUser = usersRepository.findById(createdUser.getId());

        assertThat(optionalUser.isEmpty()).isTrue();
    }
}