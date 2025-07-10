package com.example.ap.handlers;

import com.example.ap.classes.Admin;
import com.example.ap.classes.Guide;
import com.example.ap.classes.Tourist;
import com.example.ap.classes.User;
import com.example.ap.classes.enums.LANGUAGES;
import com.example.ap.classes.enums.USERTYPE;

import java.io.*;
import java.nio.Buffer;

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
                try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.GuideFile))) {
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
}
