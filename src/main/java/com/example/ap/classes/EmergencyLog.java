package com.example.ap.classes;

public class EmergencyLog {
    private String time,message;
    public EmergencyLog(String time,String message){
        this.time=time;
        this.message=message;
    }
    public String getDetails(){
        return this.time+","+this.message;
    }
    public String getTime(){return this.time;}
    public String getMessage(){return this.message;}
}
