package com.banque.client.service.service;

import com.banque.client.service.dto.ServiceDto;
import com.banque.client.service.exception.PaiementException;
import com.banque.client.service.mapper.ServiceMapper;
import com.banque.client.service.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public ServiceService(ServiceRepository serviceRepository, ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    public com.banque.client.service.entity.Service save(ServiceDto serviceDto)
    {
        Optional<com.banque.client.service.entity.Service> service = serviceRepository.findByLibelle(serviceDto.getLibelle());
        if (service.isEmpty()) {
            return serviceRepository.save(serviceMapper.DtoToEntity(serviceDto));
        }
      throw new PaiementException("Cet service exite deja!");
    }

    public List<ServiceDto> findAll() {
        return serviceRepository.findAll()
                .stream()
                .map(s-> new ServiceDto(
                        s.getId(),
                        s.getLibelle(),
                        s.getPrix()
                )).toList();
    }
}
