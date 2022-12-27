package com.krsoft.zedpay.services.user;

import com.krsoft.zedpay.entities.User;
import com.krsoft.zedpay.models.ResponseAPI;
import freemarker.template.TemplateException;

import java.io.IOException;

public interface UserService {
    User findByUserLoginAndUserPassword(String login, String password);
    User findByUserLogin(String login);
}
