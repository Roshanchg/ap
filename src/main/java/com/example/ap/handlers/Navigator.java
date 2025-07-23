package com.example.ap.handlers;

import com.example.ap.LocaleStorageSingleton;
import com.example.ap.classes.Guide;
import com.example.ap.classes.Tourist;
import com.example.ap.classes.enums.LANGUAGES;
import com.example.ap.classes.enums.NAVIGATIONS;
import com.example.ap.classes.enums.USERTYPE;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Navigator {
    public static FXMLLoader activeLoader;
    public static String activeTitle;
    public static boolean resizable;
    @FXML
    public static void Navigate(NAVIGATIONS nav, Stage stage) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
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
                Tourist tourist=(Tourist) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Tourist);
                assert tourist != null;
                if(tourist.getLanguagePref()== LANGUAGES.Nepali){
                    LocaleStorageSingleton.setLocaleNp();
                }
                else{
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
                activeLoader= new FXMLLoader(Navigator.class.getResource("/com/example/ap/touristDashboard.fxml"), bundle);
                activeTitle="Tourist Page";
                resizable=false;
            }
            case GUIDE -> {
                Guide guide=(Guide) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Guide);
                assert guide != null;
                if(guide.getLanguageSpoken()== LANGUAGES.Nepali){
                    LocaleStorageSingleton.setLocaleNp();
                }
                else{
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/ap/guideDashboard.fxml"),bundle);
                activeTitle="Guide Page";
                resizable=false;
            }
            case booking -> {
                Tourist tourist=(Tourist) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Tourist);
                assert tourist != null;
                if(tourist.getLanguagePref()== LANGUAGES.Nepali){
                    LocaleStorageSingleton.setLocaleNp();
                }
                else{
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/ap/myBooking.fxml"),bundle);
                activeTitle="My Bookings Page";
                resizable=false;
            }
            case attraction -> {
                Tourist tourist=(Tourist) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Tourist);
                assert tourist != null;
                if(tourist.getLanguagePref()== LANGUAGES.Nepali){
                    LocaleStorageSingleton.setLocaleNp();
                }
                else{
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/ap/viewAttractions.fxml"),bundle);
                activeTitle="All Attractions Page";
                resizable=false;
            }
            case festive -> {
                Tourist tourist=(Tourist) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Tourist);
                assert tourist != null;
                if(tourist.getLanguagePref()== LANGUAGES.Nepali){
                    LocaleStorageSingleton.setLocaleNp();
                }
                else{
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/ap/viewFestivals.fxml"),bundle);
                activeTitle="All Attractions Page";
                resizable=false;
            }
            case userEdit -> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/ap/AdminParts/TouristControl.fxml"));
                activeTitle="Guide Page";
                resizable=false;
            }
            case profileEditTourist -> {
                Tourist tourist=(Tourist) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Tourist);
                assert tourist != null;
                if(tourist.getLanguagePref()== LANGUAGES.Nepali){
                    LocaleStorageSingleton.setLocaleNp();
                }
                else{
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/ap/ProfileEditTourist.fxml"),bundle);
                activeTitle="Edit User Page";
                resizable=false;
            }
            case profileEditGuide -> {

            }
            case MakeBooking -> {
                Tourist tourist=(Tourist) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Tourist);
                assert tourist != null;
                if(tourist.getLanguagePref()== LANGUAGES.Nepali){
                    LocaleStorageSingleton.setLocaleNp();
                }
                else{
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/ap/MakeBooking.fxml"),bundle);
                activeTitle="Make Booking";
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
