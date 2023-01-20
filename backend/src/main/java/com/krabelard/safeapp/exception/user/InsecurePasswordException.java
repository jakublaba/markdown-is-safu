package com.krabelard.safeapp.exception.user;

public class InsecurePasswordException extends RuntimeException {

    public InsecurePasswordException() {
        super("Password must be between 8 and 128 characters and contain at least 1 lowercase and uppercase letter, 1 digit and 1 special character");
    }

}
