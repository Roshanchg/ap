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
        if(adminFile.exists()){
            adminFile.delete();
        }
        else {
            adminFile.createNewFile();
        }
        Admin roshan=new Admin(1,"Roshan Chaulagain","chgroshan6@gmail.com",
                "9823168078","roshan");
        Admin safal=new Admin(2,"Safal Lohani","safal@gmail.com",
                "9800000000","safal");

        try(BufferedWriter bw=new BufferedWriter(new FileWriter(FileHandling.AdminFile,true))){
            bw.write(roshan.getDetails());
            bw.newLine();
            bw.write(safal.getDetails());
        }

        }
}
