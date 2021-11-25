package com.cornershopapp.usersapi.rest.controllers;

import com.cornershopapp.usersapi.UsersApiApplication;
import com.cornershopapp.usersapi.rest.response.users.UsersListOBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("integration")
@SpringBootTest(classes = UsersApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
public class UsersControllerIntegrationTest {
    @Container
    private static final MySQLContainer<?> mySQLContainer;

    static {
        mySQLContainer = new MySQLContainer<>("mysql:latest")
                .withUsername("testcontainers")
                .withPassword("Testcontain3rs!")
                .withReuse(true);
        mySQLContainer.start();
    }

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
    }

    @Autowired
    public TestRestTemplate restTemplate;

    @Test
    @Sql({"/data/clearAll.sql", "/data/createData.sql"})
    public void getAllUsersFromDB_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());
        final ResponseEntity<UsersListOBean> response = restTemplate.exchange(
                "/api/users", HttpMethod.GET, request, UsersListOBean.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsers()).isNotNull();
        assertThat(response.getBody().getUsers()).isNotEmpty();
        assertThat(response.getBody().getUsers().size()).isEqualTo(1);
    }

}
