package com.example.ap.subcontrollers;

import com.example.ap.AdminControllerBorderPaneSingleton;
import com.example.ap.classes.*;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class subFestivalControl implements Initializable {
    @FXML
    private TableView<Festival> festivalTable;

    @FXML private TableColumn<Festival, Integer> idColumn;
    @FXML private TableColumn<Festival, String> nameColumn;
    @FXML private TableColumn<Festival, Date> startDateColumn;
    @FXML private TableColumn<Festival, Date> endDateColumn;
    @FXML private TableColumn<Festival,Double> discountRateColumn;
    @FXML private TableColumn<Festival, Void> actionsColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        discountRateColumn.setCellValueFactory(new PropertyValueFactory<>("discountRate"));

        try {
            List<Festival> festivals = FileHandling.AllFestival();
            ObservableList<Festival> festivalList = FXCollections.observableArrayList();

            for (Festival festival : festivals) {
                if (festival != null) {
                    festivalList.add((Festival) festival);
                }
            }
            festivalTable.setItems(festivalList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setupActionButtons();

        centerColumn(idColumn);
        centerColumn(nameColumn);
        centerColumn(startDateColumn);
        centerColumn(endDateColumn);
        centerColumn(discountRateColumn);

    }

    private void setupActionButtons() {
        actionsColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Festival, Void> call(final TableColumn<Festival, Void> param) {
                return new TableCell<>() {
                    private final Button deleteButton = new Button("Delete");
                    private final HBox pane = new HBox(10, deleteButton);

                    {


                        deleteButton.setOnAction(event -> {
                            Festival festival = getTableView().getItems().get(getIndex());
                            System.out.println("Delete clicked for: " + festival.getName());
                            // Example: remove from table
                            getTableView().getItems().remove(festival);
                            try {
                                FileHandling.removeFestival(festival.getId());
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
    private <T> void centerColumn(TableColumn<Festival, T> column) {
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    if (item instanceof Date) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        setText(formatter.format((Date) item));
                    } else {
                        setText(item.toString());
                    }
                }
                setAlignment(Pos.CENTER);
            }
        });
    }
    @FXML
    public void addFestival() throws IOException {
        Node festivalAdd = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ap/AdminParts/FestivalsControl.fxml")));
        AdminControllerBorderPaneSingleton.getMainPane().setCenter(festivalAdd);
    }
}
