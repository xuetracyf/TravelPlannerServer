package com.travelplanner.travelplanner_server.exception;

public class FailedAuthenticationException extends RuntimeException{

    public FailedAuthenticationException() {
        super("Authentication failed");
    }
}
