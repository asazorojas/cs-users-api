package com.cornershopapp.usersapi.repository;

import com.cornershopapp.usersapi.commons.Constants;
import com.cornershopapp.usersapi.domain.models.User;
import com.cornershopapp.usersapi.stubs.UserStubs;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import one.util.streamex.StreamEx;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.cornershopapp.usersapi.commons.Constants.FIXED_UUID;
import static com.cornershopapp.usersapi.commons.Constants.NOW;
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
        User user = new User();
        user.setFirstName(Constants.Sansa.FIRST_NAME);
        user.setLastName(Constants.Sansa.LAST_NAME);
        user.setEmail(Constants.Sansa.EMAIL);
        user.setUuid(FIXED_UUID);
        user.setCreatedAt(Date.from(NOW));
        user.setUpdatedAt(Date.from(NOW));
        usersRepository.save(user);

        List<User> users = StreamEx.of(usersRepository.findAll().spliterator()).collect(Collectors.toList());

        assertThat(users.size()).isEqualTo(1);
        User sansa = users.get(0);
        assertThat(sansa).isNotNull();
        assertThat(sansa.getEmail()).isEqualTo(Constants.Sansa.EMAIL);
        assertThat(sansa.getFirstName()).isEqualTo(Constants.Sansa.FIRST_NAME);
        assertThat(sansa.getLastName()).isEqualTo(Constants.Sansa.LAST_NAME);
        assertThat(sansa.getUuid()).isEqualTo(FIXED_UUID);
        assertThat(sansa.getCreatedAt()).isEqualTo(Date.from(NOW));
        assertThat(sansa.getUpdatedAt()).isEqualTo(Date.from(NOW));
    }

    @Test
    void testFindByIdWhenTheUserExists() {
        User user = UserStubs.makeSansaUserStub();
        User createdUser = usersRepository.save(user);

        User sansa = usersRepository.findById(createdUser.getId()).orElseThrow(RuntimeException::new);
        assertThat(sansa).isNotNull();
        assertThat(sansa.getEmail()).isEqualTo(Constants.Sansa.EMAIL);
        assertThat(sansa.getFirstName()).isEqualTo(Constants.Sansa.FIRST_NAME);
        assertThat(sansa.getLastName()).isEqualTo(Constants.Sansa.LAST_NAME);
        assertThat(sansa.getUuid()).isEqualTo(FIXED_UUID);
        assertThat(sansa.getCreatedAt()).isEqualTo(Date.from(NOW));
        assertThat(sansa.getUpdatedAt()).isEqualTo(Date.from(NOW));
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