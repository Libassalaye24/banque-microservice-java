package com.banque.common.service.event;

import com.banque.common.service.dto.CompteDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteEvent implements  Event{

    private CompteStatus compteStatus;
    private CompteDto compteDto;

    private UUID eventId = UUID.randomUUID();

    private Date eventDate = new Date();

    public CompteEvent(CompteDto compteDto, CompteStatus updated) {
        this.compteDto = compteDto;
        this.compteStatus = updated;
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
