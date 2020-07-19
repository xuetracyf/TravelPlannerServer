package com.travelplanner.travelplanner_server.exception;

public class EmptyCommentException extends RuntimeException {
    public EmptyCommentException() {
        super("Comment cannot be empty.");
    }
}
