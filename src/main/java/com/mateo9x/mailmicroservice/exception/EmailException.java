package com.mateo9x.mailmicroservice.exception;

public class EmailException extends RuntimeException {

    public EmailException(String message) {
        super(message);
    }
}
