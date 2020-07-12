package com.travelplanner.travelplanner_server.restservice.payload;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String username;
    private String password;
}
