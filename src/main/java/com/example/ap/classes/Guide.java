package com.example.ap.classes;

import com.example.ap.classes.enums.LANGUAGES;

public class Guide extends User{
    private int yearsExperience;
    private boolean availability;
    private LANGUAGES languageSpoken;
    public Guide(int id, String name, String email, String phone,String password,LANGUAGES languageSpoken,int yearsExperience) {
        super(id, name, email, phone,password);
        this.languageSpoken=languageSpoken;
        this.yearsExperience=yearsExperience;
        this.availability=false;
    }
    public int getYearsExperience(){return this.yearsExperience;}
    public boolean isAvailability(){return this.availability;}
    public LANGUAGES getLanguageSpoken(){return this.languageSpoken;}


    public void updateAvailability(boolean available){
        this.availability=available;
    }
    public void incrementYearsExperience(){
        this.yearsExperience++;
    }
    @Override
    public String getDetails(){
        String guide=this.getId()+","+this.getName()+","+this.getEmail()+","+this.getPhoneNumber()+","+this.getPassword()+","+this.getLanguageSpoken()
                +","+this.getYearsExperience()+","+this.isAvailability();
        System.out.println("Guide: "+guide);
        return guide;

    }

    public void viewAssignedBookings(){

    }
}
