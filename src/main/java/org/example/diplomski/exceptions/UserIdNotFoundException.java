package org.example.diplomski.exceptions;

public class UserIdNotFoundException extends RuntimeException  {

 public UserIdNotFoundException(String id) { super("User with id " + id + " not found"); }
}
