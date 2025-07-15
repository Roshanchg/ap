package com.example.ap.handlers;

import com.example.ap.classes.Booking;
import com.example.ap.classes.enums.USERTYPE;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CacheHandler {

    public static void ClearCache(){
        Path cacheDir = Paths.get("cache");

        try (Stream<Path> paths = Files.walk(cacheDir)) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            System.err.println("Failed to delete: " + path);
                        }
                    });
            System.out.println("Cache files cleared.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeCacheExcept(USERTYPE usertype){
        Path cacheDir = Paths.get("cache");
        String active=switch (usertype){
            case Guide -> "Guide";
            case Tourist -> "Tourist";
            case Admin -> "Admin";
        };
        Path activeDir = cacheDir.resolve(active);

        try (Stream<Path> paths = Files.walk(cacheDir)) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(path -> !path.startsWith(activeDir))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            System.err.println("Failed to delete: " + path);
                        }
                    });
            System.out.println("Cache files cleared.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void WriteCache(USERTYPE usertype,String line) throws IOException{

        switch (usertype){
            case Tourist -> {
                String cachename="cache/Tourist/booking.csv";
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(cachename,true))){
                    bw.write(line);
                    bw.newLine();
                }            }
            case Guide -> {
                String cachename="cache/Guide/assign.csv";
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(cachename,true))){
                    bw.write(line);
                    bw.newLine();
                }
            }
            case Admin -> {

            }
        }
    }

    public static List<String> getBookingsCache() throws IOException{
        String bookingCache="cache/Tourist/booking.csv";
        List<String> bookings=new ArrayList<>();
        String line;
        SessionHandler.getInstance().startSession(1,"Roshan Chaulagain",USERTYPE.Tourist);
        if(!fileExists(bookingCache)){
            Booking booking;
            for(int i=1;i<=FileHandling.getSize(FileHandling.BookingsFile);i++) {
                booking=ObjectFinder.getBooking(i);
                if(booking.getUserId()==SessionHandler.getInstance().getUserId()){
                    WriteCache(USERTYPE.Tourist,booking.getDetails());
                }
            }
        }
        try(BufferedReader br= new BufferedReader(new FileReader(bookingCache))){
            while((line=br.readLine())!=null){
                if (line.trim().isEmpty()) continue;
                bookings.add(line);
            }
            return bookings;
        }
    }


    public static List<String> getAssignmentsCache() throws IOException{
        String bookingCache="cache/Guide/assign.csv";
        List<String> bookings=new ArrayList<>();
        String line;
        try(BufferedReader br= new BufferedReader(new FileReader(bookingCache))){
            while((line=br.readLine())!=null){
                if (line.trim().isEmpty()) continue;
                bookings.add(line);
            }
            return bookings;
        }
    }

    public static boolean fileExists(String fileName){
        return(new File(fileName).exists());
    }
}

