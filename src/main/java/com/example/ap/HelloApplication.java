package com.example.ap;

import com.example.ap.handlers.CacheHandler;
import com.example.ap.handlers.FileHandling;
import com.example.ap.handlers.ImportantVariables;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader registerScreen = new FXMLLoader(getClass().getResource("adminDashboard.fxml"));
        Parent root = registerScreen.load();
        Scene registerScene = new Scene(root, 1300, 720);
        stage.setTitle("Mystical Travels - Registration");
        stage.setResizable(false);
        stage.setScene(registerScene);
        stage.setOnCloseRequest(
                windowEvent -> {
                    System.out.println("EXITING...");
                    CacheHandler.ClearCache();
                }
        );
        stage.show();
    }




    public static void main(String[] args) throws IOException {
        FileHandling.init();
        launch();
    }
}