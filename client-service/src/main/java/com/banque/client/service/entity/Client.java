package com.banque.client.service.entity;

import com.banque.common.service.event.ClientStatus;
import com.banque.common.service.event.CompteStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Client {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

//    @NotBlank(message = "Nom complet est obligatoire ")
    private String nomComplet;

//    @Pattern(regexp = "\\+\\d{1,3}-\\d{9,15}", message = "Invalid phone number format. Use format: +XX-XXXXXXXXXXX")
    private String telephone;

    private ClientStatus clientStatus;

    private CompteStatus compteStatus;
}

