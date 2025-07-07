package com.example.ap.handlers;

import com.example.ap.classes.enums.USERTYPE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.example.ap.handlers.FileHandling.*;

public class UserHandling {
    public static int getUserId(String username, String password, USERTYPE usertype){
        String line;
        String[] parts;
        String typeFile=
                switch(usertype){
                    case Tourist ->TouristFile;
                    case Admin -> AdminFile;
                    case Guide ->GuideFile;
                };

        if(isEmail(username)){
            try(BufferedReader br=new BufferedReader(new FileReader(typeFile))){
                // index 2, 3, 4 email, phone, password
                while((line=br.readLine())!=null){
                    if(line.trim().isEmpty()) return -1;
                    parts=line.split(",");
                    if(parts[2].equals(username)){
                        if( parts[4].equals(password)){
                            return Integer.parseInt(parts[0]);
                        }
                    }
                }
            } catch (IOException e) {
                return -1;
            }
        }
        else {
            try (BufferedReader br = new BufferedReader(new FileReader(typeFile))) {
                // index 2, 3, 4 email, phone, password
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) return -1;
                    parts = line.split(",");
                    if (parts[3].equals(username)) {
                        if(parts[4].equals(password)){
                            return Integer.parseInt(parts[0]);
                        }
                    }
                }
            } catch (IOException e) {
                return -1;
            }
        }
        return -1;
    }
    public static String getName(int id,USERTYPE usertype){
        String line;
        String[] parts;
        String typeFile=
                switch(usertype){
                    case Tourist ->TouristFile;
                    case Admin -> AdminFile;
                    case Guide ->GuideFile;
                };
        try(BufferedReader br=new BufferedReader(new FileReader(typeFile))){
            while((line=br.readLine())!=null){
                if(line.trim().isEmpty()) return "";
                parts=line.split(",");
                if(Integer.parseInt(parts[0])==id){
                    return parts[1];
                }
            }
        }
        catch (IOException e){
            return "";
        }
        return "";
    }
}
