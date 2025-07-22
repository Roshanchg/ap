package com.example.ap.subcontrollers;

import com.example.ap.AdminControllerBorderPaneSingleton;
import com.example.ap.admincontrollers.EditVsAddSingleton;
import com.example.ap.classes.*;
import com.example.ap.classes.enums.ALERTRISK;
import com.example.ap.handlers.FileHandling;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class subAlertControl implements Initializable {
    @FXML
    private TableView<Alerts> alertTable;

    @FXML private TableColumn<Alerts, Integer> idColumn;
    @FXML private TableColumn<Alerts, String> messageColumn;
    @FXML private TableColumn<Alerts, Integer> monthsActiveColumn;
    @FXML private TableColumn<Alerts, ALERTRISK> riskTypeColumn;
    @FXML private TableColumn<Alerts, Void> actionsColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        riskTypeColumn.setCellValueFactory(new PropertyValueFactory<>("riskType"));
        monthsActiveColumn.setCellValueFactory(new PropertyValueFactory<>("monthsActive"));

        try {
            List<Alerts> alerts = FileHandling.AllAlerts();
            ObservableList<Alerts> alertsList = FXCollections.observableArrayList();

            for (Alerts alert : alerts) {
                if (alert!=null) {
                    alertsList.add((Alerts) alert);
                }
            }
            alertTable.setItems(alertsList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setupActionButtons();

        centerColumn(idColumn);
        centerColumn(messageColumn);
        centerColumn(riskTypeColumn);
        centerColumn(monthsActiveColumn);
    }

    private void setupActionButtons() {
        actionsColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Alerts, Void> call(final TableColumn<Alerts, Void> param) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");
                    private final HBox pane = new HBox(10, editButton, deleteButton);

                    {
                        editButton.setOnAction(event -> {
                            Alerts alert = getTableView().getItems().get(getIndex());
                            EditVsAddSingleton.setEdit();
                            EditVsAddSingleton.setId(alert.getId());
                            Node alertAdd = null;
                            try {
                                alertAdd = FXMLLoader.load(Objects.requireNonNull(
                                        getClass().getResource("/com/example/ap/AdminParts/AlertsControl.fxml")));
                                AdminControllerBorderPaneSingleton.getMainPane().setCenter(alertAdd);

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        deleteButton.setOnAction(event -> {
                            Alerts alert = getTableView().getItems().get(getIndex());
                            getTableView().getItems().remove(alert);
                            try {
                                FileHandling.removeAlerts(alert.getId());
                                FileHandling.makeLogs("Alert Removed: "+alert.getDetails());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
            }
        });
    }
    private <T> void centerColumn(TableColumn<Alerts, T> column) {
        column.setCellFactory(col -> {
            TableCell<Alerts, T> cell = new TableCell<>() {
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
    public void addAlert() throws IOException {
        EditVsAddSingleton.setAdd();
        EditVsAddSingleton.resetVariables();
        Node alertAdd = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ap/AdminParts/AlertsControl.fxml")));
        AdminControllerBorderPaneSingleton.getMainPane().setCenter(alertAdd);
    }
}
