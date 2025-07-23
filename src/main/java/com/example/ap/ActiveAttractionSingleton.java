package com.example.ap;

public class ActiveAttractionSingleton {
    public static int aid=0;
    public static void reset(){
        aid=0;
    }
    public static void setAid(int id){
        aid=id;
    }
    public static int getAid(){return aid;}
}
