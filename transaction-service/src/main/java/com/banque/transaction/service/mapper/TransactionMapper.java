package com.banque.transaction.service.mapper;

import com.banque.common.service.dto.TransactionDto;
import com.banque.transaction.service.entity.Transaction;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TransactionMapper {

    public TransactionDto EntityToDto(Transaction transaction) {
      return TransactionDto.builder()
                .clientId(transaction.getClientId())
                .montant(transaction.getMontant())
                .date(new SimpleDateFormat("dd/MM/yyyy").format(transaction.getDate()))
                .build();
    }

    public Transaction DtoToEntity(TransactionDto transactionDto) throws ParseException {
        return Transaction.builder()
                .clientId(transactionDto.getClientId())
                .montant(transactionDto.getMontant())
                .date(new SimpleDateFormat("dd/MM/yyyy").parse(transactionDto.getDate()))
                .build();
    }
}
