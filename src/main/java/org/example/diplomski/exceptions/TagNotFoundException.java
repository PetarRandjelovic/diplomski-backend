package org.example.diplomski.exceptions;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(String message) {
        super(message);
    }
}
