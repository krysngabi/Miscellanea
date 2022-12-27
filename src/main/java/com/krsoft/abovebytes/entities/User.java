package com.krsoft.abovebytes.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "tb_user")
@JsonIgnoreProperties(ignoreUnknown=true)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Basic(optional = false)
    @Column(name = "user_first_name", nullable = false)
    private String userFirstName;

    @Basic(optional = false)
    @Column(name = "user_last_name", nullable = false)
    private String userLastName;

    @Basic(optional = false)
    @Column(name = "user_login", nullable = false)
    private String userLogin;

    @Basic
    @Column(name = "user_phone")
    private String userPhone;

    @Basic
    @Column(name = "user_email")
    private String userEmail;

    @Basic(optional = false)
    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_create_id")
    private int userCreateId;

    @Column(name = "user_modify_id")
    private int userModifyId;

    @Basic()
    @Column(name = "user_modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date userModifyDate;

    @Basic()
    @Column(name = "user_create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date userCreateDate;

    @Column(name = "user_status", length = 1)
    private int userStatus = 1;

    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "user_last_login", length = 50)
    private Date userLastLogin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
