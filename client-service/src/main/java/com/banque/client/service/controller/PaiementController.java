package com.banque.client.service.controller;

import com.banque.client.service.entity.Client;
import com.banque.client.service.entity.Paiement;
import com.banque.client.service.exception.PaiementException;
import com.banque.client.service.service.PaiementService;
import com.banque.common.service.dto.PaiementDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.text.ParseException;
import java.util.UUID;

@RequestMapping("/api/cs/paiement")
@RestController
public class PaiementController {

    private final PaiementService paiementService;

    public PaiementController(PaiementService paiementService) {
        this.paiementService = paiementService;
    }

    @ResponseBody
    @PostMapping()
    public ResponseEntity save(@Valid  @RequestBody PaiementDto paiementDto) throws ParseException {
       try {
           Paiement paiement = paiementService.savePaiement(paiementDto);

           URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                   .path("/{id}").buildAndExpand(paiement.getId()).toUri();

           return ResponseEntity.created(location).build();
       }catch (PaiementException ex) {
           throw ex;
       }
    }

    @ResponseBody
    @GetMapping()
    public ResponseEntity findAll() {
        return new ResponseEntity(paiementService.findAll() , HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/client/{client}")
    public ResponseEntity findAllByClient(@PathVariable Client client) {
        return new ResponseEntity(paiementService.findAllByClient(client) , HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity findAllByClient(@PathVariable UUID id) {
        return new ResponseEntity(paiementService.find(id) , HttpStatus.OK);
    }


}
