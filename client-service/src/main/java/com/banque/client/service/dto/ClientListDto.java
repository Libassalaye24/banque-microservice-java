package com.banque.client.service.dto;

import com.banque.common.service.event.ClientStatus;
import com.banque.common.service.event.CompteStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientListDto {

    private UUID id;

    private String nomComplet;

    private String telephone;

    private ClientStatus clientStatus;

    private CompteStatus compteStatus;
}
