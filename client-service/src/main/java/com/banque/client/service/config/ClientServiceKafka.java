package com.banque.client.service.config;


import com.banque.client.service.service.ClientService;
import com.banque.client.service.service.PaiementService;
import com.banque.common.service.event.CompteEvent;
import com.banque.common.service.event.TransactionEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientServiceKafka {
    private final ClientService clientService;
    private final PaiementService paiementService;

    public ClientServiceKafka(ClientService clientService, PaiementService paiementService) {

        this.clientService = clientService;
        this.paiementService = paiementService;
    }


    @KafkaListener(topics = "compte-event-topic", groupId = "default", containerFactory
            = "CompteEventListner")
    public void getResponse(CompteEvent compteEvent) throws JsonProcessingException {

        clientService.updateClient(compteEvent);
    }

    @KafkaListener(topics = "transaction-event-topic", groupId = "default", containerFactory
            = "TransactionEventListner")
    public void getResponseTransaction(TransactionEvent transactionEvent) throws JsonProcessingException {

        paiementService.updatePaiement(transactionEvent);
    }

//    @KafkaListener(topics = "checknumber-event-topic", groupId = "default", containerFactory
//            = "CompteClientCheckEventListner")
//    public void getResponseNumberClientCheck(CompteDepotEvent CompteDepotEvent) throws JsonProcessingException {
//
//        paiementService.updatePaiement(transactionEvent);
//
//    }
}
