package com.banque.common.service.dto;

import com.banque.common.service.event.CompteStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {

    private String date;

    private UUID clientId;

    private BigDecimal montant;

}
