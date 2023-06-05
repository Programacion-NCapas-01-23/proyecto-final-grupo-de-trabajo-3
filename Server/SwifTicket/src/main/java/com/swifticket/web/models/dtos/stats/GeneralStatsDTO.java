package com.swifticket.web.models.dtos.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeneralStatsDTO {
    private int users;
    private int events;
    private int ticketsSold;
    private int attendants;
    private double attendanceSingle;
    private double attendanceGroup;
}
