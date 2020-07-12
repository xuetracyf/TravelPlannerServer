package com.travelplanner.travelplanner_server.restservice.payload;

import lombok.Getter;

@Getter
public class SignupRequest {
    private String username;
    private String password;
    private String first_name;
    private String last_name;
}
