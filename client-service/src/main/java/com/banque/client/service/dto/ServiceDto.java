package com.banque.client.service.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceDto {

    private UUID id;

    @NotBlank(message = "Le libelle est obligatoire")
    private String libelle;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "500.00" ,  message = "Le prix doit etre supérieur ou égal à {value}")
    @DecimalMax(value = "1000000.00" , message = "Le prix doit etre inférieur ou égal à {value}")
    private BigDecimal prix;

}
