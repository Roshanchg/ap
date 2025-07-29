package com.example.ap.admincontrollers;

import com.example.ap.AdminControllerBorderPaneSingleton;
import com.example.ap.classes.Alerts;
import com.example.ap.classes.Attraction;
import com.example.ap.classes.Tourist;
import com.example.ap.classes.enums.*;
import com.example.ap.handlers.FileHandling;
import com.example.ap.handlers.ObjectFinder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

public class touristEditController {
    private final String allTourists ="/com/example/ap/AdminParts/AllTourists.fxml";
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<LANGUAGES> languagePrefField;
    @FXML private TextField nationalityField;
    @FXML private TextField emergencyContactField;

    @FXML private  TextField passwordField;

    @FXML private Label passwordLabel;

    @FXML
    public void initialize() throws IOException {
        languagePrefField.getItems().addAll(LANGUAGES.values());



        if(EditVsAddSingleton.isIsAdd()) {
            languagePrefField.setValue(LANGUAGES.English);
            EditVsAddSingleton.resetVariables();
            passwordField.setVisible(true);
            passwordLabel.setVisible(true);
        }
        int id=EditVsAddSingleton.getId();
        if(id!=0){
            Tourist tourist= (Tourist) ObjectFinder.getUser(id, USERTYPE.Tourist);
            assert tourist!=null;
            languagePrefField.setValue(tourist.getLanguagePref());
            nameField.setText(tourist.getName());
            emailField.setText(tourist.getEmail());
            phoneField.setText(tourist.getPhoneNumber());
            emergencyContactField.setText(tourist.getEmergencyContact());
            nationalityField.setText(tourist.getNationality());
            passwordField.setVisible(false);
            passwordLabel.setVisible(false);
        }
    }

    @FXML
    private void handleCancel() throws IOException {
        Node node= FXMLLoader.load(Objects.requireNonNull(getClass().getResource(allTourists)));
        BorderPane rootPane= AdminControllerBorderPaneSingleton.getMainPane();
        rootPane.setCenter(node);
    }

    @FXML
    private void handleConfirmEdit() throws IOException {

        String name=nameField.getText();
        String email=emailField.getText();
        String phoneNumber=phoneField.getText();
        String emergencyNumber=emergencyContactField.getText();
        String nationality=nationalityField.getText();
        String password;
        LANGUAGES languagePref=languagePrefField.getValue();

        int id;
        if(name.isEmpty()||languagePref==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty field/s");
            alert.showAndWait();
            return;
        }
        if (!phoneNumber.matches("\\d{10}") || !emergencyNumber.matches("\\d{10}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid phone number and/or emergency number");
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
            if(FileHandling.emailExists(email,USERTYPE.Tourist)){
                showAlert("Invalid Email","This email already exists");
                return;
            }
            if(FileHandling.phoneExists(email,USERTYPE.Tourist)){
                showAlert("Invalid Phone number","This Phone number already exists");
                return;
            }
            Tourist tourist=new Tourist(id,name,email,phoneNumber,password,languagePref,nationality,emergencyNumber);
            FileHandling.WriteUser(USERTYPE.Tourist,tourist);

            FileHandling.makeLogs("Added Tourist:  " + tourist.getName());
            EditVsAddSingleton.resetVariables();
        }
        else{

            id = EditVsAddSingleton.getId();
            if(FileHandling.emailExistsExcept(email,USERTYPE.Tourist,id)) {
                showAlert("Invalid Email","This email exists with other user");
                return;
            }
            if(FileHandling.phoneExistsExcept(phoneNumber,USERTYPE.Tourist,id)) {
                showAlert("Invalid Phone number","This Phone number exists with other user");
                return;
            }
            password= Objects.requireNonNull(ObjectFinder.getUser(id, USERTYPE.Tourist)).getPassword();
            Tourist tourist=new Tourist(id,name,email,phoneNumber,password,languagePref,nationality,emergencyNumber);
            FileHandling.makeLogs("Edited Tourist:  " +
                    Objects.requireNonNull(ObjectFinder.getUser(id,USERTYPE.Tourist)).getDetails()+" to: "+tourist.getDetails());

            FileHandling.editUser(USERTYPE.Tourist,id,tourist);
            EditVsAddSingleton.resetVariables();
        }
        BorderPane borderPane = AdminControllerBorderPaneSingleton.getMainPane();
        Node allAlerts = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(this.allTourists)));
        borderPane.setCenter(allAlerts);
    }


    private void showAlert(String title, String message){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
