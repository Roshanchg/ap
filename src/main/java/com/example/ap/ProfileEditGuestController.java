package com.example.ap;

import com.example.ap.classes.Guide;
import com.example.ap.classes.enums.LANGUAGES;
import com.example.ap.classes.enums.NAVIGATIONS;
import com.example.ap.classes.enums.USERTYPE;
import com.example.ap.handlers.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class ProfileEditGuestController implements Initializable {

    @FXML
    public TextField YOEfield;
    @FXML
    public CheckBox availabilityField;
    @FXML
    private TextField nameField, emailField, phoneField;

    @FXML
    private TextField newPasswordField, oldPasswordField;

    @FXML
    private ComboBox<LANGUAGES> languageSpokenBox;

    private Guide currentGuide;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int id = SessionHandler.getInstance().getUserId();
        try {
            currentGuide = (Guide) ObjectFinder.getUser(id, USERTYPE.Guide);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (currentGuide != null) {
            nameField.setText(currentGuide.getName());
            emailField.setText(currentGuide.getEmail());
            phoneField.setText(currentGuide.getPhoneNumber());
            if(currentGuide.getAvailability()){
                availabilityField.setSelected(true);
            }
            YOEfield.setText(String.valueOf(currentGuide.getYearsOfExperience()));
            languageSpokenBox.getItems().setAll(LANGUAGES.values());
            languageSpokenBox.setValue(currentGuide.getLanguageSpoken());
            Pattern pattern = Pattern.compile("\\d*");
            UnaryOperator<TextFormatter.Change> filter = change -> {
                String newText = change.getControlNewText();
                return pattern.matcher(newText).matches() ? change : null;
            };
            YOEfield.setTextFormatter(new TextFormatter<>(filter));
        }

    }

    @FXML
    private void saveGuideEdits() throws IOException {
        if (currentGuide == null) {
            showAlert("Error", "Guide not found.");
            return;
        }

        String enteredOldPassword = oldPasswordField.getText();
        if (!enteredOldPassword.equals(currentGuide.getPassword())) {
            showAlert("Incorrect Password", "The old password you entered is incorrect.");
            return;
        }

        String phone = phoneField.getText();

        if (!isValidPhone(phone)) {
            showAlert("Invalid Phone", "Phone number must be exactly 10 digits and numeric.");
            return;
        }


        String name = nameField.getText();
        String email = emailField.getText();

        if(!FileHandling.isEmail(email)){
            showAlert("Invalid Email","Invalid Email Value!!");
            return;
        }
        String password = newPasswordField.getText().isEmpty() ? currentGuide.getPassword() : newPasswordField.getText();

        LANGUAGES languageSpoken = languageSpokenBox.getValue();
        if(YOEfield.getText().isEmpty()){YOEfield.setText("0");}
        int yearsOfExperience=Integer.parseInt(YOEfield.getText());

        Guide updatedGuide = new Guide(
                currentGuide.getId(),
                name,
                email,
                phone,
                password,
                languageSpoken,
                yearsOfExperience
        );

        FileHandling.makeLogs("Edited Guide: " + currentGuide.getDetails() + " to: " + updatedGuide.getDetails());
        FileHandling.editUser(USERTYPE.Guide, currentGuide.getId(), updatedGuide);
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
    public void onViewAssigns(MouseEvent mouseEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.GUIDE,(Stage) nameField.getScene().getWindow());
    }

    @FXML
    public void onLogout(ActionEvent actionEvent) throws IOException {
        SessionHandler.getInstance().endSession();
        Navigator.Navigate(NAVIGATIONS.REGISTER,(Stage) nameField.getScene().getWindow());
    }

    @FXML
    public void onDeleteAccount(ActionEvent actionEvent)throws IOException {
        int id=SessionHandler.getInstance().getUserId();
        FileHandling.removeUser(USERTYPE.Guide,id);
        DeletionHandler.onUserDelete(id,USERTYPE.Guide);
        SessionHandler.getInstance().endSession();
        CacheHandler.ClearCache();
        Navigator.Navigate(NAVIGATIONS.REGISTER,(Stage) nameField.getScene().getWindow());
    }
}
