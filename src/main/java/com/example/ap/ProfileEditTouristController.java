package com.example.ap;

import com.example.ap.classes.Tourist;
import com.example.ap.classes.enums.LANGUAGES;
import com.example.ap.classes.enums.NAVIGATIONS;
import com.example.ap.classes.enums.USERTYPE;
import com.example.ap.handlers.FileHandling;
import com.example.ap.handlers.Navigator;
import com.example.ap.handlers.ObjectFinder;
import com.example.ap.handlers.SessionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileEditTouristController implements Initializable {

    @FXML
    private TextField nameField, emailField, phoneField, nationalityField, emergencyContactField;

    @FXML
    private TextField newPasswordField, oldPasswordField;

    @FXML
    private ComboBox<LANGUAGES> languageChoiceBox;

    private Tourist currentTourist;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int id = SessionHandler.getInstance().getUserId();
        try {
            currentTourist = (Tourist) ObjectFinder.getUser(id, USERTYPE.Tourist);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (currentTourist != null) {
            nameField.setText(currentTourist.getName());
            emailField.setText(currentTourist.getEmail());
            phoneField.setText(currentTourist.getPhoneNumber());
            nationalityField.setText(currentTourist.getNationality());
            emergencyContactField.setText(currentTourist.getEmergencyContact());
            languageChoiceBox.getItems().setAll(LANGUAGES.values());
            languageChoiceBox.setValue(currentTourist.getLanguagePref());
        }
    }

    @FXML
    private void saveTouristEdits() throws IOException {
        if (currentTourist == null) {
            showAlert("Error", "Tourist not found.");
            return;
        }

        String enteredOldPassword = oldPasswordField.getText();
        if (!enteredOldPassword.equals(currentTourist.getPassword())) {
            showAlert("Incorrect Password", "The old password you entered is incorrect.");
            return;
        }

        String phone = phoneField.getText();
        String emergencyContact = emergencyContactField.getText();

        if (!isValidPhone(phone)) {
            showAlert("Invalid Phone", "Phone number must be exactly 10 digits and numeric.");
            return;
        }

        if (!isValidPhone(emergencyContact)) {
            showAlert("Invalid Emergency Contact", "Emergency contact must be exactly 10 digits and numeric.");
            return;
        }

        String name = nameField.getText();
        String email = emailField.getText();

        if(!FileHandling.isEmail(email)){
            showAlert("Invalid Email","Invalid Email Value!!");
            return;
        }
        String password = newPasswordField.getText().isEmpty() ? currentTourist.getPassword() : newPasswordField.getText();
        String nationality = nationalityField.getText();
        if(nationality.isEmpty()){
            showAlert("Invalid Nationality","No nationality mentioned");
            return;
        }
        LANGUAGES languagePref = languageChoiceBox.getValue();

        Tourist updatedTourist = new Tourist(
                currentTourist.getId(),
                name,
                email,
                phone,
                password,
                languagePref,
                nationality,
                emergencyContact
        );

        FileHandling.makeLogs("Edited Tourist: " + currentTourist.getDetails() + " to: " + updatedTourist.getDetails());
        FileHandling.editUser(USERTYPE.Tourist, currentTourist.getId(), updatedTourist);
        showAlert("Success", "Your information has been updated successfully.");
    }

    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onViewBookings(MouseEvent mouseEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.booking,(Stage) nameField.getScene().getWindow());
    }

    @FXML
    public void onViewAttractions(MouseEvent mouseEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.attraction,(Stage) nameField.getScene().getWindow());
    }

    @FXML
    public void onViewFestivals(MouseEvent mouseEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.festive,(Stage) nameField.getScene().getWindow());
    }

    @FXML
    public void onLogout(ActionEvent actionEvent) throws IOException {
        SessionHandler.getInstance().endSession();
        Navigator.Navigate(NAVIGATIONS.REGISTER,(Stage) nameField.getScene().getWindow());
    }

    @FXML
    public void onDeleteAccount(ActionEvent actionEvent)throws IOException {
        int id=SessionHandler.getInstance().getUserId();
        FileHandling.removeUser(USERTYPE.Tourist,id);
        SessionHandler.getInstance().endSession();
        Navigator.Navigate(NAVIGATIONS.REGISTER,(Stage) nameField.getScene().getWindow());
    }

    public void onHomePage()throws IOException {
        Navigator.Navigate(NAVIGATIONS.attraction,(Stage) nameField.getScene().getWindow());
    }
}
