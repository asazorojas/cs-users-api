package com.cornershopapp.usersapi.rest.controllers;

import com.cornershopapp.usersapi.domain.dtos.UserDTO;
import com.cornershopapp.usersapi.services.UsersService;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    @DisplayName("GET /api/users empty list")
    void testGetUsersEmptyList() throws Exception {
        Mockito.when(usersService.getAllUsers()).thenReturn(List.of());
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
        Mockito.when(usersService.getAllUsers()).thenReturn(
                List.of(
                        UserDTO.builder()
                                .createdAt(Instant.now())
                                .updatedAt(Instant.now())
                                .id(1L)
                                .uuid(uuid)
                                .email("jonsnow@cornershopapp.com")
                                .firstName("Jon")
                                .lastName("Snow")
                                .build()
                )
        );
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.users", hasSize(1)))
                .andExpect(jsonPath("$.users[0].uuid", is(uuid.toString())))
                .andExpect(jsonPath("$.users[0].first_name", is("Jon")))
                .andExpect(jsonPath("$.users[0].last_name", is("Snow")))
                .andExpect(jsonPath("$.users[0].email", is("jonsnow@cornershopapp.com")));

        verify(usersService, times(1)).getAllUsers();
    }
}