package com.example.ap;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TouristDashboardController implements Initializable {

    @FXML
    private Label welcomeLabel;

    private String touristName;

    public void setTouristName(String name) {
        this.touristName = name;
        welcomeLabel.setText("Welcome, " + touristName + "!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Default placeholder if name not set
        welcomeLabel.setText("Welcome, Tourist!");
    }

    @FXML
    private void onViewAttractions() {
        System.out.println("Navigating to Attractions Page...");
        // TODO: Load Attractions Scene
    }

    @FXML
    private void onBookTrip() {
        System.out.println("Navigating to Booking Page...");
        // TODO: Load Booking Scene
    }

    @FXML
    private void onViewBookings() {
        System.out.println("Navigating to My Bookings...");
        // TODO: Load My Bookings Scene
    }

    @FXML
    private void onReportEmergency() {
        System.out.println("Emergency reported!");
        // TODO: Log and alert emergency
    }

    @FXML
    private void onLogout() {
        System.out.println("Logging out...");
        // TODO: Return to startup/login scene
    }
}
