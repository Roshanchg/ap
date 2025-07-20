package com.example.ap.classes;

public class User {
    private final int id;
    private String name,email,phoneNumber,password;
    public User(int id,String name,String email,String phone,String password){
        this.id=id;
        this.name=name;
        this.email=email;
        this.phoneNumber=phone;
        this.password=password;
    }
    public String getPassword(){
        return this.password;
    }
    public int getId() {
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public String getDetails(){
        String user=this.getId()+","+this.getName()+","+this.getEmail()+","+this.getPhoneNumber()+","+this.getPassword();
        System.out.println(user);
        return user;
    }

    public void updateProfile(String name,String email,String phoneNumber){
        this.name=name;
        this.email=email;
        this.phoneNumber=phoneNumber;
    }


}
