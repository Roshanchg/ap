package com.example.ap.classes;

import com.example.ap.classes.enums.BOOKINGSTATUS;

public class Booking {
    private String bookingDate;
    private BOOKINGSTATUS status;
    public Booking(String bookingDate,BOOKINGSTATUS status){
        this.bookingDate=bookingDate;
        this.status=status;
    }

    public void applyDiscount(){

    }
    public void showAlerts(){

    }
    public void logEmergency(){

    }


}
