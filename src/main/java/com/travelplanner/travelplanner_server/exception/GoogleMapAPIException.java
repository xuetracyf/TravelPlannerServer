package com.travelplanner.travelplanner_server.exception;

public class GoogleMapAPIException extends RuntimeException {
    public GoogleMapAPIException() {
        super("Invalid Response from Google Map API");
    }
}
