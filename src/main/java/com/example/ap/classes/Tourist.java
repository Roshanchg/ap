package com.example.ap.classes;

import com.example.ap.classes.enums.LANGUAGES;

public class Tourist extends User {
    private LANGUAGES languagePref;
    private String nationality;
    private String emergencyContact;

    public Tourist(int id, String name, String email, String phone,String password,LANGUAGES languagePref,String nationality,
                   String emergencyContact) {
        super(id, name, email, phone,password);
        this.nationality=nationality;
        this.languagePref=languagePref ;
        this.emergencyContact=emergencyContact;
    }


    public LANGUAGES getLanguagePref(){return this.languagePref;}
    public String getNationality(){return this.nationality;}
    public String getEmergencyContact(){return this.emergencyContact;}

    @Override
    public String getDetails(){
        String tourist=this.getId()+","+this.getName()+","+this.getEmail()+","+this.getPhoneNumber()+","+this.getPassword()
                +","+this.getLanguagePref()+","+this.getNationality()+","+this.getEmergencyContact();
        System.out.println(tourist);
        return tourist;
    }




}
