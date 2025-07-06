package com.example.ap;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class GuideDashboardController implements Initializable {

    @FXML
    private Label welcomeLabel;

    private String guideName;

    public void setGuideName(String name) {
        this.guideName = name;
        welcomeLabel.setText("Welcome, " + guideName + "!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeLabel.setText("Welcome, Guide!");
    }

    @FXML
    private void onViewBookings() {
        System.out.println("Guide viewing assigned bookings...");
        // TODO: Load bookings assigned to this guide
    }

    @FXML
    private void onViewAttractions() {
        System.out.println("Guide viewing attractions...");
        // TODO: Load attraction info
    }

    @FXML
    private void onUpdateProfile() {
        System.out.println("Guide updating profile...");
        // TODO: Load profile editor
    }

    @FXML
    private void onHandleEmergency() {
        System.out.println("Guide responding to emergency...");
        // TODO: Respond to latest emergencies
    }

    @FXML
    private void onLogout() {
        System.out.println("Guide logging out...");
        // TODO: Return to login/startup scene
    }
}
