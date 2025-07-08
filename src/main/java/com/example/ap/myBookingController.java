package com.example.ap;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class myBookingController {

    @FXML
    private VBox bookingsContainer;

    @FXML
    private Button addNewBookingButton;

    @FXML
    private void initialize() {
        // Initialization code if needed
    }

    @FXML
    private void handleAddNewBooking() {
        System.out.println("Add New Booking clicked");
        // Add your logic here
    }

    @FXML
    private void handleCancelBooking() {
        System.out.println("Cancel Booking clicked");
        // Add your logic here
    }
}