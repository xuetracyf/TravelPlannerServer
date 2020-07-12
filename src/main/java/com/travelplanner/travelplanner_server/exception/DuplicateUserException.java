package com.travelplanner.travelplanner_server.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String username) {
        super("Username " + username + " has already exists");
    }
}
