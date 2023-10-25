package com.banque.transaction.service.controller;

import com.banque.transaction.service.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/ts/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @GetMapping()
    public ResponseEntity findAll() {
        return new ResponseEntity(transactionService.findAll() , HttpStatus.OK);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity findAllByClient(@PathVariable UUID clientId) {
        return new ResponseEntity(transactionService.findAllByClient(clientId) , HttpStatus.OK);
    }


}
