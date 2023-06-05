package com.swifticket.web.models.dtos.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventStatsDTO {
    private int capacity;
    private int ticketsSold;
    private double soldVsAvailable;
    private int attendants;
    private double attendantsVsTicketsSold;
    private double attendanceSingle;
    private double attendanceGroup;
}
