package com.cornershopapp.usersapi.rest.controllers;

import com.cornershopapp.usersapi.domain.dtos.UserDTO;
import com.cornershopapp.usersapi.domain.records.CreateUserRequestRecord;
import com.cornershopapp.usersapi.rest.request.users.CreateUserIBean;
import com.cornershopapp.usersapi.rest.response.users.UserOBean;
import com.cornershopapp.usersapi.rest.response.users.UsersListOBean;
import com.cornershopapp.usersapi.services.UsersService;
import com.cornershopapp.usersapi.shared.mappers.Translator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UsersService usersService;
    private final Translator<UserDTO, UserOBean> userDTOToUserOBeanTranslator;

    @Autowired
    public UsersController(UsersService usersService, Translator<UserDTO, UserOBean> userDTOToUserOBeanTranslator) {
        this.usersService = usersService;
        this.userDTOToUserOBeanTranslator = userDTOToUserOBeanTranslator;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsersListOBean> getAllUsers() {
        List<UserOBean> users = usersService.getAllUsers()
                .stream()
                .map(userDTOToUserOBeanTranslator::translate)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                new UsersListOBean(users)
        );
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserOBean> findUserById(@NonNull @PathVariable(value = "id") Long id) {
        UserDTO user = usersService.getUserById(id);
        return ResponseEntity.ok(userDTOToUserOBeanTranslator.translate(user));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserOBean> saveUser(@Validated @RequestBody CreateUserIBean createUserPayload) {
        UserDTO user = usersService.createUser(
                new CreateUserRequestRecord(
                        createUserPayload.getFirstName(),
                        createUserPayload.getLastName(),
                        createUserPayload.getEmail()
                )
        );
        return ResponseEntity.ok(userDTOToUserOBeanTranslator.translate(user));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUserById(@PathVariable(value = "id") Long id) {
        usersService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
