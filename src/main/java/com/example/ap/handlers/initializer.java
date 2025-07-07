package com.example.ap.handlers;

import com.example.ap.classes.Admin;
import com.example.ap.classes.enums.USERTYPE;

public class initializer {
    public static void main(String[] args){
        Admin admin=new Admin(1,"Roshan Chaulagain","chgroshan6@gmail.com","9898989898","roshan");
        Admin admin2=new Admin(2,"Safal Lohani","safalBaddie@gmail.com","9898989899","safal");
        FileHandling.WriteUser(USERTYPE.Admin,admin);
        FileHandling.WriteUser(USERTYPE.Admin,admin2);
    }
}
