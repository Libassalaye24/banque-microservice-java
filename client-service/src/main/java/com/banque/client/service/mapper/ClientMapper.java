package com.banque.client.service.mapper;

import com.banque.client.service.dto.ClientListDto;
import com.banque.client.service.entity.Client;

import com.banque.common.service.dto.ClientDto;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class ClientMapper {

    public Client clientDtoToClientEntity(ClientDto clientDto) throws ParseException {
        return Client.builder()
                .id(clientDto.getId())
                .nomComplet(clientDto.getNomComplet())
                .telephone(clientDto.getTelephone())
                .clientStatus(clientDto.getClientStatus())
                .compteStatus(clientDto.getCompteStatus())
                .build();

    }

    public ClientDto clientEntityToClientDto(Client client) throws ParseException {

        return ClientDto.builder()
                .id(client.getId())
                .nomComplet(client.getNomComplet())
                .clientStatus(client.getClientStatus())
                .build();
    }

    public ClientListDto EntityToDtoList(Client client)  {

        return ClientListDto.builder()
                .id(client.getId())
                .nomComplet(client.getNomComplet())
                .clientStatus(client.getClientStatus())
                .build();
    }

}
