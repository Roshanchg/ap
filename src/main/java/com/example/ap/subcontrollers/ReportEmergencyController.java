package com.example.ap.subcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ReportEmergencyController {

    @FXML
    private TextField messageField;

    private String result;

    @FXML
    public void onCancel() {
        result = null;
        close();
    }

    @FXML
    public void onConfirm() {
        result = messageField.getText().trim();
        close();
    }

    public String getResult() {
        return result;
    }

    private void close() {
        Stage stage = (Stage) messageField.getScene().getWindow();
        stage.close();
    }
}
