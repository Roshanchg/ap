package com.example.ap.handlers;

import com.example.ap.classes.enums.NAVIGATIONS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigator {
    public static FXMLLoader activeLoader;
    public static String activeTitle;
    public static boolean resizable;
    @FXML
    public static void Navigate(NAVIGATIONS nav, Stage stage) throws IOException {
        switch(nav){
            case ADMIN -> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/ap/adminDashboard.fxml"));
                activeTitle="Admin Dashboard";
                resizable=false;
            }
            case LOGIN -> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/ap/loginPage.fxml"));
                activeTitle="Login Page";
                resizable=false;
            }
            case REGISTER -> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/ap/registerPage.fxml"));
                activeTitle="Register Page ";
                resizable=false;
            }
            case TOURIST -> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/ap/touristDashboard.fxml"));
                activeTitle="Tourist Page";
                resizable=false;
            }
            case GUIDE -> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/ap/guideDashboard.fxml"));
                activeTitle="Guide Page";
                resizable=false;
            }
            case booking -> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/ap/myBooking.fxml"));
                activeTitle="Guide Page";
                resizable=false;
            }
            case attraction -> {

            }
            case festive -> {

            }
            case userEdit -> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/ap/touristControl.fxml"));
                activeTitle="Guide Page";
                resizable=false;
            }
            default -> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/ap/registerPage.fxml"));
                activeTitle="Register Page";
                resizable=false;
            }
        }
        Parent root=activeLoader.load();
        Scene scene=new Scene(root,1300,720);
        stage.setTitle(activeTitle);

        stage.setResizable(resizable);
        stage.setScene(scene);
        stage.show();
    }
}
