package com.banque.common.service.dto;

import com.banque.common.service.event.CompteStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaiementDto {

    private UUID id;

    // @NotNull(message = "La date de paiement est obligatoire")
    private String date;

    @NotNull(message = "Le service est obligatoire")
    private UUID serviceId;
    @NotNull(message = "Le client est obligatoire")
    private UUID clientId;
    // @NotNull(message = "Le montant est obligatoire")
    private BigDecimal montant;

    private CompteStatus status;

}
