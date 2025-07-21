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
    public Booking(int bookingId,int userId,int gid,int aid,LocalDate bookingDate,double discount,boolean isCancelled,int fid){
        this.guideId=gid;
        this.aid=aid;
        this.bookingId=bookingId;
        this.userId=userId;
        this.bookingDate=bookingDate;
        this.isCancelled=isCancelled;
        this.fid=fid;
        this.discount=discount;
    }
    public String getDetails(){
        return this.bookingId+","+this.userId+","+this.guideId+","+
                this.aid+","+this.bookingDate+","+this.discount+","+this.isCancelled+","+this.fid;
    }


    public void applyDiscount() throws IOException {
        LocalDate currentDate=LocalDate.now();
        Festival festival= ObjectFinder.getFestivalForDate(currentDate);
        assert festival != null;
        this.discount=festival.getDiscountRate();
    }
    public int getBookingId(){return this.bookingId;}
    public int getAid(){return this.aid;}
    public double getDiscount(){return this.discount;}
    public boolean getIsCancelled(){return this.isCancelled;}
    public int getUserId(){return this.userId;}
    public LocalDate getBookingDate(){return this.bookingDate;}
    public int getGuideId(){return this.guideId;}
    public int getFid(){return this.fid;}


}
