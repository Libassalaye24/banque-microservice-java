package com.banque.client.service.controller;


import com.banque.client.service.entity.Client;
import com.banque.client.service.entity.Paiement;
import com.banque.client.service.entity.Service;
import com.banque.client.service.exception.InscriptionException;
import com.banque.client.service.service.ClientService;
import com.banque.client.service.service.PaiementService;
import com.banque.client.service.service.ServiceService;
import com.banque.common.service.dto.ClientDto;
import com.banque.common.service.dto.PaiementDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.UUID;

@RequestMapping("/api/cs/client")
@RestController
public class ClientController {

    private final ClientService clientService;


    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @ResponseBody
    @GetMapping()
    public ResponseEntity getAll(){

        return new ResponseEntity(clientService.findAll(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable UUID id){

        return new ResponseEntity(clientService.find(id), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/phone/{phone}")
    public ResponseEntity getById(@PathVariable String phone) {

        return new ResponseEntity(clientService.findByPhone(phone), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping()
    public ResponseEntity<Object> save(@Valid @RequestBody ClientDto clientDto) throws ParseException {

        try {
            Client client = clientService.saveClient(clientDto);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(client.getId()).toUri();

            return ResponseEntity.created(location).build();
        }catch (InscriptionException ex) {
            throw ex;
        }

    }




}
