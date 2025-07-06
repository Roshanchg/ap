package com.example.ap;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Label subtitleLabel;

    @FXML
    private ImageView logoImage;

    @FXML
    private Rectangle overlay;

    @FXML
    private StackPane root; // Make sure fx:id="root" is added in <StackPane>

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoImage.setImage(new Image(getClass().getResourceAsStream("/images/logo.png")));

        subtitleLabel.setText("Discover hidden gems and timeless culture.");

        overlay.widthProperty().bind(root.widthProperty());
        overlay.heightProperty().bind(root.heightProperty());
    }
}
