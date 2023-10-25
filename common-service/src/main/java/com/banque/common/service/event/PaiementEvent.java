package com.banque.common.service.event;

import com.banque.common.service.dto.PaiementDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaiementEvent implements Event{

    private PaiementDto paiementDto;

    private CompteStatus status;

    private UUID eventId = UUID.randomUUID();

    private Date eventDate = new Date();

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getEventDate() {
        return eventDate;
    }

    public PaiementEvent(PaiementDto paiementDto, CompteStatus status) {
        this.paiementDto = paiementDto;
        this.status = status;
    }
}
