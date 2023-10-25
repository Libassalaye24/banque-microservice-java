package com.banque.client.service.exception;

public class PaiementException extends InscriptionException {
    public PaiementException(String message) {
        super(message);
    }

    public PaiementException(String message, Throwable cause) {
        super(message, cause);
    }
}
