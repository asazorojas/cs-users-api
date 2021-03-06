package com.cornershopapp.usersapi.rest.controllers;

import com.cornershopapp.usersapi.commons.Constants;
import com.cornershopapp.usersapi.domain.dtos.UserDTO;
import com.cornershopapp.usersapi.domain.records.CreateUserRequestRecord;
import com.cornershopapp.usersapi.rest.request.users.CreateUserIBean;
import com.cornershopapp.usersapi.services.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {

    @MockBean
    private UsersService usersService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/users empty list")
    void testGetUsersEmptyList() throws Exception {
        when(usersService.getAllUsers()).thenReturn(List.of());
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.users", hasSize(0)));

        verify(usersService, times(1)).getAllUsers();
    }

    @Test
    @DisplayName("GET /api/users with one user")
    void testGetUsersWithOneUser() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(usersService.getAllUsers()).thenReturn(
                List.of(
                        UserDTO.builder()
                                .createdAt(Instant.now())
                                .updatedAt(Instant.now())
                                .id(1L)
                                .uuid(uuid)
                                .email(Constants.Jon.EMAIL)
                                .firstName(Constants.Jon.FIRST_NAME)
                                .lastName(Constants.Jon.LAST_NAME)
                                .build()
                )
        );
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.users", hasSize(1)))
                .andExpect(jsonPath("$.users[0].uuid", is(uuid.toString())))
                .andExpect(jsonPath("$.users[0].first_name", is(Constants.Jon.FIRST_NAME)))
                .andExpect(jsonPath("$.users[0].last_name", is(Constants.Jon.LAST_NAME)))
                .andExpect(jsonPath("$.users[0].email", is(Constants.Jon.EMAIL)));

        verify(usersService, times(1)).getAllUsers();
    }

    @Test
    @DisplayName("GET /api/users/uuid with one user")
    void testGetUserVyUUIDWithOneUser() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(usersService.getUserByUUID(eq(uuid))).thenReturn(
                        UserDTO.builder()
                                .createdAt(Instant.now())
                                .updatedAt(Instant.now())
                                .id(1L)
                                .uuid(uuid)
                                .email(Constants.Jon.EMAIL)
                                .firstName(Constants.Jon.FIRST_NAME)
                                .lastName(Constants.Jon.LAST_NAME)
                                .build()
        );
        mockMvc.perform(get("/api/users/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.uuid", is(uuid.toString())))
                .andExpect(jsonPath("$.first_name", is(Constants.Jon.FIRST_NAME)))
                .andExpect(jsonPath("$.last_name", is(Constants.Jon.LAST_NAME)))
                .andExpect(jsonPath("$.email", is(Constants.Jon.EMAIL)));

        verify(usersService, times(1)).getUserByUUID(eq(uuid));
    }

    @Test
    @DisplayName("POST /api/users")
    void testCreateUser() throws Exception {
        CreateUserIBean payload = new CreateUserIBean();
        payload.setEmail(Constants.Sansa.EMAIL);
        payload.setFirstName(Constants.Sansa.FIRST_NAME);
        payload.setLastName(Constants.Sansa.LAST_NAME);

        UUID uuid = UUID.randomUUID();
        Instant now = Instant.now();
        when(usersService.createUser(any(CreateUserRequestRecord.class))).thenReturn(
                UserDTO.builder()
                        .id(1L)
                        .uuid(uuid)
                        .lastName(Constants.Sansa.LAST_NAME)
                        .firstName(Constants.Sansa.FIRST_NAME)
                        .email(Constants.Sansa.EMAIL)
                        .updatedAt(now)
                        .createdAt(now)
                        .build()
        );

        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(payload)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email", is(Constants.Sansa.EMAIL)))
                .andExpect(jsonPath("$.first_name", is(Constants.Sansa.FIRST_NAME)))
                .andExpect(jsonPath("$.last_name", is(Constants.Sansa.LAST_NAME)))
                .andExpect(jsonPath("$.phone", nullValue()))
                .andExpect(jsonPath("$.uuid", is(uuid.toString())));
    }

    @Test
    @DisplayName("POST /api/users failed")
    void testCouldNotCreateUserWhenAllPayloadFieldsAreNull() throws Exception {
        CreateUserIBean payload = new CreateUserIBean();

        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status_code", is(400)))
                .andExpect(jsonPath("$.error", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.errors", hasSize(6)));

        verify(usersService, never()).createUser(any(CreateUserRequestRecord.class));
    }

    private String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}