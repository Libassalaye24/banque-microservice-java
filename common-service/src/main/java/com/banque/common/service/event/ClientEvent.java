package com.banque.common.service.event;


import com.banque.common.service.dto.ClientDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientEvent implements Event {

    private ClientDto clientDto;

    private ClientStatus clientStatus;

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

    public ClientEvent(ClientDto clientDto , ClientStatus clientStatus) {
        this.clientDto = clientDto;
        this.clientStatus = clientStatus;
    }


}
