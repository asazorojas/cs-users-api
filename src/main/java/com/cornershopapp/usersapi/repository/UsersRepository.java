package com.cornershopapp.usersapi.repository;

import com.cornershopapp.usersapi.domain.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    boolean existsByEmail(String email);
}
