package com.krsoft.zedpay.security.controllers;

import com.krsoft.zedpay.models.ResponseAPI;
import com.krsoft.zedpay.security.config.JwtTokenUtil;
import com.krsoft.zedpay.security.config.JwtUserDetailsService;
import com.krsoft.zedpay.security.models.JwtRequest;
import com.krsoft.zedpay.security.models.JwtResponse;
import com.krsoft.zedpay.security.models.ZedPayUserDetaills;
import com.krsoft.zedpay.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping(value = "/authenticate" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        com.krsoft.zedpay.entities.User user = new com.krsoft.zedpay.entities.User();

        user.setUserLogin(authenticationRequest.getUsername());
        user.setUserPassword(authenticationRequest.getPassword());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserLogin() +  ":" + user.getUserPassword(), user.getUserPassword()));
        } catch (Exception e) {
            return new ResponseEntity<>(jwtResponse, HttpStatus.BAD_REQUEST);
        }

        final ZedPayUserDetaills userDetails = (ZedPayUserDetaills) userDetailsService.loadUserByLogin(user.getUserLogin());

        final String token = jwtTokenUtil.generateToken(userDetails);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expiration = jwtTokenUtil.getExpirationDateFromToken(token);

        jwtResponse.setToken(token);
        jwtResponse.setExpDate(simpleDateFormat.format(expiration));

        return ResponseEntity.ok().body(jwtResponse);
    }
}
