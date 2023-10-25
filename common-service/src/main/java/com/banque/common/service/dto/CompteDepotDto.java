package com.banque.common.service.dto;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompteDepotDto {

    @Pattern(regexp = "\\+\\d{1,3}-\\d{9,15}", message = "le format du numéro téléphone est incorrecte. Utilisez ce format: +XXX-XXXXXXXXXXX")
    private String telephone;

    @DecimalMin(value = "2500.00" , message = "Veuillez saisir une valeur supérieur ou égal à {value}")
    @DecimalMax(value = "2000000.00",message = "Veuillez saisir une valeur inférieur ou égal à {value}")
    private BigDecimal solde;
}
