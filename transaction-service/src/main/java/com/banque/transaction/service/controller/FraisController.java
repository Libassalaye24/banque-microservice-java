package com.banque.transaction.service.controller;

import com.banque.transaction.service.repository.FraisRepository;
import com.banque.transaction.service.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/ts/frais")
public class FraisController {

    private final FraisRepository fraisRepository;

    public FraisController(FraisRepository fraisRepository) {
        this.fraisRepository = fraisRepository;
    }
    @GetMapping()
    public ResponseEntity findAll() {
        return new ResponseEntity(fraisRepository.findAll() , HttpStatus.OK);
    }

  


}
