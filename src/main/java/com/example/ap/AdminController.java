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
    @FXML private PieChart travelPieChart;
    @FXML private LineChart<String, Number> weatherLineChart;
    @FXML private  BorderPane mainBorderPane;
    @FXML private Button DashboardBt;
    @FXML private Button TouristControlBt;
    @FXML private Button AttractionsBt;
    @FXML private Button FestivalsBt;
    @FXML private Button GuidesBt;
    @FXML private Button LogsBt;
    @FXML private Button AlertBt;


    public void initialize() {

        AdminControllerBorderPaneSingleton.setMainPane(mainBorderPane);
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
