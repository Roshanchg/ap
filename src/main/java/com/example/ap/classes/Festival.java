package com.example.ap.classes;

import java.time.LocalDate;

public class Festival {
    private int id;
    private String name;
    LocalDate startDate,endDate;
    private double discountRate;
    public Festival(int id,String name,LocalDate startDate,LocalDate endDate,double discountRate){
        this.name=name;
        this.startDate=startDate;
        this.endDate=endDate;
        this.discountRate=discountRate;
        this.id=id;
    }
    public String getDetails(){
        return this.id+","+this.name+","+this.startDate+","+this.endDate+","+this.discountRate;
    }

    public double getDiscountRate(){
        return this.discountRate;
    }

}
