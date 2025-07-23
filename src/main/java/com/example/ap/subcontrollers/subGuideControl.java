package com.example.ap.subcontrollers;

import com.example.ap.AdminControllerBorderPaneSingleton;
import com.example.ap.admincontrollers.EditVsAddSingleton;
import com.example.ap.classes.Booking;
import com.example.ap.classes.Guide;
import com.example.ap.classes.Tourist;
import com.example.ap.classes.User;
import com.example.ap.classes.enums.LANGUAGES;
import com.example.ap.classes.enums.USERTYPE;
import com.example.ap.handlers.DeletionHandler;
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

public class subGuideControl implements  Initializable{

        @FXML
        private TableView<Guide> guideTable;

        @FXML private TableColumn<Guide, Integer> idColumn;
        @FXML private TableColumn<Guide, String> nameColumn;
        @FXML private TableColumn<Guide, String> emailColumn;
        @FXML private TableColumn<Guide, String> phoneNumberColumn;
        @FXML private TableColumn<Guide,Integer> yearsOfExperience;
        @FXML private TableColumn<Guide,Boolean> availability;
        @FXML private TableColumn<Guide, LANGUAGES> languagePrefColumn;
        @FXML private TableColumn<Guide, Void> actionsColumn;

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

            centerColumn(idColumn);
            centerColumn(nameColumn);
            centerColumn(languagePrefColumn);
            centerColumn(emailColumn);
            centerColumn(phoneNumberColumn);
            centerColumn(yearsOfExperience);
            centerColumn(availability);

        }

        private void setupActionButtons() {
            actionsColumn.setCellFactory(new Callback<>() {
                @Override
                public TableCell<Guide, Void> call(final TableColumn<Guide, Void> param) {
                    return new TableCell<>() {
                        private final Button editButton = new Button("Edit");
                        private final Button deleteButton = new Button("Delete");
                        private final HBox pane = new HBox(10, editButton, deleteButton);

                        {
                            editButton.setOnAction(event -> {
                                Guide guide = getTableView().getItems().get(getIndex());
                                EditVsAddSingleton.setEdit();
                                EditVsAddSingleton.setId(guide.getId());
                                Node guideEdit = null;
                                try {
                                    guideEdit = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ap/AdminParts/GuideControl.fxml")));
                                    AdminControllerBorderPaneSingleton.getMainPane().setCenter(guideEdit);

                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                            deleteButton.setOnAction(event -> {
                                Guide guide = getTableView().getItems().get(getIndex());
                                getTableView().getItems().remove(guide);
                                try {
                                    FileHandling.removeUser(USERTYPE.Guide,guide.getId());
                                    FileHandling.makeLogs("Guide Removed: "+guide.getDetails());
                                    getTableView().getItems().remove(guide);
                                    DeletionHandler.onUserDelete(guide.getId(),USERTYPE.Guide);

                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                EditVsAddSingleton.resetVariables();
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
    private <T> void centerColumn(TableColumn<Guide, T> column) {
        column.setCellFactory(col -> {
            TableCell<Guide, T> cell = new TableCell<>() {
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
    public void addGuide() throws IOException {

        EditVsAddSingleton.setAdd();
        EditVsAddSingleton.resetVariables();
        Node guideAdd = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ap/AdminParts/GuideControl.fxml")));
        AdminControllerBorderPaneSingleton.getMainPane().setCenter(guideAdd);
    }
}
