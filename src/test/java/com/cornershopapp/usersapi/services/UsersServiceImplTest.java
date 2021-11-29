package com.cornershopapp.usersapi.services;

import com.cornershopapp.usersapi.commons.Constants;
import com.cornershopapp.usersapi.domain.dtos.UserDTO;
import com.cornershopapp.usersapi.domain.models.User;
import com.cornershopapp.usersapi.domain.records.CreateUserRequestRecord;
import com.cornershopapp.usersapi.repository.UsersRepository;
import com.cornershopapp.usersapi.services.exceptions.FailedToCreateUserException;
import com.cornershopapp.usersapi.services.exceptions.UserAlreadyExistsException;
import com.cornershopapp.usersapi.services.exceptions.UserNotFoundException;
import com.cornershopapp.usersapi.services.impl.UsersServiceImpl;
import com.cornershopapp.usersapi.stubs.UserStubs;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import static com.cornershopapp.usersapi.commons.Constants.FIXED_UUID;
import static com.cornershopapp.usersapi.commons.Constants.NOW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UsersServiceImplTest {

    @MockBean
    UsersRepository usersRepository;

    @Autowired
    UsersServiceImpl usersService;

    @BeforeEach
    void setUp() {
        Instant now = Instant.parse("2021-11-24T20:24:14.499Z");
        UUID uuid = UUID.fromString("1e432619-7f35-4c6b-b39e-d95dde5e32b7");

        try (MockedStatic<Instant> mockedInstant = mockStatic(Instant.class)) {
            mockedInstant.when(Instant::now).thenReturn(now);
        }
        try (MockedStatic<UUID> mockedUUID = mockStatic(UUID.class)) {
            mockedUUID.when(UUID::randomUUID).thenReturn(uuid);
        }
    }

    @Test
    void Should_ReturnEmptyList_When_UsersRepositoryReturnsEmptyList() {
        when(usersRepository.findAll()).thenReturn(List.of());

        assertThat(usersService.getAllUsers()).isEmpty();
        verify(usersRepository, times(1)).findAll();
    }

    @Test
    void Should_ReturnOneUserDTO_When_UsersRepositoryReturnsExactlyOneUser() {
        User user = UserStubs.makeUserStub(
                1L,
                Constants.Jon.FIRST_NAME,
                Constants.Jon.LAST_NAME,
                Constants.Jon.EMAIL,
                FIXED_UUID,
                Date.from(NOW),
                Date.from(NOW)
        );
        when(usersRepository.findAll()).thenReturn(List.of(user));

        List<UserDTO> allUsers = usersService.getAllUsers();
        assertThat(allUsers).isNotEmpty();
        assertThat(allUsers).hasSize(1);
        verify(usersRepository, times(1)).findAll();
    }

    @Test
    void Should_ReturnOneUserDTO_When_UserExists() {
        User user = UserStubs.makeUserStub(
                1L,
                Constants.Jon.FIRST_NAME,
                Constants.Jon.LAST_NAME,
                Constants.Jon.EMAIL,
                FIXED_UUID,
                Date.from(NOW),
                Date.from(NOW)
        );
        when(usersRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserDTO userDTO = usersService.getUserById(1L);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getCreatedAt()).isEqualTo(NOW);
        assertThat(userDTO.getUpdatedAt()).isEqualTo(NOW);
        assertThat(userDTO.getEmail()).isEqualTo(Constants.Jon.EMAIL);
        assertThat(userDTO.getFirstName()).isEqualTo(Constants.Jon.FIRST_NAME);
        assertThat(userDTO.getLastName()).isEqualTo(Constants.Jon.LAST_NAME);
        assertThat(userDTO.getId()).isEqualTo(1L);
        assertThat(userDTO.getUuid()).isEqualTo(FIXED_UUID);
        verify(usersRepository, times(1)).findById(1L);
    }

    @Test
    void Should_RaiseException_When_UserDoesNotExist() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> usersService.getUserById(1L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with id 1 does not exist");


        verify(usersRepository, times(1)).findById(1L);
    }

    @Test
    void Should_ReturnOneUserDTO_When_UserExists_Using_UUID() {
        User user = UserStubs.makeUserStub(
                1L,
                Constants.Jon.FIRST_NAME,
                Constants.Jon.LAST_NAME,
                Constants.Jon.EMAIL,
                FIXED_UUID,
                Date.from(NOW),
                Date.from(NOW)
        );
        when(usersRepository.findByUuid(user.getUuid())).thenReturn(Optional.of(user));

        UserDTO userDTO = usersService.getUserByUUID(FIXED_UUID);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getCreatedAt()).isEqualTo(NOW);
        assertThat(userDTO.getUpdatedAt()).isEqualTo(NOW);
        assertThat(userDTO.getEmail()).isEqualTo(Constants.Jon.EMAIL);
        assertThat(userDTO.getFirstName()).isEqualTo(Constants.Jon.FIRST_NAME);
        assertThat(userDTO.getLastName()).isEqualTo(Constants.Jon.LAST_NAME);
        assertThat(userDTO.getId()).isEqualTo(1L);
        assertThat(userDTO.getUuid()).isEqualTo(FIXED_UUID);
        verify(usersRepository, times(1)).findByUuid(FIXED_UUID);
    }

    @Test
    void Should_RaiseException_When_UserDoesNotExist_Using_UUID() {
        when(usersRepository.findByUuid(FIXED_UUID)).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> usersService.getUserByUUID(FIXED_UUID))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(String.format("User with uuid %s does not exist", FIXED_UUID));


        verify(usersRepository, times(1)).findByUuid(FIXED_UUID);
    }

    @Test
    void Should_RaiseException_When_TryingToCreateAUserThatAlreadyExists() {
        CreateUserRequestRecord createUserRequestRecord = new CreateUserRequestRecord(
                "FirstName",
                "LastName",
                "fakeuser@fakedomain.com"
        );

        when(usersRepository.existsByEmail(createUserRequestRecord.email())).thenReturn(true);

        assertThatThrownBy(
                () -> usersService.createUser(createUserRequestRecord)
        ).isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    void Should_RaiseException_When_TryingToCreateOneUser() {
        CreateUserRequestRecord createUserRequestRecord = new CreateUserRequestRecord(
                "FirstName",
                "LastName",
                "fakeuser@fakedomain.com"
        );

        when(usersRepository.existsByEmail(createUserRequestRecord.email())).thenReturn(false);
        when(usersRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);

        assertThatExceptionOfType(FailedToCreateUserException.class)
                .isThrownBy(
                        () -> usersService.createUser(createUserRequestRecord)
                );
    }

    @Test
    void Should_CreateOneUserInstance() {
        CreateUserRequestRecord createUserRequestRecord = new CreateUserRequestRecord(
                "FirstName",
                "LastName",
                "fakeuser@fakedomain.com"
        );
        User user = new User();
        user.setFirstName(createUserRequestRecord.firstName());
        user.setLastName(createUserRequestRecord.lastName());
        user.setEmail(createUserRequestRecord.email());
        user.setUuid(FIXED_UUID);
        user.setId(1L);
        user.setCreatedAt(Date.from(NOW));
        user.setUpdatedAt(Date.from(NOW));

        when(usersRepository.existsByEmail(createUserRequestRecord.email())).thenReturn(false);
        when(usersRepository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO = usersService.createUser(createUserRequestRecord);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getCreatedAt()).isEqualTo(NOW);
        assertThat(userDTO.getUpdatedAt()).isEqualTo(NOW);
        assertThat(userDTO.getEmail()).isEqualTo("fakeuser@fakedomain.com");
        assertThat(userDTO.getFirstName()).isEqualTo("FirstName");
        assertThat(userDTO.getLastName()).isEqualTo("LastName");
        assertThat(userDTO.getId()).isEqualTo(1L);
        assertThat(userDTO.getUuid()).isEqualTo(FIXED_UUID);

        InOrder inOrder = Mockito.inOrder(usersRepository);
        inOrder.verify(usersRepository, times(1)).existsByEmail(user.getEmail());
        inOrder.verify(usersRepository, times(1)).save(any(User.class));
    }
}