package com.example.ap.handlers;

import com.example.ap.classes.Admin;
import com.example.ap.classes.Booking;
import com.example.ap.classes.enums.USERTYPE;

import java.io.IOException;
import java.time.LocalDate;

public class initializer {
    public static void main(String[] args) throws IOException {
//        Admin admin=new Admin(1,"Roshan Chaulagain","chgroshan6@gmail.com","9898989898","roshan");
//        Admin admin2=new Admin(2,"Safal Lohani","safalBaddie@gmail.com","9898989899","safal");
//        FileHandling.WriteUser(USERTYPE.Admin,admin);
//        FileHandling.WriteUser(USERTYPE.Admin,admin2);

        Booking b=new Booking(1,1,0,0, LocalDate.now(),0,false,0);
        Booking b2=new Booking(2,1,0,0, LocalDate.now(),0,false,0);
        Booking b3=new Booking(3,1,0,0, LocalDate.now(),0,false,0);
        FileHandling.MakeBooking(b);
        FileHandling.MakeBooking(b2);
        FileHandling.MakeBooking(b3);

    }
}
