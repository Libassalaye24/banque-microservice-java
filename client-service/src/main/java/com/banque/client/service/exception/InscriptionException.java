package com.banque.client.service.exception;

import java.util.function.Supplier;

public class InscriptionException extends RuntimeException  {

    public InscriptionException(String message) {
        super(message);
    }

    public InscriptionException(String message, Throwable cause) {
        super(message, cause);
    }


}
