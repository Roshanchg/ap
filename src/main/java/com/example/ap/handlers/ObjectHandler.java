package com.example.ap.handlers;

import com.example.ap.classes.*;
import com.example.ap.classes.enums.*;
import javafx.scene.control.Alert;

import java.io.*;
import java.nio.Buffer;
import java.time.LocalDate;

import static com.example.ap.classes.enums.USERTYPE.Guide;
import static com.example.ap.classes.enums.USERTYPE.Tourist;

public class ObjectHandler {



    public static User getUser(int uid, USERTYPE usertype) throws IOException {
        String line;
        String[] parts;
        int id;
        String name;
        String email;
        String phoneNumber;
        String password;

        switch(usertype){
            case Admin -> {
                Admin admin;
                try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.AdminFile))) {
                    while((line=br.readLine())!=null){
                        if(line.trim().isEmpty()) continue;
                        parts=line.split(",");
                        id=Integer.parseInt(parts[0]);
                        if(id==uid){
                            name=parts[1];
                            email=parts[2];
                            phoneNumber=parts[3];
                            password=parts[4];
                            admin=new Admin(id,name,email,phoneNumber,password);
                            return admin;
                        }
                    }
                    System.out.println("User not found with uid: "+uid);
                }
            }
            case Guide -> {
            Guide guide;
            LANGUAGES language;
            int YearsOfExperience;
            boolean availability;
                try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.GuideFile))){
                    while((line=br.readLine())!=null){
                        if(line.trim().isEmpty()) continue;
                        parts=line.split(",");
                        id=Integer.parseInt(parts[0]);
                        if(id==uid){
                            name=parts[1];
                            email=parts[2];
                            phoneNumber=parts[3];
                            password=parts[4];
                            language=switch(parts[5]){
                                case "Nepali"-> LANGUAGES.Nepali;
                                case "English"-> LANGUAGES.English;
                                default -> throw new IllegalStateException("Unexpected value: " + parts[5]);
                            };
                            YearsOfExperience=Integer.parseInt(parts[6]);
                            availability=Boolean.parseBoolean(parts[7]);
                            guide=new Guide(id,name,email,phoneNumber,password,language,YearsOfExperience);
                            guide.updateAvailability(availability);
                            return guide;
                        }
                    }
                    System.out.println("guide not found with uid: "+uid);

            }

            }
            case Tourist -> {
                Tourist tourist;
                LANGUAGES language;
                String nationality;
                String emergencyNumber;
                try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.TouristFile))){
                    while((line=br.readLine())!=null){
                        if(line.trim().isEmpty()) continue;
                        parts=line.split(",");
                        id=Integer.parseInt(parts[0]);
                        if(id==uid){
                            name=parts[1];
                            email=parts[2];
                            phoneNumber=parts[3];
                            password=parts[4];
                            language=switch(parts[5]){
                                case "Nepali"-> LANGUAGES.Nepali;
                                case "English"-> LANGUAGES.English;
                                default -> throw new IllegalStateException("Unexpected value: " + parts[5]);
                            };
                            nationality=parts[6];
                            emergencyNumber=parts[7];
                            tourist=new Tourist(id,name,email,phoneNumber,password,language,nationality,emergencyNumber);
                            return tourist;
                        }
                    }
                    System.out.println("tourist not found with uid: "+uid);
                }
            }

            }
            return null;
    }


    public static Festival getFestive(int id) throws IOException{
        try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.FestivalsFile))){
            String line;
            String[] parts;
            int fid;
            String name;
            LocalDate start;
            LocalDate end;
            double discount;
            Festival festival;
            while((line=br.readLine())!=null){
                if (line.trim().isEmpty()) continue;
                parts=line.split(",");
                fid=Integer.parseInt(parts[0]);
                name=parts[1];
                start= LocalDate.parse(parts[2]);
                end=LocalDate.parse(parts[3]);
                discount=Double.parseDouble(parts[4]);
                if(fid==id){
                    festival=new Festival(fid,name,start,end,discount);
                    return festival;
                }
            }
        }
        return null;
    }


    public static Attraction getAttraction(int aid)throws IOException{
        try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.AttractionsFile))){
            String line;
            String[] parts;
            int id;
            String name;
            String location;
            ATTRACTIONTYPE type;
            ATTRACTIONDIFFICULTY difficulty;
            String altitude;
            boolean restrictedMonsoon;
            Attraction attraction;
            while((line=br.readLine())!=null){
                if (line.trim().isEmpty()) continue;
                parts=line.split(",");
                id=Integer.parseInt(parts[0]);
                name=parts[1];
                location=parts[2];
                type=switch(parts[3]){
                    case "Camping"-> ATTRACTIONTYPE.Camping;
                    case "Trekking" ->  ATTRACTIONTYPE.Trekking;
                    case "Rafting"-> ATTRACTIONTYPE.Rafting;
                    default -> throw new IllegalStateException("Unexpected value: " + parts[3]);
                };
                difficulty=switch(parts[4]){
                    case "HIGH" ->ATTRACTIONDIFFICULTY.HIGH;
                    case "LOW" -> ATTRACTIONDIFFICULTY.LOW;
                    default -> throw new IllegalStateException("Unexpected value: " + parts[4]);
                };
                altitude=parts[5];
                restrictedMonsoon=Boolean.parseBoolean(parts[6]);
                if(id==aid){
                    attraction=new Attraction(id,name,location,type,difficulty,altitude,restrictedMonsoon);
                    return attraction;
                }
            }
        }
        return null;
    }


    public static Booking getBooking(int bid)throws IOException{
        try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.BookingsFile))){
            String line;
            String[] parts;
            int id;
            int uid;
            int gid;
            int aid;
            String date;
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
                date=parts[4];
                discount=Double.parseDouble(parts[5]);
                isCancelled=Boolean.parseBoolean(parts[6]);
                fid=Integer.parseInt(parts[7]);
                if(id==bid){
                    booking=new Booking(id,uid,gid,aid,date,discount,isCancelled,fid);
                    return booking;
                }
            }
        }
        return null;
    }

    public static Alerts getAlert(int aid) throws IOException{
        try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.AlertsFile))){
            int id;
            ALERTRISK risk;
            String message;
            int monthsActive;
            Alerts alert;
            String line;
            String[] parts;
            while((line=br.readLine())!=null){
                if (line.trim().isEmpty()) continue;
                parts=line.split(",");
                id=Integer.parseInt(parts[0]);
                if(id==aid){
                    risk=switch(parts[1]){
                        case "HEAVY_RAINFALL" -> ALERTRISK.HEAVY_RAINFALL;
                        default->
                            throw new IllegalStateException("Unexpected value: " + parts[1]);
                    };
                    message=parts[2];
                    monthsActive=Integer.parseInt(parts[3]);
                    alert=new Alerts(id,risk,message,monthsActive);
                    return alert;
                }
            }
        }
        return null;
    }




    public static Festival getFestivalForDate(LocalDate currentDate) throws IOException{
        try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.FestivalsFile))){
            int id;
            String line;
            String[] parts;
            LocalDate startDate;
            LocalDate endDate;
            while((line=br.readLine())!=null){
                if (line.trim().isEmpty()) continue;
                parts=line.split(",");
                startDate=LocalDate.parse(parts[2]);
                endDate=LocalDate.parse(parts[3]);
                if((currentDate.equals(endDate)||currentDate.isBefore(endDate))
                        &&(currentDate.equals(startDate)|| currentDate.isAfter(startDate) )){
                    id=Integer.parseInt(parts[0]);
                    return ObjectHandler.getFestive(id);
                }
            }

        }
        return null;
    }
}
