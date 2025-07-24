package com.example.ap;

import com.example.ap.classes.Attraction;
import com.example.ap.classes.enums.NAVIGATIONS;
import com.example.ap.handlers.FileHandling;
import com.example.ap.handlers.Navigator;
import com.example.ap.handlers.ObjectFinder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AttractionsController implements Initializable {

    @FXML
    private FlowPane attractionContainer;

    @FXML
    private TextField filterField;

    private List<Attraction> attractions;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            attractions = FileHandling.AllAttraction();
            assert attractions!=null;
            displayAttractions(attractions);
        } catch (IOException e) {
            e.printStackTrace();
        }

        filterField.textProperty().addListener((obs, oldVal, newVal) -> {
            List<Attraction> filtered = attractions.stream()
                    .filter(a -> a.getName().toLowerCase().contains(newVal.toLowerCase()))
                    .collect(Collectors.toList());
            displayAttractions(filtered);
        });
    }

    private void displayAttractions(List<Attraction> list) {
        ResourceBundle bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
        attractionContainer.getChildren().clear();
        for (Attraction a : list) {
            VBox card = new VBox(6);
            card.setPadding(new Insets(10));
            card.setStyle("attraction-card");
            card.getStyleClass().add("attraction-card");

            card.getChildren().addAll(
                    new Label(bundle.getString("nameAttractionCard") + ": " + a.getName()),
                    new Label(bundle.getString("locationAttractionCard") + ": " + a.getLocation()),
                    new Label(bundle.getString("typeAttractionCard") + ": " + a.getType().toString()),
                    new Label(bundle.getString("difficultyAttractionCard") + ": " + a.getDifficulty().toString()),
                    new Label(bundle.getString("altitudeAttractionCard") + ": " + a.getAltitude()),
                    new Label(bundle.getString("restrictedAttractionCard") + ": " + (a.getRestrictedMonsoon() ? "⚠ " + bundle.getString("yesOption") : bundle.getString("noOption")))
            );

            Button bookBtn = new Button("Book");
            bookBtn.getStyleClass().add("book-button");
            bookBtn.setOnAction(e -> {
                try {
                    handleBooking(a);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            card.getChildren().add(bookBtn);
            attractionContainer.getChildren().add(card);
        }
    }

    private void handleBooking(Attraction attraction) throws IOException {
        ActiveAttractionSingleton.reset();
        System.out.println("Booking attraction: " + attraction.getName());
        ActiveAttractionSingleton.setAid(attraction.getId());
        Navigator.Navigate(NAVIGATIONS.MakeBooking,(Stage) attractionContainer.getScene().getWindow());
    }
    @FXML
    public void onHomePage(MouseEvent mouseEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.TOURIST,(Stage) attractionContainer.getScene().getWindow());
    }

    @FXML
    public void onProfileEdit(MouseEvent mouseEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.profileEditTourist,(Stage) attractionContainer.getScene().getWindow());
    }

    @FXML
    public void onViewBookings(MouseEvent mouseEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.booking,(Stage) attractionContainer.getScene().getWindow());
    }

    @FXML
    public void onViewFestivals(MouseEvent mouseEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.festive,(Stage) attractionContainer.getScene().getWindow());
    }
}
