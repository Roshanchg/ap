package com.example.ap;

import com.example.ap.classes.enums.USERTYPE;
import com.example.ap.handlers.FileHandling;
import com.example.ap.handlers.SessionHandler;
import com.example.ap.handlers.UserHandling;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private ComboBox<USERTYPE> userTypeComboBox;

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
        ObservableList<USERTYPE> userTypes = FXCollections.observableArrayList(USERTYPE.values());
        userTypeComboBox.setItems(userTypes);
        overlay.widthProperty().bind(root.widthProperty());
        overlay.heightProperty().bind(root.heightProperty());
    }

    @FXML
    private void onLoginButtonClicked() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        USERTYPE userType = userTypeComboBox.getValue();
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("INVALID INFO");

        if (userType == null) {
//            System.out.println("Please select user type!");
            alert.setContentText("Please select a user type! ");
            alert.showAndWait();
            return;
        }
        if (username == null || username.isEmpty()) {
//            System.out.println("Please enter username!");
            alert.setContentText("Please enter an email or phone number! ");
            alert.showAndWait();
            return;
        }
        if (password == null || password.isEmpty()) {
//            System.out.println("Please enter password!");
            alert.setContentText("Please enter a password! ");
            alert.showAndWait();
            return;
        }
        boolean loggedIn=FileHandling.authenticate(userType,username,password);
        if(loggedIn){
            System.out.println("LOGGED IN");
            int id=UserHandling.getUserId(username,password,userType);
            SessionHandler.getInstance().startSession(id,
                    UserHandling.getName(id,userType),userType);
        }
        else{
            Alert loginAlert=new Alert(Alert.AlertType.ERROR);
            loginAlert.setTitle("Login Failed");
            loginAlert.setContentText("Invalid login credentials");
            loginAlert.showAndWait();
        }

    }

    @FXML
    private void onRegisterLinkClicked() {
        System.out.println("Register link clicked!");
        // TODO: Navigate to registration page
    }
}
