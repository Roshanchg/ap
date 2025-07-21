package com.example.ap.subcontrollers;

import com.example.ap.classes.Booking;
import com.example.ap.classes.Guide;
import com.example.ap.classes.Tourist;
import com.example.ap.classes.User;
import com.example.ap.classes.enums.LANGUAGES;
import com.example.ap.classes.enums.USERTYPE;
import com.example.ap.handlers.FileHandling;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class subBookingsControl implements Initializable {
    @FXML
    private TableView<Booking> bookingsTable;

    @FXML private TableColumn<Booking, Integer> idColumn;
    @FXML private TableColumn<Booking, Integer> uidColumn;
    @FXML private TableColumn<Booking, Integer> gidColumn;
    @FXML private TableColumn<Booking, Integer> aidColumn;
    @FXML private TableColumn<Booking,Integer> fidColumn;
    @FXML private TableColumn<Booking, Date> dateColumn;
    @FXML private TableColumn<Booking, Boolean> cancelledColumn;
    @FXML private TableColumn<Booking, Double> discountColumn;
    @FXML private TableColumn<Booking, Void> actionsColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        fidColumn.setCellValueFactory(new PropertyValueFactory<>("fid"));
        gidColumn.setCellValueFactory(new PropertyValueFactory<>("guideId"));
        uidColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        aidColumn.setCellValueFactory(new PropertyValueFactory<>("aid"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        cancelledColumn.setCellValueFactory(new PropertyValueFactory<>("isCancelled"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));

        try {
            List<Booking> bookings = FileHandling.AllBookings();
            ObservableList<Booking> bookingList = FXCollections.observableArrayList();

            for (Booking booking : bookings) {
                if (booking!=null) {
                    bookingList.add((Booking) booking);
                }
            }
            bookingsTable.setItems(bookingList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setupActionButtons();

        centerColumn(idColumn);
        centerColumn(uidColumn);
        centerColumn(gidColumn);
        centerColumn(aidColumn);
        centerColumn(fidColumn);
        centerColumn(dateColumn);
        centerColumn(discountColumn);
        centerColumn(cancelledColumn);
    }

    private void setupActionButtons() {
        actionsColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Booking, Void> call(final TableColumn<Booking, Void> param) {
                return new TableCell<>() {
                    private final Button guideAssign = new Button("Assign Guide");
                    private final Button deleteButton = new Button("Delete");
                    private final HBox pane = new HBox(10, guideAssign, deleteButton);

                    {
                        guideAssign.setOnAction(event -> {
                            Booking booking = getTableView().getItems().get(getIndex());
                            System.out.println("Edit clicked for: " + booking.getBookingId());
                        });

                        deleteButton.setOnAction(event -> {
                            Booking booking = getTableView().getItems().get(getIndex());
                            System.out.println("Delete clicked for: " + booking.getBookingId());
                            // Example: remove from table
                            getTableView().getItems().remove(booking);
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

    private <T> void centerColumn(TableColumn<Booking, T> column) {
        column.setCellFactory(col -> {
            TableCell<Booking, T> cell = new TableCell<>() {
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
