package com.example.ap.classes;

import com.example.ap.classes.enums.ALERTRISK;

public class Alerts {
    private ALERTRISK riskType;
    private String message;
    private int monthsActive;
    public Alerts(ALERTRISK riskType,String message,int monthsActive){
        this.riskType=riskType;
        this.message=message;
        this.monthsActive=monthsActive;
    }
}
