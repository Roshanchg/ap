package com.example.ap.classes;

import com.example.ap.classes.enums.ATTRACTIONDIFFICULTY;
import com.example.ap.classes.enums.ATTRACTIONTYPE;

public class Attraction {
    private String name;
    private String location;
    private ATTRACTIONTYPE type;
    private ATTRACTIONDIFFICULTY difficulty;
    private String altitude;
    private boolean restrictedMonsoon;
    private int id;
    public Attraction(int id, String name,String location,ATTRACTIONTYPE type,ATTRACTIONDIFFICULTY difficulty,
                      String altitude,boolean restrictedMonsoon){
        this.name=name;
        this.location=location;
        this.type=type;
        this.difficulty=difficulty;
        this.altitude=altitude;
        this.restrictedMonsoon=restrictedMonsoon;
        this.id=id;
    }

}
