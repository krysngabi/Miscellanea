package com.krsoft.zedpay.security.config;

import com.krsoft.zedpay.models.ResponseAPI;
import com.krsoft.zedpay.security.models.ZedPayUserDetaills;
import com.krsoft.zedpay.services.user.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String loginPassword) throws UsernameNotFoundException {
        com.krsoft.zedpay.entities.User user;
        String[] splitted = loginPassword.split(":");
        if (splitted.length == 2) {
            user = this.userService.findByUserLoginAndUserPassword(splitted[0], splitted[1]);
        } else {
            user = this.userService.findByUserLogin(loginPassword);
        }

        if (user != null) {
            return new ZedPayUserDetaills(user.getUserId(), user.getUserPhone(), user.getUserEmail(), user.getUserLogin(), user.getUserPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with login: " + splitted[0]);
        }
    }

    public UserDetails loadUserByLogin(String login) throws UsernameNotFoundException {
        com.krsoft.zedpay.entities.User user = this.userService.findByUserLogin(login);
        if (user != null) {
            return new ZedPayUserDetaills(user.getUserId(), user.getUserPhone(), user.getUserEmail(), user.getUserLogin(), user.getUserPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with login: " + login);
        }
    }
}
