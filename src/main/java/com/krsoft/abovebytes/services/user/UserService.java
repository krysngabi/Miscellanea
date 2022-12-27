package com.krsoft.abovebytes.services.user;

import com.krsoft.abovebytes.entities.User;

public interface UserService {
    User findByUserLoginAndUserPassword(String login, String password);
    User findByUserLogin(String login);
}
