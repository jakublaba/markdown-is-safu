package com.krabelard.safeapp.exception.user;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String email) {
        super(String.format("[%s] - this is not a valid email", email));
    }

}
