package com.example.ap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class AdminController {
    @FXML private PieChart touristPieChart;
    @FXML private BarChart<String, Number> travelBarChart;
    @FXML private LineChart<String, Number> weatherLineChart;

    public void initialize() {
        setupTouristPieChart();
        setupTravelBarChart();
        setupWeatherLineChart();
    }

    private void setupTouristPieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Paris", 707),
                new PieChart.Data("Bangkok", 400),
                new PieChart.Data("San Francisco", 200)
        );
        touristPieChart.setData(pieChartData);
    }

    private void setupTravelBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("KFC", 12));
        series.getData().add(new XYChart.Data<>("FIAT-Chrysler", 8));
        series.getData().add(new XYChart.Data<>("KLM", 15));
        series.getData().add(new XYChart.Data<>("Aeroflot", 6));
        series.getData().add(new XYChart.Data<>("Lukoil", 9));
        series.getData().add(new XYChart.Data<>("American Express", 11));
        series.getData().add(new XYChart.Data<>("Daimler", 7));

        travelBarChart.getData().add(series);
        travelBarChart.setCategoryGap(20);
    }

    private void setupWeatherLineChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Mon", 15));
        series.getData().add(new XYChart.Data<>("Tue", 22));
        series.getData().add(new XYChart.Data<>("Wed", 18));
        series.getData().add(new XYChart.Data<>("Thu", 25));
        series.getData().add(new XYChart.Data<>("Fri", 30));
        series.getData().add(new XYChart.Data<>("Sat", 28));
        series.getData().add(new XYChart.Data<>("Sun", 20));

        weatherLineChart.getData().add(series);
    }
}
