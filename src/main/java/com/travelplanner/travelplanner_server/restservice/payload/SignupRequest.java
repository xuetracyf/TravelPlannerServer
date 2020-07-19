package com.travelplanner.travelplanner_server.restservice.payload;

import lombok.Getter;

@Getter
public class SignupRequest {
    private String userName;
    private String passWord;
    private String passwordConfirmation;
    private String firstName;
    private String lastName;
    private String email;
    private String profileUrl;
}
