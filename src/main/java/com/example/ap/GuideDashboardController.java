package com.example.ap;

import com.example.ap.classes.Booking;
import com.example.ap.classes.User;
import com.example.ap.classes.enums.NAVIGATIONS;
import com.example.ap.classes.enums.USERTYPE;
import com.example.ap.handlers.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class GuideDashboardController implements Initializable {

    @FXML
    private FlowPane bookingContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Booking> allBookings = CacheHandler.getAssignmentsCache();
            int guideId = SessionHandler.getInstance().getUserId();
            if(allBookings!=null) {
                List<Booking> assignedBookings = allBookings.stream()
                        .filter(b -> b.getGuideId() == guideId)
                        .toList();
                loadBookings(assignedBookings);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadBookings(List<Booking> bookings) {
        bookingContainer.getChildren().clear();
        if(bookings==null){
            return;
        }
        for (Booking booking : bookings) {
            VBox card = new VBox(8);
            card.setPadding(new Insets(15));
            card.setPrefWidth(220);
            card.setStyle("""
                    -fx-background-color: white;
                    -fx-background-radius: 12;
                    -fx-border-color: #dddddd;
                    -fx-border-radius: 12;
                    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 4);
                    """);

            String attractionName;
            String touristName;
            try {
                attractionName = Objects.requireNonNull(ObjectFinder.getAttraction(booking.getAid())).getName();
            } catch (Exception e) {
                attractionName = "Unknown Attraction";
            }

            try {
                User tourist = ObjectFinder.getUser(booking.getUserId(), USERTYPE.Tourist);
                touristName = (tourist != null) ? tourist.getName() : "Unknown Tourist";
            } catch (Exception e) {
                touristName = "Unknown Tourist";
                continue;
            }

            Label user = new Label("User: " + touristName);
            Label attraction = new Label("Attraction: " + attractionName);
            Label date = new Label("Date: " + booking.getBookingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            Label description = new Label("Standard tourist package.\nIncludes basic amenities.");
            description.setWrapText(true);

            Button cancelBtn = new Button("Cancel");
            cancelBtn.setStyle("-fx-background-color: #e63946; -fx-text-fill: white; -fx-background-radius: 20;");
            cancelBtn.setOnAction(e -> {
                try {
                    handleCancel(booking);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            card.getChildren().addAll(user, attraction, date, description, cancelBtn);
            bookingContainer.getChildren().add(card);
        }
    }

    private void handleCancel(Booking booking) throws IOException {
        DeletionHandler.onUserDelete(booking.getGuideId(), USERTYPE.Guide);
        CacheHandler.ClearCache();
        Navigator.Navigate(NAVIGATIONS.GUIDE, (Stage) bookingContainer.getScene().getWindow());
    }

    @FXML
    public void goUserEdit() throws IOException {
        Navigator.Navigate(NAVIGATIONS.profileEditGuide, (Stage) bookingContainer.getScene().getWindow());
    }
}
