package com.krsoft.abovebytes.security.config;

import com.krsoft.abovebytes.security.models.MiscellaneaUserDetails;
import com.krsoft.abovebytes.services.user.impl.UserServiceImpl;
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
        com.krsoft.abovebytes.entities.User user;
        String[] splitted = loginPassword.split(":");
        if (splitted.length == 2) {
            user = this.userService.findByUserLoginAndUserPassword(splitted[0], splitted[1]);
        } else {
            user = this.userService.findByUserLogin(loginPassword);
        }

        if (user != null) {
            return new MiscellaneaUserDetails(user.getUserId(), user.getUserPhone(), user.getUserEmail(), user.getUserLogin(), user.getUserPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with login: " + splitted[0]);
        }
    }

    public UserDetails loadUserByLogin(String login) throws UsernameNotFoundException {
        com.krsoft.abovebytes.entities.User user = this.userService.findByUserLogin(login);
        if (user != null) {
            return new MiscellaneaUserDetails(user.getUserId(), user.getUserPhone(), user.getUserEmail(), user.getUserLogin(), user.getUserPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with login: " + login);
        }
    }
}
