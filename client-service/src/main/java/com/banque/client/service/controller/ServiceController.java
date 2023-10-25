package com.banque.client.service.controller;

import com.banque.client.service.dto.ServiceDto;
import com.banque.client.service.entity.Service;
import com.banque.client.service.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping("/api/cs/service")
@RestController
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @ResponseBody
    @PostMapping()
    public ResponseEntity<Object> saveService(@Valid @RequestBody ServiceDto serviceDto)
    {
        Service service = serviceService.save(serviceDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(service.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @ResponseBody
    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity(serviceService.findAll(), HttpStatus.OK);
    }


}
