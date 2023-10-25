package com.banque.transaction.service.service;

import com.banque.common.service.dto.ClientDto;
import com.banque.common.service.dto.CompteDepotDto;
import com.banque.common.service.dto.CompteDto;
import com.banque.common.service.event.*;
import com.banque.transaction.service.entity.CompteClient;
import com.banque.transaction.service.exception.CompteException;
import com.banque.transaction.service.mapper.CompteMapper;
import com.banque.transaction.service.outil.UniqueCodeGenerator;
import com.banque.transaction.service.repository.CompteRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompteService {

    private final CompteRepository compteRepository;
    private final CompteMapper compteMapper;
    private final WebClient webClient;

    public CompteService(WebClient.Builder webClientBuilder , CompteRepository compteRepository , CompteMapper compteMapper) {
        this.webClient = webClientBuilder.build();
        this.compteRepository = compteRepository;
        this.compteMapper = compteMapper;
    }

    private final String CLIENT_API_URL = "http://localhost:8081";

    @Transactional
    public CompteEvent saveCompte(ClientEvent clientEvent) {
        ClientDto clientDto = clientEvent.getClientDto();
        CompteEvent compteEvent = null;
        CompteDto compteDto = new CompteDto(clientDto.getId() , clientDto.getSolde() ,UniqueCodeGenerator.generateUniqueCodeUUID() , true);
        try {
            CompteClient compteClient = new CompteClient(clientDto.getId() , clientDto.getSolde() , compteDto.getNumber() , compteDto.isActive());
            compteRepository.save(compteClient);
            compteEvent = new CompteEvent(compteDto,CompteStatus.UPDATED);
        }catch (Exception e) {
            compteEvent = new CompteEvent(compteDto,CompteStatus.ERROR_COMPTE);
        }
        return compteEvent;
    }

    @Transactional
    public CompteDto depot(CompteDto compteDto) {
        Optional<CompteClient> compteClient = compteRepository.findByClientId(compteDto.getClientId());
        if (compteClient.isEmpty()) {
            return null;
        }
        CompteClient compte = compteClient.get();
        compte.setSolde(compte.getSolde().add(compteDto.getSolde()));
        return compteMapper.EntityToDto(compte);
    }

      @Transactional
    public CompteDto depotWithNumber(CompteDto compteDto) {
        Optional<CompteClient> compteClient = compteRepository.findByNumber(compteDto.getNumber());
        if (compteClient.isEmpty()) {
            return null;
        }
        CompteClient compte = compteClient.get();
        compte.setSolde(compte.getSolde().add(compteDto.getSolde()));
        return compteMapper.EntityToDto(compte);
    }

    public Mono<ClientDto> getClient(String number) {
        return webClient
                .get()
                .uri(CLIENT_API_URL+"/api/cs/client/phone/{phone}" , number)
                .retrieve()
                .bodyToMono(ClientDto.class)
                ;
    }

    public CompteDto testDepot(CompteDepotDto compteDepotDto) {
        Mono<ClientDto> client = this.getClient(compteDepotDto.getTelephone());
        client.flatMap(clientDto -> {
            CompteDto compteDto = this.findByClientId(clientDto.getId());
            return Mono.just(this.depot(compteDto));

        }).switchIfEmpty(Mono.error(new CompteException("Le numero de telephone saisi n'est liee a aucun client!")));
        return  null;
    }

//    public void checkNumberClientCompte(CompteDepotDto compteDepotDto) {
//        CompteDepotEvent compteDepotEvent = new CompteDepotEvent();
//        compteDepotEvent.setCompteDto(compteDepotDto);
//        compteDepotEvent.setCompteStatus(CompteStatus.COMPLETED);
//        kafkaTemplate.send("checknumber-event-topic" ,compteDepotEvent);
//    }


    public List<CompteDto> findAll() {

        return compteRepository.findAll()
                .stream()
                .map(p -> new CompteDto(
                        p.getClientId()
                        ,p.getSolde(),
                        p.getNumber(),
                        p.isActive()
                )).toList();
    }

    public CompteDto find(UUID clientId) {
        Optional<CompteClient> compteClient = compteRepository.findById(clientId);
        if (compteClient.isEmpty()) {
            return null;
        }
        return compteMapper.EntityToDto(compteClient.get());
    }



    public CompteDto findByClientId(UUID clientId) {
        Optional<CompteClient> compteClient = compteRepository.findByClientId(clientId);
        return compteClient.map(compteMapper::EntityToDto).orElse(null);
    }

    public boolean checkSolde(UUID clientId , BigDecimal montant) {
        CompteDto compteDto = findByClientId(clientId);
        if (compteDto != null) {
            return compteDto.getSolde().compareTo(montant) >= 0;
        }
        return false;
    }

    @Transactional
    public void activeOrdesactiveCompte(String compte , boolean etat) {
        compteRepository.findByNumber(compte).ifPresentOrElse(
            cl -> cl.setActive(etat),
            () -> { throw new CompteException("Compte with number : " + compte + " is not found"); }
        );
    }
    // @Transactional
    // public CompteDto activeOrdesactiveCompte(String compte , boolean etat) {
    //     Optional<CompteClient> client = compteRepository.findByNumber(compte);
    //     if (client.isEmpty()) {
    //         throw new CompteException("Compte with number "+compte+" is not found");
    //     }
    //     CompteClient cl = client.get();
    //      cl.setActive(etat);
    //     return compteMapper.EntityToDto(cl);
    // }
  
//    @Transactional
//    public void sendCompteClient(PaiementEvent paiementEvent) {
//        Optional<CompteClient> compteClient = compteRepository.findById(paiementEvent.getPaiementDto().getClientId());
//        CompteClientCheckEvent compteClientCheckEvent = null;
//        CompteDto compteDto = null;
//        if (compteClient.isPresent()) {
//             compteDto = compteMapper.EntityToDto(compteClient.get());
//            if (!(compteClient.get().getSolde().compareTo(paiementEvent.getPaiementDto().getMontant()) >= 0)) {
//                compteClientCheckEvent = new CompteClientCheckEvent(paiementEvent.getPaiementDto() ,CompteStatus.SOLDE_INSUFFISANT ,compteDto);
//            }else {
//                compteClientCheckEvent = new CompteClientCheckEvent(paiementEvent.getPaiementDto() ,CompteStatus.UPDATED , compteDto);
//            }
//        }else {
//            compteClientCheckEvent = new CompteClientCheckEvent(paiementEvent.getPaiementDto() ,CompteStatus.ERROR_COMPTE , compteDto);
//        }
////        kafkaTemplate.send("compteclientcheck-event-topic" ,compteClientCheckEvent);
//    }
}
