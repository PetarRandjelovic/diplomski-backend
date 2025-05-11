package org.example.diplomski.exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(long message) {
        super("Post by id " + message + " not found");
    }
}
