package com.example.ap.handlers;

import com.example.ap.classes.Admin;
import com.example.ap.classes.User;
import com.example.ap.classes.enums.USERTYPE;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdminInitializer {
    public static void init() throws IOException {
        File adminFile=new File(FileHandling.AdminFile);
        if(!adminFile.exists()){
            adminFile.createNewFile();
        }
        Admin roshan=new Admin(1,"Roshan Chaulagain","chgroshan6@gmail.com",
                "9823168078","roshan");
        Admin safal=new Admin(2,"Safal Lohani","safal@gmail.com",
                "9800000000","safal");
        boolean isEmpty;

        try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.AdminFile))){
            String line;
            while((line= br.readLine())!=null) {
                if (line.trim().isEmpty()) continue;
                else{
                    isEmpty=false;
                    break;
                }
            }
            isEmpty=true;
        }
        if(isEmpty){
                FileHandling.WriteUser(USERTYPE.Admin,roshan);
                FileHandling.WriteUser(USERTYPE.Admin,safal);
        }
        else {
            List<User> users= FileHandling.AllUsers(USERTYPE.Admin);
            List<Admin> admins= new ArrayList<>();
            assert users!=null;
            for(User user:users){
                admins.add((Admin)user);
            }
            for(Admin admin:admins){
                if(!admin.equals(roshan) && !admin.equals(safal)){
                    adminFile.delete();
                    init();
                }
            }
        }
        }
}
