package com.example.ap.handlers;

import com.example.ap.classes.Booking;
import com.example.ap.classes.enums.USERTYPE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;


// user index 1, guide 2, attraction 3, festival 7
public class DeletionHandler {
    public static void onUserDelete(int uid, USERTYPE usertype) throws IOException {
        if(usertype==USERTYPE.Tourist) {
            deleteBookingFor(1, uid);
        }
        else if(usertype==USERTYPE.Guide){
            setNullBookingAtIndex(2,uid);
        }
    }

    public static void onAttractionDelete(int aid) throws IOException {
        deleteBookingFor(3,aid);
    }

    public static void onFestivalDeletion(int fid)throws IOException{
        setNullBookingAtIndex(7,fid);
    }

    public static void deleteBookingFor(int index, int filter)throws IOException {
        int bid = 0;
        try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.BookingsFile))){
            String line;
            String[] parts;

            while((line=br.readLine())!=null){
                parts=line.split(",");
                if (Integer.parseInt(parts[index])==filter){
                    bid=Integer.parseInt(parts[0]);
                }
            }
        }
        FileHandling.removeBooking(bid);
    }

    public static void setNullBookingAtIndex(int index, int filter)throws IOException {
        int bid=0;
        Booking booking = null;
        try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.BookingsFile))){
            String line;
            String[] parts;
            int uid;
            int gid;
            int aid;
            LocalDate date;
            double discount;
            boolean cancelled;
            int fid;
            while((line=br.readLine())!=null){
                parts=line.split(",");
                if (Integer.parseInt(parts[index])==filter){
                    parts[index]="0";
                    bid=Integer.parseInt(parts[0]);
                    uid=Integer.parseInt(parts[1]);
                    gid=Integer.parseInt(parts[2]);
                    aid=Integer.parseInt(parts[3]);
                    date=LocalDate.parse(parts[4]);
                    discount=Double.parseDouble(parts[5]);
                    cancelled=Boolean.parseBoolean(parts[6]);
                    fid=Integer.parseInt(parts[7]);
                    booking=new Booking(bid,uid,gid,aid,date,discount,cancelled,fid);
                }
            }
        }
        FileHandling.editBooking(bid,booking);
    }
}
