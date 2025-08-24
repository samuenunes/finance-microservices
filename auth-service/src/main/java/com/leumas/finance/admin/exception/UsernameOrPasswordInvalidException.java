package com.leumas.finance.admin.exception;

public class UsernameOrPasswordInvalidException extends RuntimeException {

    public UsernameOrPasswordInvalidException(String message) {
        super(message);
    }
}
