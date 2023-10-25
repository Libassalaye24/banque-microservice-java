package com.banque.transaction.service.config;


import com.banque.common.service.event.ClientEvent;
import com.banque.common.service.event.CompteEvent;
import com.banque.common.service.event.PaiementEvent;
import com.banque.common.service.event.TransactionEvent;
import com.banque.transaction.service.service.CompteService;
import com.banque.transaction.service.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Slf4j
@Component
public class CompteServiceKafka {
    private static final String compteTopic = "compte-event-topic";
    private static final String transactionTopic = "transaction-event-topic";

    private final CompteService compteService;
    private final TransactionService transactionService;
    private final KafkaTemplate<String, CompteEvent> kafkaTemplate;
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplateP;

    public CompteServiceKafka(CompteService compteService, TransactionService transactionService, KafkaTemplate<String, CompteEvent> kafkaTemplate, KafkaTemplate<String, TransactionEvent> kafkaTemplateP) {
        this.compteService = compteService;
        this.transactionService = transactionService;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplateP = kafkaTemplateP;
    }


    @KafkaListener(topics = "client-event-topic", groupId = "default", containerFactory
            = "ClientEventListner")
    public void consumeMessage(ClientEvent clientEvent) throws JsonProcessingException, ParseException {
        log.info("message consumed {}", clientEvent.getEventId());
        try {
            CompteEvent compteEvent = compteService.saveCompte(clientEvent);
            kafkaTemplate.send(compteTopic, compteEvent);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @KafkaListener(topics = "paiement-event-topic", groupId = "default", containerFactory
            = "PaiementEventListner")
    public void consumeMessage2(PaiementEvent paiementEvent) throws JsonProcessingException, ParseException {
        log.info("message paiementEvent consumed {}", paiementEvent.getEventId());
        try {

            log.info("amount is ok {}", paiementEvent.getEventId());
            TransactionEvent transactionEvent = transactionService.saveTransaction(paiementEvent);
            kafkaTemplateP.send(transactionTopic, transactionEvent);

        }catch (Exception e){
            e.printStackTrace();
        }


    }




}
