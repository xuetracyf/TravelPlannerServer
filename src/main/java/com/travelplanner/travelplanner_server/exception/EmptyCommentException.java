package com.travelplanner.travelplanner_server.exception;

public class EmptyCommentException extends RuntimeException {
    public EmptyCommentException() {
<<<<<<< HEAD
        super("Empty comment(s).");

=======
        super("Comment cannot be empty.");
>>>>>>> fa059b6... basic comment API + test done
    }
}
