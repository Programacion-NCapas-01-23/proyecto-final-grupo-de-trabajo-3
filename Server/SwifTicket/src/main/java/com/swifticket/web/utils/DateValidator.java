package com.swifticket.web.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateValidator {
    public Boolean isValidEventDate(String date) {
        try {
            Date currentDate = new Date();
            Date eventDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            // Can't add events in the past
            return eventDate.compareTo(currentDate) < 0;
        } catch (Exception e) {
            return false;
        }
    }
}
