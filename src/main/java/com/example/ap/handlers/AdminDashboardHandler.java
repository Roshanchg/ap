package com.example.ap.handlers;

import com.example.ap.classes.Alerts;
import com.example.ap.classes.Attraction;
import com.example.ap.classes.Booking;
import com.example.ap.classes.EmergencyLog;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminDashboardHandler {
    public static Map<LocalDate, Integer> getBookingDateChartMap() throws IOException {
        List<Booking> allBookings = FileHandling.AllBookings();
        Map<LocalDate, Integer> bookingDateMap = new HashMap<>();
        if (allBookings == null) return bookingDateMap;

        LocalDate currentDate = LocalDate.now();
        LocalDate thresholdDate = currentDate.minusWeeks(2);

        for (Booking booking : allBookings) {
            LocalDate bookingDate = booking.getBookingDate();
            if (bookingDate.isAfter(thresholdDate) && bookingDate.isBefore(currentDate) && !booking.getIsCancelled()) {
                bookingDateMap.put(bookingDate, bookingDateMap.getOrDefault(bookingDate, 0) + 1);
            }
        }
        return bookingDateMap;
    }

    public static Map<Attraction,Integer> getAttractionBookingMap() throws IOException{
        List<Booking> allBookings=FileHandling.AllBookings();
        Map<Attraction,Integer> attractionCountMap=new HashMap<>();
        Attraction attraction;
        for(Booking booking:allBookings){
            if(!booking.getIsCancelled()&&booking.getAid()!=0){
                attraction=ObjectFinder.getAttraction(booking.getAid());
                if(attraction==null) continue;
                attractionCountMap.put(attraction,attractionCountMap.getOrDefault(attraction,0)+1);
            }
        }
        return attractionCountMap;
    }
    public static List<EmergencyLog> getEmergencyLogs()throws IOException{
        List<EmergencyLog> emergencyOnly=new ArrayList<>();
        emergencyOnly=null;
        List<EmergencyLog> allLogs=FileHandling.AllLogs();
        for(EmergencyLog log:allLogs){
            if((log.getMessage().split(" ",2)[0]).equals("Emergency: ")){
                emergencyOnly.add(log);
            }
        }
        return emergencyOnly;

    }
    public static List<Alerts> getAlerts()throws IOException {
        return FileHandling.AllAlerts();
    }
}
