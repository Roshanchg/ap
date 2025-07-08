package com.example.ap;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class touristEditController {
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML private TextField loginIdField;
    @FXML private ComboBox<String> bookingsComboBox;
    @FXML private TextArea contactDetailsArea;

    @FXML
    public void initialize() {
        // Initialize form fields
        bookingsComboBox.getItems().addAll("Any", "1-5", "6-10", "10+");
    }

    @FXML
    private void handleConfirmEdit() {
        // Logic to save tourist edits
        System.out.println("Tourist data saved:");
        System.out.println("Name: " + nameField.getText());
        System.out.println("Email: " + emailField.getText());
        System.out.println("Login ID: " + loginIdField.getText());
        System.out.println("Contact Details: " + contactDetailsArea.getText());
    }

    @FXML
    private void handleConfirmPasswords() {
        // Logic to confirm/update password
        System.out.println("Password confirmed/updated");
    }

    @FXML
    private void handleFilter() {
        // Logic for filtering tourists
        System.out.println("Filtering tourists...");
    }

    @FXML
    private void handleClearSearch() {
        // Logic to clear search/filters
        nameField.clear();
        emailField.clear();
        loginIdField.clear();
        contactDetailsArea.clear();
        bookingsComboBox.setValue("Any");
        System.out.println("Search cleared");
    }
}
