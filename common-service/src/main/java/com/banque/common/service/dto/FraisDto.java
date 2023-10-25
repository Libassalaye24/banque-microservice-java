package com.banque.common.service.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FraisDto {
    private UUID clientId;

    private BigDecimal montant;

    private String date;

    private String number;
}
