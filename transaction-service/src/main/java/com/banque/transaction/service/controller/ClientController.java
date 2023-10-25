package com.banque.transaction.service.controller;

import com.banque.common.service.dto.CompteDepotDto;
import com.banque.common.service.dto.CompteDto;
import com.banque.transaction.service.exception.CompteException;
import com.banque.transaction.service.service.CompteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ts/compte")
public class ClientController {
    private final CompteService compteService;

    public ClientController(CompteService compteService) {
        this.compteService = compteService;
    }


    @PostMapping("/depot1")
    public ResponseEntity<CompteDto> depot1(@Valid @RequestBody CompteDto compteDto) {
        CompteDto compteDto1 = compteService.depot(compteDto);
        return new ResponseEntity<CompteDto>(compteDto1, HttpStatus.CREATED);
    }

    @PostMapping("/depot")
    public ResponseEntity<CompteDto> depot(@Valid @RequestBody CompteDepotDto compteDepotDto) {

        try {
            CompteDto compteDto1 = compteService.testDepot(compteDepotDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(compteDto1.getClientId()).toUri();

            return ResponseEntity.created(location).build();
        }catch (CompteException ex) {
            throw ex;
        }
//        return new ResponseEntity(compteDto1, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<List<CompteDto>> findAll(){
        List<CompteDto> ages = compteService.findAll();
        return new ResponseEntity<List<CompteDto>>(ages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<CompteDto> find(@PathVariable UUID id){
        CompteDto compteDto = compteService.find(id);
        return new ResponseEntity<CompteDto>(compteDto, HttpStatus.OK);
    }


    @ResponseBody
    @GetMapping("/client/{clientId}")
    public ResponseEntity<CompteDto> findByClientId(@PathVariable UUID clientId){
            CompteDto compteDto = compteService.findByClientId(clientId);
            return new ResponseEntity<CompteDto>(compteDto, HttpStatus.OK);

    }
    @ResponseBody
    @GetMapping("/clientId/{clientId}")
    public ResponseEntity<CompteDto> findByClientIdApi(@PathVariable UUID clientId){
        CompteDto compteDto = compteService.findByClientId(clientId);
        return new ResponseEntity<CompteDto>(compteDto, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("bloquer-compte/{number}")
    public ResponseEntity<Void> bloquerCompte(@PathVariable String number) {
        try {
            compteService.activeOrdesactiveCompte(number , false);
            return ResponseEntity.noContent().build();
        } catch (CompteException e) {
            throw e;
        }
    }

    @ResponseBody
    @GetMapping("active-compte/{number}")
    public ResponseEntity<Void> activerCompte(@PathVariable String number) {
        try {
            compteService.activeOrdesactiveCompte(number , true);
            return ResponseEntity.noContent().build();
        } catch (CompteException e) {
            throw e;
        }
    }

}
