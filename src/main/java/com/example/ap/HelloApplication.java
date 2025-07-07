package com.example.ap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader registerScreen = new FXMLLoader(getClass().getResource("registerPage.fxml"));
        Parent root = registerScreen.load();
        Scene registerScene = new Scene(root, 1200, 800);
        stage.setTitle("Mystical Travels - Registration");
        stage.setScene(registerScene);
        stage.show();
    }




    public static void main(String[] args) {
        launch();
    }
}