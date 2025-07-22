package com.example.ap.subcontrollers;

import com.example.ap.AdminControllerBorderPaneSingleton;
import com.example.ap.admincontrollers.EditVsAddSingleton;
import com.example.ap.classes.Tourist;
import com.example.ap.classes.User;
import com.example.ap.classes.enums.LANGUAGES;
import com.example.ap.classes.enums.USERTYPE;
import com.example.ap.handlers.FileHandling;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class subTouristControl implements Initializable {

    @FXML private TableView<Tourist> touristTable;

    @FXML private TableColumn<Tourist, Integer> idColumn;
    @FXML private TableColumn<Tourist, String> nameColumn;
    @FXML private TableColumn<Tourist, LANGUAGES> languagePrefColumn;
    @FXML private TableColumn<Tourist, String> emailColumn;
    @FXML private TableColumn<Tourist, String> phoneNumberColumn;
    @FXML private TableColumn<Tourist, String> nationalityColumn;
    @FXML private TableColumn<Tourist, String> emergencyNumberColumn;
    @FXML private TableColumn<Tourist, Void> actionsColumn;  // Typically buttons go here

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        languagePrefColumn.setCellValueFactory(new PropertyValueFactory<>("languagePref"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        nationalityColumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        emergencyNumberColumn.setCellValueFactory(new PropertyValueFactory<>("emergencyContact"));

        try {
            List<User> users = FileHandling.AllUsers(USERTYPE.Tourist);
            ObservableList<Tourist> tourists = FXCollections.observableArrayList();

            for (User user : users) {
                if (user instanceof Tourist) {
                    tourists.add((Tourist) user);
                }
            }
            touristTable.setItems(tourists);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setupActionButtons();

        centerColumn(idColumn);
        centerColumn(nameColumn);
        centerColumn(languagePrefColumn);
        centerColumn(emailColumn);
        centerColumn(phoneNumberColumn);
        centerColumn(nationalityColumn);
        centerColumn(emergencyNumberColumn);
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
                            EditVsAddSingleton.setEdit();
                            EditVsAddSingleton.setId(tourist.getId());
                            Node touristEdit = null;
                            try {
                                touristEdit = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ap/AdminParts/TouristControl.fxml")));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            AdminControllerBorderPaneSingleton.getMainPane().setCenter(touristEdit);
                        });

                        deleteButton.setOnAction(event -> {
                            Tourist tourist = getTableView().getItems().get(getIndex());
                            System.out.println("Delete clicked for: " + tourist.getName());
                            try {
                                FileHandling.removeUser(USERTYPE.Tourist,tourist.getId());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
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

    private <T> void centerColumn(TableColumn<Tourist, T> column) {
        column.setCellFactory(col -> {
            TableCell<Tourist, T> cell = new TableCell<>() {
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
    public void addTourist() throws IOException {
        EditVsAddSingleton.setAdd();
        EditVsAddSingleton.resetVariables();
        Node touristAdd = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ap/AdminParts/TouristControl.fxml")));
        AdminControllerBorderPaneSingleton.getMainPane().setCenter(touristAdd);
    }
}
