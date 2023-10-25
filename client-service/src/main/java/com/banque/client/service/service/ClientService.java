package com.banque.client.service.service;


import com.banque.client.service.dto.ClientListDto;
import com.banque.client.service.entity.Client;
import com.banque.client.service.exception.InscriptionException;
import com.banque.client.service.mapper.ClientMapper;
import com.banque.client.service.repository.ClientRepository;
import com.banque.common.service.dto.ClientDto;
import com.banque.common.service.event.ClientEvent;
import com.banque.common.service.event.ClientStatus;
import com.banque.common.service.event.CompteEvent;
import com.banque.common.service.event.CompteStatus;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {
    private static final String clientTopic = "client-event-topic";
    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;
    private final KafkaTemplate<String, ClientEvent> kafkaTemplate;

    public ClientService(ClientMapper clientMapper, ClientRepository clientRepository, KafkaTemplate<String, ClientEvent> kafkaTemplate) {
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<ClientListDto> findAll(){
       return clientRepository.findAll().stream()
                .map(c -> new ClientListDto(
                        c.getId(),
                        c.getNomComplet(),
                        c.getTelephone(),
                        c.getClientStatus(),
                        c.getCompteStatus()
                ))
                .toList();
    }

    public ClientListDto findByPhone(String telephone) {
        Optional<Client> client = clientRepository.findByTelephone(telephone);
        if (client.isEmpty()) {
            return  null;
        }
        return clientMapper.EntityToDtoList(client.get());
    }

    public ClientListDto find(UUID id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            return  null;
        }
        return clientMapper.EntityToDtoList(client.get());
    }


    @Transactional
    public Client saveClient(ClientDto clientDto) throws ParseException {
        Optional<Client> clientt = clientRepository.findByTelephone(clientDto.getTelephone());
        if (clientt.isEmpty()) {
            clientDto.setClientStatus(ClientStatus.CREATED);
            clientDto.setCompteStatus(CompteStatus.INIT);
            Client client = clientRepository.save(clientMapper.clientDtoToClientEntity(clientDto));
            clientDto.setId(client.getId());
            ClientEvent clientEvent = new ClientEvent(clientDto , ClientStatus.CREATED);
            //send to kafka for update age
            kafkaTemplate.send(clientTopic, clientEvent);
            return client;
        }else {
            throw new InscriptionException("Un client avec ce numero de telephone existe deja!");
        }
      
    }

    @Transactional
    public void updateClient(CompteEvent compteEvent) {
        Optional<Client> client = clientRepository.findById(compteEvent.getCompteDto().getClientId());
        if(client.isPresent()){
            boolean isAgeSaved = CompteStatus.UPDATED.equals(compteEvent.getCompteStatus());
            ClientStatus clientStatus = isAgeSaved?ClientStatus.COMPLETED:ClientStatus.ERROR_COMPTE;
            client.get().setClientStatus(clientStatus);
            client.get().setCompteStatus(CompteStatus.UPDATED);
            if(!isAgeSaved){
                client.get().setCompteStatus(CompteStatus.ERROR_COMPTE);
            }
        }else {
            throw new InscriptionException("Client is not found");
        }
    }


}
