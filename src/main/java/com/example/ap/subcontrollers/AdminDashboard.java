package com.example.ap.subcontrollers;

import com.example.ap.AdminControllerBorderPaneSingleton;
import com.example.ap.classes.Alerts;
import com.example.ap.classes.Attraction;
import com.example.ap.classes.EmergencyLog;
import com.example.ap.handlers.AdminDashboardHandler;

import com.example.ap.handlers.FileHandling;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class AdminDashboard implements Initializable {

    @FXML
    public VBox emergencyLogsContainer;
    @FXML
    public VBox alertsContainer;
    @FXML
    private LineChart<String, Number> touristDataChart;

    @FXML
    private PieChart travelPieChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadTouristDataChart();
            loadTravelPieChart();
            loadEmergencyLogs();
            loadAlerts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTouristDataChart() throws IOException {
        Map<LocalDate, Integer> bookingData = AdminDashboardHandler.getBookingDateChartMap();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Bookings");

        bookingData.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    String date = entry.getKey().toString();
                    Integer count = entry.getValue();
                    series.getData().add(new XYChart.Data<>(date, count));
                });

        touristDataChart.getData().add(series);
    }

    private void loadTravelPieChart() throws IOException {
        Map<Attraction, Integer> attractionMap = AdminDashboardHandler.getAttractionBookingMap();

        for (Map.Entry<Attraction, Integer> entry : attractionMap.entrySet()) {
            String name = entry.getKey().getName();
            int count = entry.getValue();
            travelPieChart.getData().add(new PieChart.Data(name, count));
        }
    }

    private void loadEmergencyLogs() throws IOException {
        List<EmergencyLog> logs = AdminDashboardHandler.getEmergencyLogs();
        emergencyLogsContainer.getChildren().clear();
        if(logs==null) return;
        for (EmergencyLog log : logs) {
            Label label = new Label(log.getMessage()); // use your actual getter
            label.getStyleClass().add("info-text");
            emergencyLogsContainer.getChildren().add(label);
        }
    }
    private void loadAlerts() throws IOException {
        List<Alerts> alerts = AdminDashboardHandler.getAlerts();
        alertsContainer.getChildren().clear();
        if (alerts==null) return;
        for (Alerts alert : alerts) {
            Label label = new Label(alert.getRiskType()+" "+alert.getMessage()+" active for "+alert.getMonthsActive()+" months"); // use your actual getter
            label.getStyleClass().add("info-text");
            alertsContainer.getChildren().add(label);
        }
    }

    public void createAlert(ActionEvent actionEvent) throws IOException {
        BorderPane mainBorderPane= AdminControllerBorderPaneSingleton.getMainPane();
        Node touristControl = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ap/AdminParts/AllAlerts.fxml")));
        mainBorderPane.setCenter(touristControl);
    }

    public void exportLog(ActionEvent actionEvent) throws IOException{
        FileHandling.exportEmergencyLogs();
    }
}
