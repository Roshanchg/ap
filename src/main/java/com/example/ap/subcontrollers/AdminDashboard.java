package com.example.ap.subcontrollers;

import com.example.ap.AdminControllerBorderPaneSingleton;
import com.example.ap.classes.Alerts;
import com.example.ap.classes.Attraction;
import com.example.ap.classes.EmergencyLog;
import com.example.ap.handlers.AdminDashboardHandler;

import com.example.ap.handlers.FileHandling;
import com.example.ap.handlers.ObjectFinder;
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
        Map<Integer, Integer> attractionMap = AdminDashboardHandler.getAttractionBookingMap();

        for (Map.Entry<Integer, Integer> entry : attractionMap.entrySet()) {
            if(entry.getKey()==0) return;
            String name = Objects.requireNonNull(ObjectFinder.getAttraction(entry.getKey())).getName();
            int count = entry.getValue();
            travelPieChart.getData().add(new PieChart.Data(name, count));
        }
    }

    private void loadEmergencyLogs() throws IOException {
        List<EmergencyLog> logs = AdminDashboardHandler.getEmergencyLogs();
        emergencyLogsContainer.getChildren().clear();
        if(logs==null) return;
        String message;
        String username;
        String[] messagearray;
        for (EmergencyLog log : logs) {
            messagearray=log.getMessage().split(" ",2);
            username=messagearray[0].trim();
            message=messagearray[1].trim();
            Label label = new Label("From: "+username+", "+message);
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

    public void exportReport(ActionEvent actionEvent) throws IOException {
        FileHandling.makeReport();
    }

    public void exportLog(ActionEvent actionEvent) throws IOException{
        FileHandling.exportEmergencyLogs();
    }
}
