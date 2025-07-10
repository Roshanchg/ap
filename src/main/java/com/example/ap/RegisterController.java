package com.example.ap;

import com.example.ap.classes.Guide;
import com.example.ap.classes.Tourist;
import com.example.ap.classes.enums.LANGUAGES;
import com.example.ap.classes.enums.NAVIGATIONS;
import com.example.ap.classes.enums.USERTYPE;
import com.example.ap.handlers.FileHandling;
import com.example.ap.handlers.Navigator;
import com.example.ap.handlers.SessionHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;

    @FXML private ComboBox<LANGUAGES> LanguagesField;

    @FXML private Button registerBtn;

    @FXML private TextField guideExperienceField;

    @FXML private ImageView logoImage;

    private USERTYPE selectedRole;

    @FXML private Label loginButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png"))));
        LanguagesField.getItems().addAll(LANGUAGES.values());
    }

    @FXML
    private void onTouristSelected() {
        selectedRole =USERTYPE.Tourist;
        roleSelectionBox.setVisible(false);
        roleSelectionBox.setManaged(false);
        formContainer.setVisible(true);
        formContainer.setManaged(true);
        guideFieldsContainer.setVisible(false);
        guideFieldsContainer.setManaged(false);
        emergencyContactField.setVisible(true);
        emergencyContactField.setManaged(true);

        nationalityField.setVisible(true);
        nationalityField.setManaged(true);

    }

    @FXML
    private void onGuideSelected() {
        selectedRole = USERTYPE.Guide;
        roleSelectionBox.setVisible(false);
        roleSelectionBox.setManaged(false);
        formContainer.setVisible(true);
        formContainer.setManaged(true);
        guideFieldsContainer.setVisible(true);
        guideFieldsContainer.setManaged(true);
        emergencyContactField.setVisible(false);
        emergencyContactField.setManaged(false);

        nationalityField.setVisible(false);
        nationalityField.setManaged(false);
    }

    @FXML
    private void onRegisterButtonClicked() throws IOException {

        //form validation
        if (selectedRole==null) {
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

        if (selectedRole==USERTYPE.Guide) {
            if (LanguagesField==null || guideExperienceField.getText().isEmpty()) {
                showAlert("Please complete guide-specific fields.");
                return;
            }

        }

        if(!FileHandling.isEmail(emailField.getText())){
            showAlert("Invalid Email address!!");
            return;
        }

        if(!phoneField.getText().matches("\\d+") || !(phoneField.getText().length()==10)){
            showAlert("Insert a proper phone number");
            return;
        }

        switch(selectedRole){
            case USERTYPE.Tourist -> {

                String fullName=nameField.getText();
                String nationality=nationalityField.getText();
                String emergencyContact=emergencyContactField.getText();
                if(!emergencyContact.matches("\\d+") || !(emergencyContact.length()==10)){
                    showAlert("Invalid Emergency Number!!");
                    break;
                }
                String phoneNumber=phoneField.getText();
                String email=emailField.getText();
                String password=passwordField.getText();
                LANGUAGES languagePref= (LanguagesField.getValue()==null)?LANGUAGES.English:LanguagesField.getValue();

                Tourist tourist=new Tourist(FileHandling.getNextId(FileHandling.TouristFile),fullName,
                        email,phoneNumber,password, languagePref,nationality,emergencyContact);
                FileHandling.WriteUser(USERTYPE.Tourist,tourist);
                SessionHandler.getInstance().startSession(tourist.getId(),tourist.getName(),USERTYPE.Tourist);

//                Navigate to Tourist home page
                Stage stage=(Stage) registerBtn.getScene().getWindow();
                Navigator.Navigate(NAVIGATIONS.TOURIST,stage);
            }
            case USERTYPE.Guide -> {
                String fullName=nameField.getText();
                String phoneNumber=phoneField.getText();
                String email=emailField.getText();
                String password=passwordField.getText();
                LANGUAGES languagePref= (LanguagesField.getValue()==null)?LANGUAGES.English:LanguagesField.getValue();
                String yearsOfExperience=guideExperienceField.getText();
                Guide guide=new Guide(FileHandling.getNextId(FileHandling.GuideFile),fullName,
                        email,phoneNumber,password ,languagePref,Integer.parseInt(yearsOfExperience));
                FileHandling.WriteUser(USERTYPE.Guide,guide);
                SessionHandler.getInstance().startSession(guide.getId(),guide.getName(),USERTYPE.Guide);

//                  Navigate to guide Home page
                Stage stage=(Stage) registerBtn.getScene().getWindow();
                Navigator.Navigate(NAVIGATIONS.GUIDE,stage);

            }
            default -> System.out.println("BRUH MATE HOW DID YOU CHOOSE OTHER USERTYPE? bombaclatt");
        }

    }

    @FXML
    private void onLoginLinkClicked() throws IOException {
//        System.out.println("Redirect to Login page...");
        Stage stage=(Stage) loginButton.getScene().getWindow();
        Navigator.Navigate(NAVIGATIONS.LOGIN,stage);
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
