package com.assignment.musiclibrary.exceptions;

public class ForbiddenOperationException extends RuntimeException {
    public ForbiddenOperationException() {
        super("Forbidden operation: You cannot create an admin user.");
    }
}