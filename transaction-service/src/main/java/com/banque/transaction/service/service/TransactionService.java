package com.banque.transaction.service.service;

import com.banque.common.service.dto.PaiementDto;
import com.banque.common.service.dto.TransactionDto;
import com.banque.common.service.event.CompteStatus;
import com.banque.common.service.event.PaiementEvent;
import com.banque.common.service.event.TransactionEvent;
import com.banque.transaction.service.entity.CompteClient;
import com.banque.transaction.service.entity.Frais;
import com.banque.transaction.service.entity.Transaction;
import com.banque.transaction.service.exception.CompteException;
import com.banque.transaction.service.outil.UniqueCodeGenerator;
import com.banque.transaction.service.repository.CompteRepository;
import com.banque.transaction.service.repository.FraisRepository;
import com.banque.transaction.service.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CompteRepository compteRepository;
    private final FraisRepository fraisRepository;
    public TransactionService(TransactionRepository transactionRepository, CompteRepository compteRepository, FraisRepository fraisRepository) {
        this.transactionRepository = transactionRepository;
        this.compteRepository = compteRepository;
        this.fraisRepository = fraisRepository;
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Transactional
    public TransactionEvent saveTransaction(PaiementEvent paiementEvent) {
            CompteClient compte = checkCompte(paiementEvent.getPaiementDto().getClientId());

            PaiementDto paiementDto = paiementEvent.getPaiementDto();
            TransactionEvent transactionEvent = null;
            if (compte.getSolde().compareTo(paiementEvent.getPaiementDto().getMontant()) >= 0) {
                Transaction transaction = new Transaction(paiementDto.getClientId(),paiementDto.getMontant(),new Date(),UniqueCodeGenerator.generateUniqueCodeByDate(),paiementDto.getId());
                transactionRepository.save(transaction);
                BigDecimal frais = saveFrais(transaction);
                reglerMontant(compte,paiementEvent.getPaiementDto().getMontant() , frais);
                
                transactionEvent = new TransactionEvent(paiementDto,CompteStatus.UPDATED);
            }else {
                transactionEvent = new TransactionEvent(paiementDto,CompteStatus.ERROR_COMPTE);
            }

            return transactionEvent;
    }

    public CompteClient checkCompte(UUID clientId) {
        Optional<CompteClient> compte = compteRepository.findByClientId(clientId);

        return compte.orElseThrow(()-> new CompteException("Could not find compte"));
    }


    public BigDecimal saveFrais(Transaction transaction) {
        BigDecimal amount = transaction.getMontant().multiply(BigDecimal.valueOf(0.9)).divide(BigDecimal.valueOf(100));
        Frais frais = new Frais(null ,amount,transaction.getNumber());
        fraisRepository.save(frais);
        return amount;
    }
    public void checkAmount(CompteClient compteClient , BigDecimal amount) {
        boolean check = compteClient.getSolde().compareTo(amount) >= 0;
        if (!check) {
            throw new CompteException("Le solde du compte est insuffisant pour payer le service");
        }
    }

    public void reglerMontant(CompteClient compteClient , BigDecimal amount , BigDecimal frais) {
        compteClient.setSolde(compteClient.getSolde().subtract(amount).subtract(frais));
    }

    public List<TransactionDto> findAll() {
        return transactionRepository.findAll()
                .stream().map(tr -> new TransactionDto(
                      new SimpleDateFormat("dd/MM/yyyy" ).format(tr.getDate()),
                        tr.getClientId(),
                        tr.getMontant()
                )).toList();
    }

    public List<TransactionDto> findAllByClient(UUID clientId) {
        return transactionRepository.findAllByClientId(clientId)
                .stream().map(tr -> new TransactionDto(
                        new SimpleDateFormat("dd/MM/yyyy" ).format(tr.getDate()),
                        tr.getClientId(),
                        tr.getMontant()
                )).toList();
    }

    
   
}
