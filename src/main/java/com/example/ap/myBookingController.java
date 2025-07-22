package com.example.ap;

import com.example.ap.classes.Booking;
import com.example.ap.classes.User;
import com.example.ap.handlers.CacheHandler;
import com.example.ap.handlers.SessionHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.print.Book;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


public class myBookingController {

    @FXML
    private VBox bookingsContainer;

    @FXML
    private Button addNewBookingButton;

    @FXML
    private HBox bookingContainer;

    @FXML
    private void initialize() throws IOException {

        int loggedUserId=SessionHandler.getInstance().getUserId();
        List<Booking> myBookings= CacheHandler.getBookingsCache();
        for(Booking booking:myBookings){
        Node bookingCard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("BookingCard.fxml")));
        bookingContainer.getChildren().add(bookingCard);
        }
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