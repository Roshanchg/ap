package com.example.ap.admincontrollers;

import com.example.ap.AdminControllerBorderPaneSingleton;
import com.example.ap.classes.Attraction;
import com.example.ap.classes.Festival;
import com.example.ap.classes.enums.ATTRACTIONDIFFICULTY;
import com.example.ap.classes.enums.ATTRACTIONTYPE;
import com.example.ap.handlers.FileHandling;
import com.example.ap.handlers.ObjectFinder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class festivalEditController {

    private final String allFestivals = "/com/example/ap/AdminParts/AllFestivals.fxml";

    @FXML
    private TextField nameField;
    @FXML
    private DatePicker startDateField;
    @FXML
    private DatePicker endDateField;
    @FXML
    private TextField discountField;


    @FXML
    public void initialize() throws IOException {

        startDateField.setValue(LocalDate.now());
        endDateField.setValue(LocalDate.now());
        discountField.setText("0");
        discountField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("-?\\d*(\\.\\d*)?")) {
                discountField.setText(oldVal);
            }
        });

    }



    @FXML
    private void handleCancel() throws IOException {
        Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(allFestivals)));
        BorderPane rootPane = AdminControllerBorderPaneSingleton.getMainPane();
        rootPane.setCenter(node);
    }

    @FXML
    private void handleConfirmEdit() throws IOException {

        String name = nameField.getText();
        LocalDate startDate=startDateField.getValue();
        LocalDate endDate=endDateField.getValue();
        if(discountField.getText().trim().isEmpty()){discountField.setText("0");}
        double discount=Double.parseDouble(discountField.getText());


        int id;
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty field/s");
            alert.showAndWait();
            return;
        }
        id = FileHandling.getNextId(FileHandling.FestivalsFile);
        Festival festival=new Festival(id,name,startDate,endDate,discount);
        FileHandling.addFestival(festival);
        FileHandling.makeLogs("Added festival  " + festival.getDetails());

        BorderPane borderPane = AdminControllerBorderPaneSingleton.getMainPane();
        Node allAlerts = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(allFestivals)));
        borderPane.setCenter(allAlerts);
    }
}
