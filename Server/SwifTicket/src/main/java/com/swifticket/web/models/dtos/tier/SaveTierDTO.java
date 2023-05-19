package com.swifticket.web.models.dtos.tier;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveTierDTO {
    private String eventId;
    private String name;
    private int capacity;
    private int price;
}
