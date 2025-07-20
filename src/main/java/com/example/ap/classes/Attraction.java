package com.example.ap.classes;

import com.example.ap.classes.enums.ATTRACTIONDIFFICULTY;
import com.example.ap.classes.enums.ATTRACTIONTYPE;

public class Attraction {
    private int id;
    private String name;
    private String location;
    private ATTRACTIONTYPE type;
    private ATTRACTIONDIFFICULTY difficulty;
    private String altitude;
    private boolean restrictedMonsoon;
    public Attraction(int id, String name,String location,ATTRACTIONTYPE type,ATTRACTIONDIFFICULTY difficulty,
                      String altitude,boolean restrictedMonsoon){
        this.id=id;
        this.name=name;
        this.location=location;
        this.type=type;
        this.difficulty=difficulty;
        this.altitude=altitude;
        this.restrictedMonsoon=restrictedMonsoon;
    }

    public String getDetails(){
        return this.id+","+this.name+","+this.location+","+this.type+","+this.difficulty+","+this.altitude+","+this.restrictedMonsoon;

    }
    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public ATTRACTIONDIFFICULTY getDifficulty(){return this.difficulty;}
    public String getLocation(){return this.location;}
    public String getAltitude(){return this.altitude;}
    public boolean getRestrictedMonsoon(){return this.restrictedMonsoon;}
    public ATTRACTIONTYPE getType(){return this.type;}
}
