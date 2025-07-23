package com.example.ap.subcontrollers;

import com.example.ap.AdminControllerBorderPaneSingleton;
import com.example.ap.admincontrollers.EditVsAddSingleton;
import com.example.ap.classes.*;
import com.example.ap.classes.enums.LANGUAGES;
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

public class subAttractionsControl implements Initializable {

    private final String fxmlForm="/com/example/ap/AdminParts/AttractionControl.fxml";
    @FXML
    private TableView<Attraction> attractionsTable;

    @FXML private TableColumn<Attraction, Integer> idColumn;
    @FXML private TableColumn<Attraction, String> nameColumn;
    @FXML private TableColumn<Attraction, String> locationColumn;
    @FXML private TableColumn<Attraction, String> typeColumn;
    @FXML private TableColumn<Attraction,Integer> difficultyColumn;
    @FXML private TableColumn<Attraction,Boolean> altitudeColumn;
    @FXML private TableColumn<Attraction, LANGUAGES> restrictedMonsoonColumn;
    @FXML private TableColumn<Attraction, Void> actionsColumn;

    @FXML private Button addButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        altitudeColumn.setCellValueFactory(new PropertyValueFactory<>("altitude"));
        restrictedMonsoonColumn.setCellValueFactory(new PropertyValueFactory<>("restrictedMonsoon"));

        try {
            List<Attraction> attractions = FileHandling.AllAttraction();
            ObservableList<Attraction> attractionsList = FXCollections.observableArrayList();

            assert attractions != null;
            for (Attraction attraction : attractions) {
                if (attraction != null) {
                    attractionsList.add((Attraction) attraction);
                }
            }
            attractionsTable.setItems(attractionsList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setupActionButtons();

        centerColumn(idColumn);
        centerColumn(nameColumn);
        centerColumn(locationColumn);
        centerColumn(typeColumn);
        centerColumn(difficultyColumn);
        centerColumn(altitudeColumn);
        centerColumn(restrictedMonsoonColumn);
    }

    private void setupActionButtons() {
        actionsColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Attraction, Void> call(final TableColumn<Attraction, Void> param) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");
                    private final HBox pane = new HBox(10, editButton, deleteButton);

                    {
                        editButton.setOnAction(event -> {
                            Attraction attraction = getTableView().getItems().get(getIndex());
                            EditVsAddSingleton.setEdit();
                            EditVsAddSingleton.setId(attraction.getId());
                            try {
                                Node node=FXMLLoader.load(Objects.requireNonNull(
                                        getClass().getResource(fxmlForm)));

                                AdminControllerBorderPaneSingleton.getMainPane().setCenter(node);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        deleteButton.setOnAction(event -> {
                            Attraction attraction = getTableView().getItems().get(getIndex());
                            try {
                                FileHandling.removeAttraction(attraction.getId());
                                FileHandling.makeLogs("Attraction Deleted: "+attraction.getDetails());
                                DeletionHandler.onAttractionDelete(attraction.getId());
                                getTableView().getItems().remove(attraction);
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
    private <T> void centerColumn(TableColumn<Attraction, T> column) {
        column.setCellFactory(col -> {
            TableCell<Attraction, T> cell = new TableCell<>() {
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
    public void addAttraction() throws IOException {
        EditVsAddSingleton.setAdd();
        Node attractionAdd = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlForm)));
        AdminControllerBorderPaneSingleton.getMainPane().setCenter(attractionAdd);
    }
}
