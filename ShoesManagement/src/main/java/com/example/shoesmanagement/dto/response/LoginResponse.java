package com.example.shoesmanagement.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
    private String username;
    private Date createdDate;
    private Date updatedDate;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String token;
    private String imgUrl;
    private String[] roles;
}
