package com.banque.transaction.service.exception;

import com.banque.common.service.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    // Gestion d'une exception spécifique
    @ResponseBody
    @ExceptionHandler(CompteException.class)
    @ResponseStatus(HttpStatus.OK)
    public ErrorDTO handleYourException(CompteException ex, WebRequest request) {
       return ErrorDTO.builder()
                .code(HttpStatus.OK.getReasonPhrase())
                .message(ex.getMessage())
                .build();
        // Renvoyez une réponse avec le corps de l'erreur et le statut approprié
    }



    // Gestion d'autres exceptions...

    // Gestion des exceptions par défaut
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleDefaultException(Exception ex, WebRequest request) {
        return ErrorDTO.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }
}

