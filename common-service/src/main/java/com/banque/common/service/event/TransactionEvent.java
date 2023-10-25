package com.banque.common.service.event;

import com.banque.common.service.dto.PaiementDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEvent implements  Event{

    private CompteStatus status;
    private PaiementDto paiementDto;

    private UUID eventId = UUID.randomUUID();

    private Date eventDate = new Date();

    public TransactionEvent(PaiementDto paiementDto , CompteStatus updated) {
        this.status = updated;
        this.paiementDto = paiementDto;
    }


    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getEventDate() {
        return eventDate;
    }


}
