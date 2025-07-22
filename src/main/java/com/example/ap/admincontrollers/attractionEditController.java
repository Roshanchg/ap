package com.example.ap.admincontrollers;

import com.example.ap.AdminControllerBorderPaneSingleton;
import com.example.ap.classes.Attraction;
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
import java.util.Objects;

public class attractionEditController {

    private final String allAttractions="/com/example/ap/AdminParts/AllAttractions.fxml";

    @FXML
    private TextField nameField;
    @FXML private TextField altitudeField;
    @FXML private ComboBox<ATTRACTIONTYPE> typeField;
    @FXML private TextField locationField;
    @FXML private ComboBox<ATTRACTIONDIFFICULTY> difficultyField;
    @FXML private ComboBox<Boolean> restrictedMonsoonField;



    @FXML
    public void initialize() throws IOException {
        typeField.getItems().addAll(ATTRACTIONTYPE.values());
        difficultyField.getItems().addAll(ATTRACTIONDIFFICULTY.values());
        restrictedMonsoonField.getItems().addAll(true,false);

        if(EditVsAddSingleton.isIsAdd()) {
            typeField.setValue(ATTRACTIONTYPE.Camping);
            difficultyField.setValue(ATTRACTIONDIFFICULTY.HIGH);
            restrictedMonsoonField.setValue(true);
            EditVsAddSingleton.resetVariables();
        }
        int id=EditVsAddSingleton.getId();
        if(id!=0){
            Attraction attraction= ObjectFinder.getAttraction(id);
            assert attraction!=null;
            nameField.setText(attraction.getName());
            locationField.setText(attraction.getLocation());
            difficultyField.setValue(attraction.getDifficulty());
            typeField.setValue(attraction.getType());
            altitudeField.setText(attraction.getAltitude());
            restrictedMonsoonField.setValue(attraction.getRestrictedMonsoon());
        }
    }

    @FXML
    private void handleCancel() throws IOException {
        Node node=FXMLLoader.load(Objects.requireNonNull(getClass().getResource(allAttractions)));
        BorderPane rootPane=AdminControllerBorderPaneSingleton.getMainPane();
        rootPane.setCenter(node);
    }

    @FXML
    private void handleConfirmEdit() throws IOException {

        String name = nameField.getText();
        String location = locationField.getText();
        String altitude = altitudeField.getText();
        ATTRACTIONTYPE type = typeField.getValue();
        ATTRACTIONDIFFICULTY difficulty = difficultyField.getValue();
        boolean restrictedMonsoon = restrictedMonsoonField.getValue();
        int id;
        if(name.isEmpty() || location.isEmpty()||altitude.isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty field/s");
            alert.showAndWait();
            return;
        }
        if(EditVsAddSingleton.isIsAdd()) {
            System.out.println("HEllo");
             id = FileHandling.getNextId(FileHandling.AttractionsFile);
            Attraction attraction = new Attraction(id, name, location, type, difficulty, altitude, restrictedMonsoon);
            FileHandling.AddAttraction(attraction);

            FileHandling.makeLogs("Added Attraction  " + attraction.getDetails());

        }
        else{
            id = EditVsAddSingleton.getId();
            Attraction attraction = new Attraction(id, name, location, type, difficulty, altitude, restrictedMonsoon);
            FileHandling.makeLogs("Edited Attraction:  " +
                    Objects.requireNonNull(ObjectFinder.getAttraction(id)).getDetails()+" to: "+attraction.getDetails());

            FileHandling.editAttraction(id,attraction);
            EditVsAddSingleton.resetVariables();
        }
        BorderPane borderPane = AdminControllerBorderPaneSingleton.getMainPane();
        Node allAlerts = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(allAttractions)));
        borderPane.setCenter(allAlerts);
    }
}
