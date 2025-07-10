package com.example.ap;

import com.example.ap.classes.enums.NAVIGATIONS;
import com.example.ap.handlers.ImportantVariables;
import com.example.ap.handlers.Navigator;
import com.example.ap.handlers.SessionHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.sql.rowset.spi.TransactionalWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TouristDashboardController implements Initializable {

    @FXML private VBox root;
    @FXML private HBox topBar;
    @FXML private Label bookingOption;
    @FXML private Label attractionOption;
    @FXML private Label festivalOption;
    @FXML private Label exploreOption;
    @FXML private Button mainButton;
    @FXML private HBox mainLabel;
    @FXML private HBox bottomBar;

    private String touristName;

    public void setTouristName(String name) {
        this.touristName = name;
//        welcomeLabel.setText("Welcome, " + touristName + "!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            Stage stage = (Stage) bottomBar.getScene().getWindow();
            bottomBar.prefWidthProperty().bind(stage.widthProperty().multiply(0.8));
            bottomBar.prefHeightProperty().bind(stage.heightProperty().multiply(0.2));

            topBar.prefWidthProperty().bind(stage.widthProperty().multiply(0.9));
            topBar.prefHeightProperty().bind(stage.heightProperty().multiply(0.1));

            mainLabel.setPrefWidth(ImportantVariables.screenBounds.getWidth()*0.8);
            mainLabel.setPrefHeight(ImportantVariables.screenBounds.getHeight()*0.4);

        });

    }

    @FXML
    private void onViewAttractions() {
        System.out.println("Navigating to Attractions Page...");
    }

    @FXML
    private void onBookTrip() {
        System.out.println("Navigating to Booking Page...");

    }

    @FXML
    private void onViewBookings() throws IOException {
        System.out.println("Navigating to My Bookings...");
        Navigator.Navigate(NAVIGATIONS.booking,(Stage) bottomBar.getScene().getWindow());
    }

    @FXML
    private void onReportEmergency() {
        System.out.println("Emergency reported!");
    }

    @FXML
    private void onLogout() throws IOException {
        System.out.println("Logging out...");
        SessionHandler.getInstance().endSession();
        Navigator.Navigate(NAVIGATIONS.REGISTER,(Stage) bottomBar.getScene().getWindow());
    }
}
