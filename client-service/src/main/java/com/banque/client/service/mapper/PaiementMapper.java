package com.banque.client.service.mapper;

import com.banque.client.service.entity.Client;
import com.banque.client.service.entity.Paiement;
import com.banque.client.service.entity.Service;
import com.banque.client.service.exception.PaiementException;
import com.banque.client.service.repository.ClientRepository;
import com.banque.client.service.repository.ServiceRepository;
import com.banque.common.service.dto.PaiementDto;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
public class PaiementMapper {

    private final ServiceRepository serviceRepository;
    private final ClientRepository clientRepository;

    public PaiementMapper(ServiceRepository serviceRepository, ClientRepository clientRepository) {
        this.serviceRepository = serviceRepository;
        this.clientRepository = clientRepository;
    }

//    public Paiement DtoToEntity(PaiementDto paiementDto) throws ParseException {
//        Optional<Service> service = serviceRepository.findById(paiementDto.getServiceId());
//        Optional<Client> client = clientRepository.findById(paiementDto.getClientId());
//        return Paiement.builder()
//                .id(paiementDto.getId())
//                .date(new SimpleDateFormat("yyyy-MM-dd").parse(paiementDto.getDate()))
//                .service(service.get())
//                .client(client.get())
//                .montant(paiementDto.getMontant())
//                .status(paiementDto.getStatus())
//                .build();
//    }
public Paiement DtoToEntity(PaiementDto paiementDto)  {
    Optional<Service> service = serviceRepository.findById(paiementDto.getServiceId());
    Optional<Client> client = clientRepository.findById(paiementDto.getClientId());

    Date date;
    try {
        date = new SimpleDateFormat("yyyy-MM-dd").parse(paiementDto.getDate());
    } catch (ParseException ex) {
        // Gérer l'erreur de parsing de date ici (par exemple, renvoyer une valeur par défaut)
        throw new PaiementException("Format de date invalide .");
    }

    return Paiement.builder()
            .id(paiementDto.getId())
            .date(date)
            .service(service.orElseThrow(() -> new PaiementException("Service introuvable.")))
            .client(client.orElseThrow(() -> new PaiementException("Client introuvable.")))
            .montant(paiementDto.getMontant())
            .status(paiementDto.getStatus())
            .build();
}


    public PaiementDto EntityToDto(Paiement paiement) {
        return PaiementDto.builder()
                .id(paiement.getId())
                .date(new SimpleDateFormat("dd/MM/yyyy").format(paiement.getDate()))
                .serviceId(paiement.getService().getId())
                .clientId(paiement.getClient().getId())
                .montant(paiement.getMontant())
                .status(paiement.getStatus())
                .build();

    }
}
