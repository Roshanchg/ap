package com.example.ap.classes;

import com.example.ap.classes.enums.ALERTRISK;

public class Alerts {
    private int id;
    private ALERTRISK riskType;
    private String message;
    private int monthsActive;
    public Alerts(int id,ALERTRISK riskType,String message,int monthsActive){
        this.riskType=riskType;
        this.id=id;
        this.message=message;
        this.monthsActive=monthsActive;
    }
    public String getDetails(){
        return this.id+","+this.riskType+","+this.message+","+this.monthsActive;
    }
}
