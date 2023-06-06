package com.swifticket.web.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateFormatter {

    public String hourFormatter(Date date) {
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
        return hourFormat.format(date);
    }

    public String getHoursKey(String hour) {
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();

        try {
            Date _hour = hourFormat.parse(hour);
            calendar.setTime(_hour);

            // Round down to the nearest hour
            calendar.set(Calendar.MINUTE, 0);
            String roundedHour = hourFormat.format(calendar.getTime());

            // Add one hour
            calendar.add(Calendar.HOUR, 1);
            String nextHour = hourFormat.format(calendar.getTime());

            // Format "HH:mm - HH:mm"
            return roundedHour + " - " + nextHour;
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
    }
}
