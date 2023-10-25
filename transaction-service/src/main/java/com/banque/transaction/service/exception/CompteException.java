package com.banque.transaction.service.exception;

public class CompteException extends RuntimeException  {

    public CompteException(String message) {
        super(message);
    }

    public CompteException(String message, Throwable cause) {
        super(message, cause);
    }
}
