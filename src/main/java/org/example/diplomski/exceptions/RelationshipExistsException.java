package org.example.diplomski.exceptions;

public class RelationshipExistsException extends RuntimeException {
    public RelationshipExistsException(String emailSender, String emailReceiver) {
        super("Request already exist by "+emailSender+" and "+emailReceiver);
    }
}
