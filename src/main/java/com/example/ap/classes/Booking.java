package com.example.ap.classes;


import com.example.ap.handlers.FileHandling;
import com.example.ap.handlers.ObjectHandler;

import java.io.IOException;
import java.time.LocalDate;

public class Booking {
    private int bookingId;
    private int userId;
    private int guideId;
    private int aid;
    private double discount;
    private String bookingDate;
    private boolean isCancelled;
    private int fid;
    public Booking(int bookingId,int userId,int gid,int aid,String bookingDate,double discount,boolean isCancelled,int fid){
        this.guideId=gid;
        this.aid=aid;
        this.bookingId=bookingId;
        this.userId=userId;
        this.bookingDate=bookingDate;
        this.isCancelled=isCancelled;
        this.fid=fid;
    }
    public String getDetails(){
        return this.bookingId+","+this.userId+","+this.guideId+","+
                this.aid+","+this.bookingDate+","+this.discount+","+this.isCancelled;
    }


    public void applyDiscount() throws IOException {
        LocalDate currentDate=LocalDate.now();
        Festival festival= ObjectHandler.getFestivalForDate(currentDate);
        assert festival != null;
        this.discount=festival.getDiscountRate();
    }
    public void showAlerts(){

    }
    public void logEmergency(){

    }


}
