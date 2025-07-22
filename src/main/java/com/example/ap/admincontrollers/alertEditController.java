package com.example.ap.admincontrollers;

import com.example.ap.AdminControllerBorderPaneSingleton;
import com.example.ap.classes.Alerts;
import com.example.ap.classes.Attraction;
import com.example.ap.classes.enums.ALERTRISK;
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

public class alertEditController {

    private final String allAlerts ="/com/example/ap/AdminParts/AllAlerts.fxml";
    @FXML private TextField monthsActiveField;
    @FXML private ComboBox<ALERTRISK> riskTypeField;
    @FXML private TextField messageField;



    @FXML
    public void initialize() throws IOException {
        riskTypeField.getItems().addAll(ALERTRISK.values());
        monthsActiveField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[1-9]\\d*")) {
                monthsActiveField.setText(oldVal);
            }
        });

        if(EditVsAddSingleton.isIsAdd()) {
            riskTypeField.setValue(ALERTRISK.HEAVY_RAINFALL);
            monthsActiveField.setText("1");
            EditVsAddSingleton.resetVariables();

        }
        int id=EditVsAddSingleton.getId();
        if(id!=0){
            Alerts alert= ObjectFinder.getAlert(id);
            assert alert!=null;
            riskTypeField.setValue(alert.getRiskType());
            messageField.setText(alert.getMessage());
            monthsActiveField.setText(String.valueOf(alert.getMonthsActive()));
        }
    }

    @FXML
    private void handleCancel() throws IOException {
        Node node= FXMLLoader.load(Objects.requireNonNull(getClass().getResource(allAlerts)));
        BorderPane rootPane= AdminControllerBorderPaneSingleton.getMainPane();
        rootPane.setCenter(node);
    }

    @FXML
    private void handleConfirmEdit() throws IOException {

        ALERTRISK riskType = riskTypeField.getValue();
        String message = messageField.getText();
        if(monthsActiveField.getText().trim().isEmpty()){monthsActiveField.setText("0");}
        int monthsActive=Integer.parseInt(monthsActiveField.getText());
        int id;
        if(message.isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty field/s");
            alert.showAndWait();
            return;
        }
        if(EditVsAddSingleton.isIsAdd()) {
            id = FileHandling.getNextId(FileHandling.AlertsFile);
            Alerts alert=new Alerts(id,riskType,message,monthsActive);
            FileHandling.AddAlerts(alert);

            FileHandling.makeLogs("Added Alert:  " + alert.getDetails());
            EditVsAddSingleton.resetVariables();
        }
        else{
            id = EditVsAddSingleton.getId();
            Alerts alert=new Alerts(id,riskType,message,monthsActive);
            FileHandling.makeLogs("Edited Alert:  " +
                    Objects.requireNonNull(ObjectFinder.getAlert(id)).getDetails()+" to: "+alert.getDetails());

            FileHandling.editAlert(id,alert);
            EditVsAddSingleton.resetVariables();
        }
        BorderPane borderPane = AdminControllerBorderPaneSingleton.getMainPane();
        Node allAlerts = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(this.allAlerts)));
        borderPane.setCenter(allAlerts);
    }
}
