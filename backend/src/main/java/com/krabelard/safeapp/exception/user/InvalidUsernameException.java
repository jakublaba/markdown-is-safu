package com.krabelard.safeapp.exception.user;

public class InvalidUsernameException extends RuntimeException {

    public InvalidUsernameException(String username) {
        super(String.format("[%s] - invalid username, only letters and '_' are allowed", username));
    }

}
