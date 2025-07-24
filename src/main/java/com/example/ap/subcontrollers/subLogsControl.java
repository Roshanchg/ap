package com.example.ap.subcontrollers;

import com.example.ap.classes.*;
import com.example.ap.handlers.FileHandling;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class subLogsControl implements Initializable {
    @FXML
    private TableView<EmergencyLog> logsTable;

    @FXML private TableColumn<EmergencyLog, String> timeColumn;
    @FXML private TableColumn<EmergencyLog, String> messageColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        try {
            List<EmergencyLog> logs = FileHandling.AllLogs();
            ObservableList<EmergencyLog> logsList = FXCollections.observableArrayList();

            for (EmergencyLog log : logs) {
                if (log!=null) {
                    logsList.add((EmergencyLog) log);
                }
            }
            logsTable.setItems(logsList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        centerColumn(timeColumn);
        centerColumn(messageColumn);
    }

    private <T> void centerColumn(TableColumn<EmergencyLog, T> column) {
        column.setCellFactory(col -> {
            TableCell<EmergencyLog, T> cell = new TableCell<>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.toString());
                    setAlignment(Pos.CENTER);
                }
            };
            return cell;
        });
    }
    @FXML
    public void onExportLogs() throws IOException {
        FileHandling.getLogs();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Log Exported at /Exports/Log.txt");
        alert.showAndWait();
    }
}
