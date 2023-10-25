package com.banque.transaction.service.mapper;

import com.banque.common.service.dto.CompteDto;
import com.banque.transaction.service.entity.CompteClient;
import org.springframework.stereotype.Component;

@Component
public class CompteMapper {

    public CompteDto EntityToDto(CompteClient compteClient) {
        return CompteDto.builder()
                .clientId(compteClient.getClientId())
                .solde(compteClient.getSolde())
                .number(compteClient.getNumber())
                .isActive(compteClient.isActive())
                .build();
    }

    public CompteClient DtoToEntity(CompteDto compteDto) {
        return CompteClient.builder()
                .clientId(compteDto.getClientId())
                .solde(compteDto.getSolde())
                .number(compteDto.getNumber())
                .isActive(compteDto.isActive())
                .build();
    }
}
