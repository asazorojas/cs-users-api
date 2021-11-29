package com.cornershopapp.usersapi.services.impl;

import com.cornershopapp.usersapi.domain.dtos.UserDTO;
import com.cornershopapp.usersapi.domain.models.User;
import com.cornershopapp.usersapi.domain.records.CreateUserRequestRecord;
import com.cornershopapp.usersapi.repository.UsersRepository;
import com.cornershopapp.usersapi.services.UsersService;
import com.cornershopapp.usersapi.services.exceptions.FailedToCreateUserException;
import com.cornershopapp.usersapi.services.exceptions.FailedToDeleteUserException;
import com.cornershopapp.usersapi.services.exceptions.UserAlreadyExistsException;
import com.cornershopapp.usersapi.services.exceptions.UserNotFoundException;
import com.cornershopapp.usersapi.shared.mappers.Translator;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final Translator<User, UserDTO> userToUserDTOTranslator;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, Translator<User, UserDTO> userToUserDTOTranslator) {
        this.usersRepository = usersRepository;
        this.userToUserDTOTranslator = userToUserDTOTranslator;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return StreamEx.of(
                        usersRepository.findAll().spliterator())
                .map(userToUserDTOTranslator::translate)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) throws UserNotFoundException {
        return usersRepository.findById(id)
                .map(userToUserDTOTranslator::translate)
                .orElseThrow(
                        () -> new UserNotFoundException(
                                String.format("User with id %s does not exist", id)
                        )
                );
    }

    @Override
    public UserDTO getUserByUUID(UUID uuid) throws UserNotFoundException {
        return usersRepository.findByUuid(uuid)
                .map(userToUserDTOTranslator::translate)
                .orElseThrow(
                        () -> new UserNotFoundException(
                                String.format("User with uuid %s does not exist", uuid)
                        )
                );
    }


    @Override
    public UserDTO createUser(CreateUserRequestRecord createUserPayload) throws UserAlreadyExistsException, FailedToCreateUserException {
        if (usersRepository.existsByEmail(createUserPayload.email())) {
            throw new UserAlreadyExistsException("User already exists");
        }
        try {
            User user = new User();
            user.setFirstName(createUserPayload.firstName());
            user.setLastName(createUserPayload.lastName());
            user.setEmail(createUserPayload.email());
            user.setCreatedAt(Date.from(Instant.now()));
            user.setUpdatedAt(Date.from(Instant.now()));
            user.setUuid(UUID.randomUUID());
            return userToUserDTOTranslator.translate(
                    usersRepository.save(user)
            );
        } catch (DataIntegrityViolationException | ConstraintViolationException ex) {
            throw new FailedToCreateUserException(ex.getMessage(), ex);
        }
    }

    @Override
    public void deleteUserById(Long id) throws UserNotFoundException, FailedToDeleteUserException {
        if (!usersRepository.existsById(id)) {
            throw new UserNotFoundException(String.format("User with id %s does not exist", id));
        }
        try {
            usersRepository.deleteById(id);
        } catch (Exception ex) {
            log.error(String.format("Failed to delete the user with id %s", id), ex);
            throw new FailedToDeleteUserException(String.format("User with id %s cannot be deleted", id));
        }
    }
}