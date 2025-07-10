package com.example.ap.classes;

import com.example.ap.classes.enums.BOOKINGSTATUS;

public class Booking {
    private int bookingId;
    private int userId;
    private String bookingDate;
    private BOOKINGSTATUS status;
    public Booking(int bookingId,int userId,String bookingDate,BOOKINGSTATUS status){
        this.bookingId=bookingId;
        this.userId=userId;
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
