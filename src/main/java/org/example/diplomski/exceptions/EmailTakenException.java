package org.example.diplomski.exceptions;

public class EmailTakenException extends RuntimeException{
    public EmailTakenException(String email) {
        super(String.format("User with email '%s' already exists", email));
    }
}
