package com.example.ap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private StackPane root;

    @FXML
    private Rectangle overlay;

    @FXML
    private ImageView logoImage;

    @FXML
    private ComboBox<String> userTypeComboBox;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    @FXML
    private Label registerLink;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> userTypes = FXCollections.observableArrayList("Tourist", "Guide");
        userTypeComboBox.setItems(userTypes);
        overlay.widthProperty().bind(root.widthProperty());
        overlay.heightProperty().bind(root.heightProperty());
    }

    @FXML
    private void onLoginButtonClicked() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String userType = userTypeComboBox.getValue();

        if (userType == null) {
            System.out.println("Please select user type!");
            // TODO: Show Alert dialog instead of println for better UX
            return;
        }
        if (username == null || username.isEmpty()) {
            System.out.println("Please enter username!");
            return;
        }
        if (password == null || password.isEmpty()) {
            System.out.println("Please enter password!");
            return;
        }

        if ("Tourist".equals(userType)) {
            System.out.println("Logging in Tourist: " + username);
            // TODO: Tourist login logic here
        } else if ("Guide".equals(userType)) {
            System.out.println("Logging in Guide: " + username);
            // TODO: Guide login logic here
        }
    }

    @FXML
    private void onRegisterLinkClicked() {
        System.out.println("Register link clicked!");
        // TODO: Navigate to registration page
    }
}
