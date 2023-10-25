package com.banque.common.service.dto;

import com.banque.common.service.event.ClientStatus;
import com.banque.common.service.event.CompteStatus;
import jakarta.validation.constraints.*;
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
public class ClientDto {

    private UUID id;

    @NotBlank(message = "Nom complet est obligatoire ")
    @NotNull(message = "Nom complet est obligatoire ")
    private String nomComplet;

    @Pattern(regexp = "\\+\\d{1,3}-\\d{9,15}", message = "le format du numéro téléphone est incorrecte. Utilisez ce format: +XXX-XXXXXXXXXXX")
    private String telephone;

    private ClientStatus clientStatus;

    private CompteStatus compteStatus;

    @DecimalMin(value = "5000.00" , message = "Veuillez saisir une valeur supérieur ou égal à {value}")
    @DecimalMax(value = "2000000.00",message = "Veuillez saisir une valeur inférieur ou égal à {value}")
    private BigDecimal solde;


    public ClientDto(UUID id, String nomComplet, String telephone, ClientStatus clientStatus, CompteStatus compteStatus) {
        this.id = id;
        this.nomComplet = nomComplet;
        this.telephone = telephone;
        this.clientStatus = clientStatus;
        this.compteStatus = compteStatus;
    }
}
