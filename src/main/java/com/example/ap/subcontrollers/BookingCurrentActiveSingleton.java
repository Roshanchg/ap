package com.example.ap.subcontrollers;

public class BookingCurrentActiveSingleton {
    private static int id=0;
    public static void setId(int id){
        BookingCurrentActiveSingleton.id=id;
    }
    public static int getId(){
        return id;
    }
    public static void reset(){
        id=0;
    }
}
