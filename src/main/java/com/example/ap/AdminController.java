package com.example.ap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class AdminController {
    @FXML private PieChart travelPieChart;
    @FXML private LineChart<String, Number> weatherLineChart;

    public void initialize() {
        setupTouristDataGraph();
        setupTravelPieChart();
    }

    private void setupTouristDataGraph() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Paris", 707),
                new PieChart.Data("Bangkok", 400),
                new PieChart.Data("San Francisco", 200)
        );
        travelPieChart.setData(pieChartData);
    }

    private void setupTravelPieChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("KFC", 12));
        series.getData().add(new XYChart.Data<>("FIAT-Chrysler", 8));
        series.getData().add(new XYChart.Data<>("KLM", 15));
        series.getData().add(new XYChart.Data<>("Aeroflot", 6));
        series.getData().add(new XYChart.Data<>("Lukoil", 9));
        series.getData().add(new XYChart.Data<>("American Express", 11));
        series.getData().add(new XYChart.Data<>("Daimler", 7));

    }

}
