package com.krabelard.safeapp.exception.user;

public class UsernameTakenException extends RuntimeException {

    public UsernameTakenException(String username) {
        super(String.format("[%s] - username already in use", username));
    }

}
