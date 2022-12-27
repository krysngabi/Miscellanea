package com.krsoft.zedpay.security.models;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private String token;
    private String expDate;
}