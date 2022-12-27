package com.krsoft.abovebytes.services.user.impl;

import com.krsoft.abovebytes.entities.User;
import com.krsoft.abovebytes.repositories.UserRepository;
import com.krsoft.abovebytes.services.user.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUserLoginAndUserPassword(String login, String password) {
        Optional<User> user = userRepository.findByUserLoginAndUserPassword(login, password);

        return user.orElse(null);
    }

    @Override
    public User findByUserLogin(String login) {
        Optional<User> user = userRepository.findByUserLogin(login);

        return user.orElse(null);
    }
}
