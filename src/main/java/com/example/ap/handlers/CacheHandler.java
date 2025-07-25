package com.example.ap.handlers;

import com.example.ap.classes.Booking;
import com.example.ap.classes.enums.USERTYPE;

import java.awt.print.Book;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CacheHandler {
    public static final Path cacheDir = Paths.get("cache");

    public static final String bookingCacheFile="cache/Tourist/booking.csv";

    public static final String assignmentsCacheFile="cache/Guide/assign.csv";


    public static void initCache() {
        try {
            Files.createDirectories(Paths.get("Exports"));
            Files.createDirectories(cacheDir);
            Files.createDirectories(Paths.get(bookingCacheFile).getParent());
            Files.createDirectories(Paths.get(assignmentsCacheFile).getParent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ClearCache(){

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
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(bookingCacheFile,true))){
                    bw.write(line);
                    bw.newLine();
                }            }
            case Guide -> {
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(assignmentsCacheFile,true))){
                    bw.write(line);
                    bw.newLine();
                }
            }
            case Admin -> {

            }
        }
    }

    public static void initBookingsCache()throws IOException{
        if(!fileExists(bookingCacheFile)){
            List<Booking> allBookings=FileHandling.AllBookings();
            for(Booking booking: allBookings) {
                assert booking != null;
                if(booking.getUserId()==SessionHandler.getInstance().getUserId()){
                    WriteCache(USERTYPE.Tourist,booking.getDetails());
                }
            }
        }
    }

    public static List<Booking> getBookingsCache() throws IOException{
        initBookingsCache();
        List<Booking> bookings=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader(bookingCacheFile))){
            String line;
            String[] parts;
            int id;
            int uid;
            int gid;
            int aid;
            LocalDate date;
            double discount;
            boolean isCancelled;
            int fid;
            Booking booking;
            while((line=br.readLine())!=null){
                if (line.trim().isEmpty()) continue;
                parts=line.split(",");
                id=Integer.parseInt(parts[0]);
                uid=Integer.parseInt(parts[1]);
                gid=Integer.parseInt(parts[2]);
                aid=Integer.parseInt(parts[3]);
                date=LocalDate.parse(parts[4]);
                discount=Double.parseDouble(parts[5]);
                isCancelled=Boolean.parseBoolean(parts[6]);
                if(isCancelled)continue;
                fid=Integer.parseInt(parts[7]);
                booking=new Booking(id,uid,gid,aid,date,discount,isCancelled,fid);
                bookings.add(booking);
            }
        }
        catch (FileNotFoundException e){
            return null;
        }

        return bookings.reversed();
    }


    public static void initAssignmentsCache()throws IOException{
        if(!fileExists(assignmentsCacheFile)){
            List<Booking> allBookings=FileHandling.AllBookings();
            for(Booking booking: allBookings) {
                assert booking != null;
                if(booking.getGuideId()==SessionHandler.getInstance().getUserId()){
                    WriteCache(USERTYPE.Guide,booking.getDetails());
                }
            }
        }
    }


    public static List<Booking> getAssignmentsCache() throws IOException{
        initAssignmentsCache();
        List<Booking> bookings=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader(assignmentsCacheFile))){
            String line;
            String[] parts;
            int id;
            int uid;
            int gid;
            int aid;
            LocalDate date;
            double discount;
            boolean isCancelled;
            int fid;
            Booking booking;
            while((line=br.readLine())!=null){
                if (line.trim().isEmpty()) continue;
                parts=line.split(",");
                id=Integer.parseInt(parts[0]);
                uid=Integer.parseInt(parts[1]);
                gid=Integer.parseInt(parts[2]);
                aid=Integer.parseInt(parts[3]);
                date=LocalDate.parse(parts[4]);
                discount=Double.parseDouble(parts[5]);
                isCancelled=Boolean.parseBoolean(parts[6]);
                fid=Integer.parseInt(parts[7]);
                booking=new Booking(id,uid,gid,aid,date,discount,isCancelled,fid);
                bookings.add(booking);
                }
            }
        catch (FileNotFoundException e){
            return null;
        }
        return bookings;
    }


    public static void reloadCache(USERTYPE usertype) throws IOException {
        ClearCache();
        switch (usertype){
            case Guide -> initAssignmentsCache();
            case Tourist -> initBookingsCache();
        }
    }
    public static boolean fileExists(String fileName){
        return(new File(fileName).exists());
    }
}

