package com.example.ap;

public class ActiveFestivalSingleton {
    private static int fid=0;
    public static int getFid(){
        return fid;
    }
    public static void setFid(int id){
        fid=id;
    }
    public static void reset(){
        fid=0;
    }
}
