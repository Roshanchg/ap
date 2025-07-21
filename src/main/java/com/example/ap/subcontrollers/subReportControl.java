package com.example.ap.subcontrollers;

import com.example.ap.classes.*;
import com.example.ap.classes.enums.LANGUAGES;
import com.example.ap.classes.enums.USERTYPE;
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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class subReportControl implements Initializable {
    @FXML
    private TableView<Guide> guideTable;

    @FXML private TableColumn<Guide, Integer> idColumn;
    @FXML private TableColumn<Guide, String> nameColumn;
    @FXML private TableColumn<Tourist, String> emailColumn;
    @FXML private TableColumn<Tourist, String> phoneNumberColumn;
    @FXML private TableColumn<Guide,Integer> yearsOfExperience;
    @FXML private TableColumn<Guide,Boolean> availability;
    @FXML private TableColumn<Tourist, LANGUAGES> languagePrefColumn;
    @FXML private TableColumn<Tourist, Void> actionsColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        languagePrefColumn.setCellValueFactory(new PropertyValueFactory<>("languageSpoken"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        yearsOfExperience.setCellValueFactory(new PropertyValueFactory<>("yearsOfExperience"));
        availability.setCellValueFactory(new PropertyValueFactory<>("availability"));

        try {
            List<User> users = FileHandling.AllUsers(USERTYPE.Guide);
            ObservableList<Guide> guides = FXCollections.observableArrayList();

            assert users != null;
            for (User user : users) {
                if (user instanceof Guide) {
                    guides.add((Guide) user);
                }
            }
            guideTable.setItems(guides);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setupActionButtons();
    }

    private void setupActionButtons() {
        actionsColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Tourist, Void> call(final TableColumn<Tourist, Void> param) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");
                    private final HBox pane = new HBox(10, editButton, deleteButton);

                    {
                        editButton.setOnAction(event -> {
                            Tourist tourist = getTableView().getItems().get(getIndex());
                            System.out.println("Edit clicked for: " + tourist.getName());
                        });

                        deleteButton.setOnAction(event -> {
                            Tourist tourist = getTableView().getItems().get(getIndex());
                            System.out.println("Delete clicked for: " + tourist.getName());
                            // Example: remove from table
                            getTableView().getItems().remove(tourist);
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
}
