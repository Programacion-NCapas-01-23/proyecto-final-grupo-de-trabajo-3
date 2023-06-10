package com.swifticket.web.models.dtos.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
// DTO just to get the Events Stats data and not to set it
public class EventStatsDTO {
    private int capacity;
    private int ticketsSold;
    private double soldVsAvailable;
    private int attendants;
    private double attendantsVsTicketsSold;
    private List<Double> attendanceSingleVsGroup;

    private List<String> tiers;
    private List<Integer> ticketsSoldByTier;

    private List<String> attendanceHours;
    private List<Integer> attendanceValues;
}
