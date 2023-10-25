package com.banque.common.service.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
public class CompteDto {

    @NotNull(message = "Le client id est obligatoire")
    private UUID clientId;

    @DecimalMin(value = "2500.00" , message = "Veuillez saisir une valeur supérieur ou égal à {value}")
    @DecimalMax(value = "2000000.00",message = "Veuillez saisir une valeur inférieur ou égal à {value}")
    private BigDecimal solde;

    private String number;

    private boolean isActive;
}
