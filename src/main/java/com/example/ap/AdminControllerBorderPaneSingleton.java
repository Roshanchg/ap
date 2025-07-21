package com.example.ap;

import javafx.scene.layout.BorderPane;

public class AdminControllerBorderPaneSingleton {
    private static BorderPane mainPane;

    public static void setMainPane(BorderPane pane) {
        mainPane = pane;
    }

    public static BorderPane getMainPane() {
        return mainPane;
    }
}
