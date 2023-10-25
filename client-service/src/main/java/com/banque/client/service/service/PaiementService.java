package com.banque.client.service.service;

import com.banque.client.service.entity.CancelTime;
import com.banque.client.service.entity.Client;
import com.banque.client.service.entity.Paiement;
import com.banque.client.service.exception.InscriptionException;
import com.banque.client.service.exception.PaiementException;
import com.banque.client.service.mapper.PaiementMapper;
import com.banque.client.service.repository.CancelTimeRepository;
import com.banque.client.service.repository.PaiementRepository;
import com.banque.client.service.repository.ServiceRepository;
import com.banque.common.service.dto.PaiementDto;
import com.banque.common.service.event.CompteStatus;
import com.banque.common.service.event.PaiementEvent;
import com.banque.common.service.event.TransactionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class PaiementService {

    private static final String URL_COMPTE = "http://localhost:8086";
    private static final String paiementTopic = "paiement-event-topic";
    private final PaiementMapper paiementMapper;
    private final PaiementRepository paiementRepository;
    private final ServiceRepository serviceRepository;
    private final KafkaTemplate<String, PaiementEvent> kafkaTemplate;
    private final CancelTimeRepository  cancelTimeRepository;

    // private final RestTemplate restTemplate;
    public PaiementService(PaiementMapper paiementMapper, PaiementRepository paiementRepository,
            ServiceRepository serviceRepository, KafkaTemplate<String, PaiementEvent> kafkaTemplate,CancelTimeRepository  cancelTimeRepository) {
        this.paiementMapper = paiementMapper;
        this.paiementRepository = paiementRepository;
        this.serviceRepository = serviceRepository;
        this.kafkaTemplate = kafkaTemplate;       
        this.cancelTimeRepository = cancelTimeRepository;


    }

    public List<PaiementDto> findAllByClient(Client client) {
        Optional<List<Paiement>> paiements = paiementRepository.findByClient(client);
        if (paiements.isEmpty()) {
            return null;
        }

        return paiements.get().stream()
                .map(p -> new PaiementDto(
                        p.getId(),
                        new SimpleDateFormat("dd/MM/yyyy").format(p.getDate()),
                        p.getService().getId(),
                        p.getClient().getId(),
                        p.getMontant(),
                        p.getStatus()))
                .toList();
    }

    public List<PaiementDto> findAll() {
        return paiementRepository.findAll().stream()
                .map(p2 -> new PaiementDto(
                        p2.getId(),
                        new SimpleDateFormat("dd/MM/yyyy").format(p2.getDate()),
                        p2.getService().getId(),
                        p2.getClient().getId(),
                        p2.getMontant(),
                        p2.getStatus()))
                .toList();
    }

    @Transactional
    public Paiement savePaiement(PaiementDto paiementDto) {
        // BigDecimal amount = paiementDto.getMontant();
        BigDecimal montantDuService = getServiceMontant(paiementDto.getServiceId());
        paiementDto.setMontant(montantDuService);
        paiementDto.setDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        paiementDto.setStatus(CompteStatus.CREATED);
        Paiement paiement = paiementRepository.save(paiementMapper.DtoToEntity(paiementDto));
        paiementDto.setId(paiement.getId());
        PaiementEvent paiementEvent = new PaiementEvent(paiementDto, CompteStatus.CREATED);
        // send to kafka for create transaction
        kafkaTemplate.send(paiementTopic, paiementEvent);
        return paiement;
       

    }

    private BigDecimal getServiceMontant(UUID serviceId) {
        Optional<com.banque.client.service.entity.Service> service = serviceRepository.findById(serviceId);
        return service.map(com.banque.client.service.entity.Service::getPrix)
                .orElseThrow(() -> new InscriptionException("Service introuvable"));
    }

    @Transactional
    public void cancelPaiement(UUID paiementId) {
        Paiement paiement = checkPayment(paiementId);
        CancelTime time = getTimeTocancelPayment();
        Date date = new Date();
        Long intervalTime = paiement.getDate().getTime() - date.getTime();
        if (intervalTime <= time.getTime()) {
            // canceled payment 
            
        }else {
            throw new PaiementException("Le temps "+time.getTime()+" pour annuler le paiement est depassé!");
        }
      
    }

    public Paiement checkPayment(UUID paiementId) {
        return paiementRepository.findById(paiementId)
                .orElseThrow(() -> new PaiementException("Paiement introuvable."));
    }
    public CancelTime getTimeTocancelPayment() {
        return  cancelTimeRepository.findByEtat(true)
                    .orElseThrow(()-> new PaiementException("Time not found"));
    }
    //
    // @Transactional
    // public Mono<Paiement> savePaiement(PaiementDto paiementDto) {
    // Mono<CompteDto> compteDtoMono =
    // this.getSoldeClient(paiementDto.getClientId());
    // return compteDtoMono.flatMap(compteDto -> {
    // boolean check = compteDto.getSolde().compareTo(paiementDto.getMontant()) >=
    // 0;
    // if (check) {
    // // Effectuez le traitement pour créer le paiement et envoyer l'événement
    // Kafka
    // // ...
    // paiementDto.setStatus(CompteStatus.CREATED);
    // Paiement paiement =
    // paiementRepository.save(paiementMapper.DtoToEntity(paiementDto));
    //
    // paiementDto.setId(paiement.getId());
    // PaiementEvent paiementEvent = new PaiementEvent(paiementDto ,
    // CompteStatus.CREATED);
    // // send to kafka for create transaction
    // kafkaTemplate.send(paiementTopic , paiementEvent);
    // return Mono.just(paiement);
    // } else {
    // throw new PaiementException("Le solde du compte est insuffisant pour payer le
    // service");
    // }
    // }).switchIfEmpty(Mono.error(new PaiementException("Ce client n'existe pas.
    // Veuillez créer un compte!")));
    // }

    // public Paiement processPayment(PaiementDto paiementDto) {
    // Optiona
    // return null;
    // }

    @Transactional
    public void updatePaiement(TransactionEvent transactionEvent) {
        Optional<Paiement> paiement = paiementRepository.findById(transactionEvent.getPaiementDto().getId());
        if (paiement.isPresent()) {
            boolean check = CompteStatus.UPDATED.equals(transactionEvent.getStatus());
            CompteStatus status = check ? CompteStatus.COMPLETED : CompteStatus.ERROR_TRANSACTION;

            paiement.get().setStatus(status);
            if (!check) {
                paiement.get().setStatus(CompteStatus.ERROR_TRANSACTION);
            }
        }

    }

    // public void checkSolde(CompteClientCheckEvent compteClientCheckEvent) {
    // PaiementDto paiementDto = compteClientCheckEvent.getPaiementDto();
    // CompteDto compteDto = compteClientCheckEvent.getCompteDto();
    // boolean checkIn = compteDto.getSolde().compareTo(paiementDto.getMontant()) >=
    // 0;
    //
    // if (compteClientCheckEvent.getStatus() == CompteStatus.UPDATED) {
    // Optional<com.banque.client.service.entity.Service> service =
    // serviceRepository.findById(paiementDto.getServiceId());
    // if (service.isPresent()) {
    //
    // }
    // }
    // }

    public PaiementDto find(UUID id) {
        Optional<Paiement> paiement = paiementRepository.findById(id);
        if (paiement.isEmpty()) {
            return null;
        }
        return paiementMapper.EntityToDto(paiement.get());
    }
}
