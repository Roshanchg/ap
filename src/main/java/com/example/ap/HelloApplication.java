package com.example.ap;

import com.example.ap.handlers.ImportantVariables;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader registerScreen = new FXMLLoader(getClass().getResource("touristDashboard.fxml"));
        Parent root = registerScreen.load();
        Scene registerScene = new Scene(root, 1300, 720);
        stage.setTitle("Mystical Travels - Registration");
        stage.setResizable(false);
//        stage.setWidth(ImportantVariables.screenBounds.getWidth());
//        stage.setHeight(ImportantVariables.screenBounds.getHeight());
        stage.setScene(registerScene);
        stage.show();
    }




    public static void main(String[] args) {
        launch();
    }
}