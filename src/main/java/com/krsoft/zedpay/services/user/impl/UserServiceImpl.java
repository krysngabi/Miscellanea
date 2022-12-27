package com.krsoft.zedpay.services.user.impl;

import com.krsoft.zedpay.entities.Property;
import com.krsoft.zedpay.entities.User;
import com.krsoft.zedpay.models.ResponseAPI;
import com.krsoft.zedpay.repositories.UserRepository;
import com.krsoft.zedpay.services.email.EmailSenderService;
import com.krsoft.zedpay.services.properties.PropertyService;
import com.krsoft.zedpay.services.user.UserService;
import com.krsoft.zedpay.utils.CustomUtils;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
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
