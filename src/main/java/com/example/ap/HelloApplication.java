package com.example.ap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("startupPage.fxml"));
//        Parent root = loader.load();
//        Scene scene = new Scene(root, 1200, 800);
//        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
//        stage.setTitle("Mystical Travels - Explore our Beautiful Nepal");
//        stage.setScene(scene);
//        stage.show();
//    }
//@Override
//public void start(Stage stage) throws IOException {
//    FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
//    Parent root = loader.load();
//    Scene scene = new Scene(root, 1200, 800);
//    scene.getStylesheets().add(getClass().getResource("/css/auth.css").toExternalForm());
//
//    stage.setTitle("Login - Mystical Travels");
//    stage.setScene(scene);
//    stage.show();
//}

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("registerPage.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/css/auth.css").toExternalForm());

        stage.setTitle("Mystical Travels - Registration");
        stage.setScene(scene);
        stage.show();
    }




    public static void main(String[] args) {
        launch();
    }
}