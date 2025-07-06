package com.example.ap;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML private VBox roleSelectionBox;
    @FXML private VBox formContainer;
    @FXML private VBox guideFieldsContainer;

    @FXML private TextField nameField;
    @FXML private TextField nationalityField;
    @FXML private TextField emergencyContactField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private TextField addressField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;

    @FXML private TextField guideLanguagesField;
    @FXML private TextField guideExperienceField;
    @FXML private TextField guideContactField;

    @FXML private ImageView logoImage;

    private String selectedRole = ""; // "Tourist" or "Guide"

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoImage.setImage(new Image(getClass().getResourceAsStream("/images/logo.png")));
    }

    @FXML
    private void onTouristSelected() {
        selectedRole = "Tourist";
        roleSelectionBox.setVisible(false);
        roleSelectionBox.setManaged(false);
        formContainer.setVisible(true);
        formContainer.setManaged(true);
        guideFieldsContainer.setVisible(false);
        guideFieldsContainer.setManaged(false);
    }

    @FXML
    private void onGuideSelected() {
        selectedRole = "Guide";
        roleSelectionBox.setVisible(false);
        roleSelectionBox.setManaged(false);
        formContainer.setVisible(true);
        formContainer.setManaged(true);
        guideFieldsContainer.setVisible(true);
        guideFieldsContainer.setManaged(true);
    }

    @FXML
    private void onRegisterButtonClicked() {
        if (selectedRole.isEmpty()) {
            showAlert("Please select a role first.");
            return;
        }

        if (nameField.getText().isEmpty() || emailField.getText().isEmpty()
                || passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()) {
            showAlert("Please fill all required fields.");
            return;
        }

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showAlert("Passwords do not match.");
            return;
        }

        if ("Guide".equals(selectedRole)) {
            if (guideLanguagesField.getText().isEmpty() || guideExperienceField.getText().isEmpty() || guideContactField.getText().isEmpty()) {
                showAlert("Please complete guide-specific fields.");
                return;
            }
        }

        System.out.println("Registered as " + selectedRole + ": " + nameField.getText());
        // Proceed to dashboard or save user info
    }

    @FXML
    private void onLoginLinkClicked() {
        System.out.println("Redirect to Login page...");
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
