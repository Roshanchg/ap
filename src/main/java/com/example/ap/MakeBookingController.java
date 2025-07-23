package com.example.ap;

import com.example.ap.classes.Attraction;
import com.example.ap.classes.Booking;
import com.example.ap.classes.Festival;
import com.example.ap.classes.enums.NAVIGATIONS;
import com.example.ap.classes.enums.USERTYPE;
import com.example.ap.handlers.*;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class MakeBookingController implements Initializable {

    @FXML private HBox topBar;


    @FXML private DatePicker bookingDatePicker;
    @FXML private ComboBox<Festival> festivalComboBox;
    @FXML private Label discountLabel;
    @FXML private Label attractionLabel;
    private List<Festival> activeFestivals;

    private Attraction selectedAttraction;
    private Festival selectedFestival;

    public void onViewBookings(MouseEvent mouseEvent) throws IOException {
        Navigator.Navigate(NAVIGATIONS.booking,(Stage) topBar.getScene().getWindow());
    }

    public void onViewAttractions(MouseEvent mouseEvent) throws  IOException{
        Navigator.Navigate(NAVIGATIONS.attraction,(Stage) topBar.getScene().getWindow());
    }

    public void onViewFestivals(MouseEvent mouseEvent) throws IOException {
        Navigator.Navigate(NAVIGATIONS.festive,(Stage) topBar.getScene().getWindow());
    }

    public void onProfileEdit(ActionEvent actionEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.profileEditTourist,(Stage) topBar.getScene().getWindow());

    }

    public void onSubmit(ActionEvent actionEvent) throws IOException {
        LocalDate bookingDate=bookingDatePicker.getValue();
        if(bookingDate==null){
            showAlert("Invalid Booking Date");
            return;
        }
        Festival festivalBoxValue=festivalComboBox.getValue();
        double festivalDiscountRate=0;
        int fid=0;
        if(festivalBoxValue!=null){
            fid=festivalBoxValue.getId();
            festivalDiscountRate=festivalBoxValue.getDiscountRate();
        }


        Booking booking=new Booking(
                FileHandling.getNextId(FileHandling.BookingsFile),
                SessionHandler.getInstance().getUserId(),
                0,
                selectedAttraction.getId(),
                bookingDate,
                festivalDiscountRate,
                false,
                fid
                );
        FileHandling.MakeBooking(booking);
        CacheHandler.ClearCache();
        ActiveAttractionSingleton.reset();
        ActiveFestivalSingleton.reset();
        Navigator.Navigate(NAVIGATIONS.booking,(Stage) topBar.getScene().getWindow());
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Navigator.Navigate(NAVIGATIONS.attraction,(Stage) topBar.getScene().getWindow());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(ActiveAttractionSingleton.getAid()==0){
            showAlert("No Attraction Selected");
            try {
                Navigator.Navigate(NAVIGATIONS.attraction,(Stage) topBar.getScene().getWindow());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            selectedAttraction= ObjectFinder.getAttraction(ActiveAttractionSingleton.getAid());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(selectedAttraction==null){
            showAlert("Selected Attraction not found");
            return;
        }

        attractionLabel.setText(selectedAttraction.getName());
        try {
            activeFestivals= ObjectFinder.getFestivalForCurrent(LocalDate.now());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(activeFestivals==null){
            festivalComboBox.setValue(null);
            discountLabel.setText("0%");
        }
        festivalComboBox.getItems().addAll(activeFestivals);
        if(ActiveFestivalSingleton.getFid()!=0){
            try {
                selectedFestival=ObjectFinder.getFestive(ActiveFestivalSingleton.getFid());
                festivalComboBox.setValue(selectedFestival);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        discountLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            Festival selected = festivalComboBox.getValue();
            return selected != null ? selected.getDiscountRate() + "%" : "0%";
        }, festivalComboBox.valueProperty()));
    }

    public void showAlert(String message){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Wrong input");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
