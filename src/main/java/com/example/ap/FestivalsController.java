package com.example.ap;

import com.example.ap.classes.Festival;  // your Festival class
import com.example.ap.classes.enums.NAVIGATIONS;
import com.example.ap.handlers.FileHandling;
import com.example.ap.handlers.Navigator;
import com.example.ap.handlers.ObjectFinder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FestivalsController implements Initializable {

    @FXML
    private FlowPane festivalContainer;

    @FXML
    private TextField filterField;

    private List<Festival> festivals;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            festivals = ObjectFinder.getFestivalForCurrent(LocalDate.now());  // method to load festivals
            displayFestivals(festivals);
        } catch (IOException e) {
            e.printStackTrace();
        }

        filterField.textProperty().addListener((obs, oldVal, newVal) -> {
            List<Festival> filtered = festivals.stream()
                    .filter(f -> f.getName().toLowerCase().contains(newVal.toLowerCase()))
                    .collect(Collectors.toList());
            displayFestivals(filtered);
        });
    }

    private void displayFestivals(List<Festival> list) {
        ResourceBundle bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
        festivalContainer.getChildren().clear();

        for (Festival f : list) {
            VBox card = new VBox(6);
            card.setPadding(new Insets(10));
            card.getStyleClass().add("festival-card");

            card.getChildren().addAll(
                    new Label(bundle.getString("festivalCardName")+": " + f.getName()),
                    new Label(bundle.getString("festivalCardSDate")+": " + f.getStartDate().format(dateFormatter)),
                    new Label(bundle.getString("festivalCardEDate")+": " + f.getEndDate().format(dateFormatter)),
                    new Label(bundle.getString("festivalCardDiscount")+": " + f.getDiscountRate() + "%")
            );

            Button bookBtn = new Button("Book");
            bookBtn.getStyleClass().add("book-button");
            bookBtn.setOnAction(e -> {
                try {
                    handleBooking(f);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            card.getChildren().add(bookBtn);
            festivalContainer.getChildren().add(card);
        }
    }

    private void handleBooking(Festival festival) throws IOException{
        System.out.println("Booking festival: " + festival.getName());
        ActiveFestivalSingleton.setFid(festival.getId());
        Navigator.Navigate(NAVIGATIONS.attraction,(Stage) festivalContainer.getScene().getWindow());
    }
    public void onHomePage(MouseEvent mouseEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.TOURIST,(Stage) festivalContainer.getScene().getWindow());
    }

    public void onProfileEdit(MouseEvent mouseEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.profileEditTourist,(Stage) festivalContainer.getScene().getWindow());
    }
    public void onViewBookings(MouseEvent mouseEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.booking,(Stage) festivalContainer.getScene().getWindow());
    }

    public void onViewAttractions(MouseEvent mouseEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.attraction,(Stage) festivalContainer.getScene().getWindow());
    }

}
