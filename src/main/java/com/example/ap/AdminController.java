package com.example.ap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.Objects;

public class AdminController {
    @FXML public Button BookingsBt;
    @FXML private  BorderPane mainBorderPane;




    public void initialize() throws IOException {
        Node touristControl = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ap/AdminParts/DashBoard.fxml")));
        mainBorderPane.setCenter(touristControl);
        AdminControllerBorderPaneSingleton.setMainPane(mainBorderPane);
    }

//    Tools Views
    @FXML
    public void onTouristControl() throws IOException {
        Node touristControl = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ap/AdminParts/AllTourists.fxml")));
        mainBorderPane.setCenter(touristControl);
    }

    @FXML
    public void onGuideControl() throws IOException {
        Node touristControl = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ap/AdminParts/AllGuides.fxml")));
        mainBorderPane.setCenter(touristControl);
    }

    @FXML
    public void onAttractionControl() throws IOException {
        Node touristControl = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ap/AdminParts/AllAttractions.fxml")));
        mainBorderPane.setCenter(touristControl);
    }

    @FXML
    public void onFestivalsControl() throws IOException {
        Node touristControl = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ap/AdminParts/AllFestivals.fxml")));
        mainBorderPane.setCenter(touristControl);
    }

    @FXML
    public void onBookingsControl() throws IOException {
        Node touristControl = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ap/AdminParts/AllBookings.fxml")));
        mainBorderPane.setCenter(touristControl);
    }

    @FXML
    public void onLogsControl() throws IOException {
        Node touristControl = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ap/AdminParts/AllLogs.fxml")));
        mainBorderPane.setCenter(touristControl);
    }

    @FXML
    public void onReportControl() throws IOException {
        Node touristControl = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ap/AdminParts/AllAlerts.fxml")));
        mainBorderPane.setCenter(touristControl);
    }

    @FXML
    public void onDashboard() throws IOException {
        Node touristControl = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ap/AdminParts/DashBoard.fxml")));
        mainBorderPane.setCenter(touristControl);
    }
}
