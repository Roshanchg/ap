package com.example.ap.admincontrollers;

import com.example.ap.AdminControllerBorderPaneSingleton;
import com.example.ap.classes.Guide;
import com.example.ap.classes.Tourist;
import com.example.ap.classes.enums.LANGUAGES;
import com.example.ap.classes.enums.USERTYPE;
import com.example.ap.handlers.FileHandling;
import com.example.ap.handlers.ObjectFinder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

public class guideEditController {
    private final String allGuides ="/com/example/ap/AdminParts/AllGuides.fxml";
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<LANGUAGES> languageSpokenField;
    @FXML private TextField yearsExperienceField;
    @FXML private CheckBox availabilityField;
    @FXML private PasswordField passwordField;
    @FXML private Label passwordLabel;

    @FXML
    public void initialize() throws IOException {
        languageSpokenField.getItems().addAll(LANGUAGES.values());
        yearsExperienceField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                yearsExperienceField.setText(oldVal);
            }
        });
        if(EditVsAddSingleton.isIsAdd()) {
            languageSpokenField.setValue(LANGUAGES.English);
            EditVsAddSingleton.resetVariables();
            passwordField.setVisible(true);
            passwordLabel.setVisible(true);

        }
        int id=EditVsAddSingleton.getId();
        if(id!=0){
            Guide guide= (Guide) ObjectFinder.getUser(id, USERTYPE.Guide);
            assert guide!=null;
            languageSpokenField.setValue(guide.getLanguageSpoken());
            nameField.setText(guide.getName());
            emailField.setText(guide.getEmail());
            phoneField.setText(guide.getPhoneNumber());
            passwordField.setVisible(false);
            passwordLabel.setVisible(false);
            availabilityField.setSelected(guide.getAvailability());
            yearsExperienceField.setText(String.valueOf(guide.getYearsOfExperience()));
        }
    }

    @FXML
    private void handleCancel() throws IOException {
        Node node= FXMLLoader.load(Objects.requireNonNull(getClass().getResource(allGuides)));
        BorderPane rootPane= AdminControllerBorderPaneSingleton.getMainPane();
        rootPane.setCenter(node);
    }

    @FXML
    private void handleConfirmEdit() throws IOException {

        String name=nameField.getText();
        String email=emailField.getText();
        String phoneNumber=phoneField.getText();
        String password;
        LANGUAGES languageSpoken=languageSpokenField.getValue();
        if(yearsExperienceField.getText().trim().isEmpty()){yearsExperienceField.setText("0");}
        int yearsOfExperience=Integer.parseInt(yearsExperienceField.getText());
        boolean availability=availabilityField.isSelected();
        int id;
        if(name.isEmpty()||languageSpoken==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty field/s");
            alert.showAndWait();
            return;
        }
        if (!phoneNumber.matches("\\d{10}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid phone number");
            alert.showAndWait();
            return;
        }
        if(!FileHandling.isEmail(email)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid email");
            alert.showAndWait();
            return;
        }

        if(EditVsAddSingleton.isIsAdd()) {
            password=passwordField.getText();
            id = FileHandling.getNextId(FileHandling.TouristFile);
            Guide guide=new Guide(id,name,email,phoneNumber,password,languageSpoken,yearsOfExperience);
            guide.updateAvailability(availability);
            FileHandling.WriteUser(USERTYPE.Guide,guide);

            FileHandling.makeLogs("Added Guide:  " + guide.getName());
            EditVsAddSingleton.resetVariables();
        }
        else{
            id = EditVsAddSingleton.getId();
            password= Objects.requireNonNull(ObjectFinder.getUser(id, USERTYPE.Guide)).getPassword();
            Guide guide=new Guide(id,name,email,phoneNumber,password,languageSpoken,yearsOfExperience);
            guide.updateAvailability(availability);
            FileHandling.editUser(USERTYPE.Guide,id,guide);
            EditVsAddSingleton.resetVariables();
            FileHandling.makeLogs("Edited Guide:  " +
                    Objects.requireNonNull(ObjectFinder.getUser(id,USERTYPE.Guide)).getDetails()+" to: "+guide.getDetails());
        }
        BorderPane borderPane = AdminControllerBorderPaneSingleton.getMainPane();
        Node allAlerts = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(this.allGuides)));
        borderPane.setCenter(allAlerts);
    }
}
