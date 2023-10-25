package com.banque.client.service.entity;

import com.banque.common.service.event.CompteStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Paiement {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ManyToOne
    @JoinColumn()
    private Service service;

    @ManyToOne
    @JoinColumn()
    private Client client;

    private BigDecimal montant;

    private CompteStatus status;

    


}
