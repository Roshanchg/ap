package com.example.ap.classes;

import com.example.ap.classes.enums.ATTRACTIONDIFFICULTY;
import com.example.ap.classes.enums.ATTRACTIONTYPE;

public class Attraction {
    private String name;
    private String location;
    private ATTRACTIONTYPE type;
    private ATTRACTIONDIFFICULTY difficulty;
    private int altitude;
    private boolean restrictedMonsoon;
    private int id;
    public Attraction(String name,String location,ATTRACTIONTYPE type,ATTRACTIONDIFFICULTY difficulty,
                      int altitude,boolean restrictedMonsoon,int id){
        this.name=name;
        this.location=location;
        this.type=type;
        this.difficulty=difficulty;
        this.altitude=altitude;
        this.restrictedMonsoon=restrictedMonsoon;
        this.id=id;
    }

}
