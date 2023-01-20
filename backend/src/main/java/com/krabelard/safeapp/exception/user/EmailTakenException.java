package com.krabelard.safeapp.exception.user;

public class EmailTakenException extends RuntimeException {

    public EmailTakenException(String email) {
        super(String.format("[%s] - email already in use", email));
    }

}
