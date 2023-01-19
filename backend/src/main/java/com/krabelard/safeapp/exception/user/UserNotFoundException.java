package com.krabelard.safeapp.exception.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super(String.format("[%s] - user does not exist", username));
    }

}
