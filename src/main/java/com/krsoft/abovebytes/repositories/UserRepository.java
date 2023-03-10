package com.krsoft.abovebytes.repositories;

import com.krsoft.abovebytes.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserLoginAndUserPassword(String login, String password);
    Optional<User> findByUserLogin(String login);
}
