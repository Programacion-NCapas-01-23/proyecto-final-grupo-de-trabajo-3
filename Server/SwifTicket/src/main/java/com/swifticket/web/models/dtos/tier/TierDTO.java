package com.swifticket.web.models.dtos.tier;

import com.swifticket.web.models.entities.Tier;
import lombok.Data;
import java.util.UUID;

@Data
public class TierDTO {
    private UUID id;
    private String name;
    private int capacity;
    private float price;
    private int ticketsSold;
    private boolean isAvailable;

    public TierDTO(Tier tier) {
        this.id = tier.getId();
        this.name = tier.getName();
        this.capacity = tier.getCapacity();
        this.price = tier.getPrice();
        this.ticketsSold = tier.getTickets().size();
        this.isAvailable = tier.getCapacity() > tier.getTickets().size();
    }
}
