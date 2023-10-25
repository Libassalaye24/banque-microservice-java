package com.banque.client.service.mapper;

import com.banque.client.service.dto.ServiceDto;
import com.banque.client.service.entity.Service;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {

    public Service DtoToEntity(ServiceDto serviceDto) {
        return Service.builder()
                .id(serviceDto.getId())
                .libelle(serviceDto.getLibelle())
                .prix(serviceDto.getPrix())
                .build();
    }

    public ServiceDto EntityToDto(Service service) {
        return ServiceDto.builder()
                .id(service.getId())
                .libelle(service.getLibelle())
                .prix(service.getPrix())
                .build();
    }


}
