package com.cornershopapp.usersapi.services;

import com.cornershopapp.usersapi.domain.dtos.UserDTO;
import com.cornershopapp.usersapi.domain.records.CreateUserRequestRecord;
import com.cornershopapp.usersapi.services.exceptions.FailedToCreateUserException;
import com.cornershopapp.usersapi.services.exceptions.FailedToDeleteUserException;
import com.cornershopapp.usersapi.services.exceptions.UserAlreadyExistsException;
import com.cornershopapp.usersapi.services.exceptions.UserNotFoundException;
import java.util.List;
import java.util.UUID;

public interface UsersService {
    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id) throws UserNotFoundException;

    UserDTO getUserByUUID(UUID id) throws UserNotFoundException;

    UserDTO createUser(CreateUserRequestRecord createUserPayload) throws UserAlreadyExistsException, FailedToCreateUserException;

    void deleteUserById(Long id) throws UserNotFoundException, FailedToDeleteUserException;
}
