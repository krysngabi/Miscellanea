package com.krsoft.abovebytes.security.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

public class MiscellaneaUserDetails extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Getter@Setter
    private int userId;
    @Getter@Setter
    private String email;
    @Getter@Setter
    private String userPhone;
    @Getter@Setter
    private String isIdentified;
    @Getter@Setter
    private String declineMessage;

    public MiscellaneaUserDetails(int userId, String userPhone, String email, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.userId = userId;
        this.userPhone = userPhone;
        this.email = email;
    }
}
