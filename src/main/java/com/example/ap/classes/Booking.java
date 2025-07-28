package com.example.ap.classes;


import com.example.ap.handlers.ObjectFinder;

import java.io.IOException;
import java.time.LocalDate;

public class Booking {
    private int bookingId;
    private int userId;
    private int guideId;
    private int aid;
    private double discount;
    private LocalDate bookingDate;
    private boolean isCancelled;
    private int fid;
    private double price;
    private double total;


    public Booking(int bookingId,int userId,int gid,int aid,LocalDate bookingDate,double discount,boolean isCancelled,int fid,double price){
        this.guideId=gid;
        this.aid=aid;
        this.bookingId=bookingId;
        this.userId=userId;
        this.bookingDate=bookingDate;
        this.isCancelled=isCancelled;
        this.fid=fid;
        this.discount=discount;
        this.price=price;
        applyDiscount(this.discount);
    }

    public void applyDiscount(double discount){
            this.discount=discount;
            total=price-((price*this.discount)/100);
    }

    public String getDetails(){
        return this.bookingId+","+this.userId+","+this.guideId+","+
                this.aid+","+this.bookingDate+","+this.discount+","+this.isCancelled+","+this.fid+
                ","+this.price+","+this.total;
    }

    public double getPrice(){return this.price;}
    public double getTotal(){return this.total;}
    public int getBookingId(){return this.bookingId;}
    public int getAid(){return this.aid;}
    public double getDiscount(){return this.discount;}
    public boolean getIsCancelled(){return this.isCancelled;}
    public int getUserId(){return this.userId;}
    public LocalDate getBookingDate(){return this.bookingDate;}
    public int getGuideId(){return this.guideId;}
    public int getFid(){return this.fid;}

    public void cancel(){
        this.isCancelled=true;
    }
    public void setGid(int id){this.guideId=id;}

}
