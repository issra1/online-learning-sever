package com.online.platform.learning.payload.request;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignupRequest {

    private String username;
    private String email;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String gender;
    private String password;
    private String institutionname;
    private String department;
    private String experience;
    private String profession;
    private String status;
    private Set<String> roles;

}
