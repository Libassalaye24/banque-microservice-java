package com.banque.client.service.exception;

import com.banque.common.service.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler{

    // Gestion d'une exception spécifique
    @ResponseBody
    @ExceptionHandler(InscriptionException.class)
    @ResponseStatus(HttpStatus.OK)
    public  ErrorDTO handleYourException(InscriptionException ex, WebRequest request) {
       return ErrorDTO.builder()
                .code(HttpStatus.OK.getReasonPhrase())
                .message(ex.getMessage())
                .build();
        // Renvoyez une réponse avec le corps de l'erreur et le statut approprié
    }

    @ResponseBody
    @ExceptionHandler(PaiementException.class)
    @ResponseStatus(HttpStatus.OK)
    public ErrorDTO handleYourException(PaiementException ex, WebRequest request) {
        return ErrorDTO.builder()
                .code(HttpStatus.OK.getReasonPhrase())
                .message(ex.getMessage())
                .build();
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

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Handle the validation exception and return a response (e.g., error message)
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

