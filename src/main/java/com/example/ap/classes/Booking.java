package com.example.ap.classes;


public class Booking {
    private int bookingId;
    private int userId;
    private int guideId;
    private int aid;
    private double discount;
    private String bookingDate;
    private boolean isCancelled;
    public Booking(int bookingId,int userId,int gid,int aid,String bookingDate,double discount,boolean isCancelled){
        this.guideId=gid;
        this.aid=aid;
        this.bookingId=bookingId;
        this.userId=userId;
        this.bookingDate=bookingDate;
        this.isCancelled=isCancelled;
    }

    public void applyDiscount(){

    }
    public void showAlerts(){

    }
    public void logEmergency(){

    }


}
