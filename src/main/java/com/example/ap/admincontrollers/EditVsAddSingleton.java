package com.example.ap.admincontrollers;

public class EditVsAddSingleton {
    private static boolean isAdd;
    private static int id;
    public static void setAdd(){
        isAdd=true;
    }
    public static boolean isIsAdd(){
        return isAdd;
    }
    public static void setEdit(){
        isAdd=false;
    }
    public static int getId(){
        return id;
    }
    public static void setId(int id){
        EditVsAddSingleton.id=id;
    }
    public static void resetVariables(){
        id=0;
    }
}
