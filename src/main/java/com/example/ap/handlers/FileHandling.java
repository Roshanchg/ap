package com.example.ap.handlers;

import com.example.ap.classes.Attraction;
import com.example.ap.classes.Booking;
import com.example.ap.classes.Festival;
import com.example.ap.classes.User;
import com.example.ap.classes.enums.USERTYPE;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHandling {
    public static String email_regex="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static Pattern email_pattern=Pattern.compile(email_regex);

    public static boolean isEmail(String string){
        if(string.isEmpty()){
            return false;
        }
        Matcher matcher =email_pattern.matcher(string);
        return matcher.matches();
    }


    public static String TouristFile="tourists.csv";
    public static String GuideFile="Guides.csv";
    public static String AdminFile="Admins.csv";
    public static String ReportFile="Report.csv";
    public static String AttractionsFile="Attractions.csv";
    public static String FestivalsFile="Festivals.csv";
    public static String BookingsFile="Bookings.csv";
    public static String LogFile="Log.txt";
    public static String AlertsFile="Alerts.csv";


    public static void WriteUser(USERTYPE usertype,User user){
        switch(usertype){
            case Admin -> {
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(AdminFile,true))){
                    String line=user.getDetails();
                    bw.write(line);
                    bw.newLine();
                }
                catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }
            case Guide -> {
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(GuideFile,true))){
                    String line=user.getDetails();
                    bw.write(line);
                    bw.newLine();
                }
                catch (IOException e){
                    System.out.println(e.getMessage());

                }

            }
            case Tourist -> {
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(TouristFile,true))){
                    String line=user.getDetails();
                    bw.write(line);
                    bw.newLine();
                }
                catch (IOException e){
                    System.out.println(e.getMessage());

                }
            }
            default -> {

            }
        }

    }
    public void removeUser(USERTYPE usertype,int uid){
        String tempFile="tempUser.csv";
        switch(usertype){
            case Admin -> {
//                try(BufferedWriter bw=new BufferedWriter(new FileWriter(AdminFile,true))){
//                    String line=user.getDetails();
//                    bw.write(line);
//                    bw.newLine();
//                }
//                catch (IOException e){
//                    System.out.println(e.getMessage());
//                }
            }
            case Guide -> {
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(GuideFile,true))){

                }
                catch (IOException e){
                    System.out.println(e.getMessage());

                }

            }
            case Tourist -> {
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(TouristFile,true))){

                }
                catch (IOException e){
                    System.out.println(e.getMessage());

                }
            }
        }
    }
    public void editUser(USERTYPE usertype,int uid, User newUser){
        switch(usertype) {
            case Admin -> {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(AdminFile, true))) {

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            case Guide -> {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(GuideFile, true))) {

                } catch (IOException e) {
                    System.out.println(e.getMessage());

                }

            }
            case Tourist -> {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(TouristFile, true))) {

                } catch (IOException e) {
                    System.out.println(e.getMessage());

                }
            }
        }
    }


    public static int getSize(String filename){
        int i=1;
        try(BufferedReader br=new BufferedReader(new FileReader(filename))) {
            while(br.readLine()!=null){
                i++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return i;
    }
    public static int getNextId(String filename){
        int i=1;
        String line;
        try(BufferedReader br=new BufferedReader(new FileReader(filename))) {
            while((line=br.readLine())!=null){
                if( line.trim().isEmpty() ) continue;
                i=Integer.parseInt(line.split(",")[0])+1;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return i;
    }
    public static boolean authenticate(USERTYPE type,String username,String password){
        String line;
        String[] parts;
        String typeFile=
                switch(type){
                    case Tourist ->TouristFile;
                    case Admin -> AdminFile;
                    case Guide ->GuideFile;
                    };

        if(isEmail(username)){
            try(BufferedReader br=new BufferedReader(new FileReader(typeFile))){
                // index 2, 3, 4 email, phone, password
                while((line=br.readLine())!=null){
                    if(line.trim().isEmpty()) return false;
                    parts=line.split(",");
                    if(parts[2].equals(username)){
                        return parts[4].equals(password);
                    }
                }
            } catch (IOException e) {
                return false;
            }
        }
        else {
            try (BufferedReader br = new BufferedReader(new FileReader(typeFile))) {
                // index 2, 3, 4 email, phone, password
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) return false;
                    parts = line.split(",");
                    if (parts[3].equals(username)) {
                        return parts[4].equals(password);
                    }
                }
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    public static void MakeBooking(Booking booking){

    }
    public static void EditBooking(int bid,Booking newBooking){
    }
    public static void removeBooking(int bid){

    }



    public static void AddAttraction(Attraction attraction){

    }
    public static void editAttraction(int aid,Attraction newAttraction){
    }
    public static void removeAttraction(int aid){

    }

    public static void addFestival(int fid, Festival festival){

    }
    public static void removeFestival(int fid){

    }
}
