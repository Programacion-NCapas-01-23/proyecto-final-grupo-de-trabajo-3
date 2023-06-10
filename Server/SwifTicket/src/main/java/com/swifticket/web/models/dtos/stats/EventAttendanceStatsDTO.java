package com.swifticket.web.models.dtos.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// DTO just to get the Event Attendance Stat data and not to set it
public class EventAttendanceStatsDTO {
    private int capacity;
    private int ticketsSold;
    private double soldVsAvailable;
    private int attendants;
    private double attendantsVsTicketsSold;
}
