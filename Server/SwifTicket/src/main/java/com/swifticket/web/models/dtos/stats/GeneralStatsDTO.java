package com.swifticket.web.models.dtos.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
// DTO just to get the General Stats data and not to set it
public class GeneralStatsDTO {
    private int users;
    private int events;
    private int ticketsSold;
    private int attendants;
    private List<Double> attendanceSingleVsGroup;
}
