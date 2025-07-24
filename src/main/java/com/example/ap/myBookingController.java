package com.example.ap;

import com.example.ap.classes.Booking;
import com.example.ap.classes.User;
import com.example.ap.classes.enums.NAVIGATIONS;
import com.example.ap.classes.enums.USERTYPE;
import com.example.ap.handlers.CacheHandler;
import com.example.ap.handlers.FileHandling;
import com.example.ap.handlers.Navigator;
import com.example.ap.handlers.ObjectFinder;
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
import java.util.ResourceBundle;
import java.util.function.Function;

public class myBookingController implements Initializable {
    private List<Booking> bookings;

    private Function<Integer, String> guideNameResolver;

    @FXML
    private FlowPane bookingContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Booking> bookings = null;
        try {
            bookings = CacheHandler.getBookingsCache();
            this.bookings = bookings;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        guideNameResolver = guideId -> {
            if (guideId == 0) {
                return "Not Assigned";
            }
            User guide = null;
            try {
                guide = ObjectFinder.getUser(guideId, USERTYPE.Guide);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return guide != null ? guide.getName() : "Not Assigned";
        };
        initializeBookings(this.bookings);
    }

    public void setBookings(List<Booking> bookings, Function<Integer, String> guideNameResolver) {
        this.bookings = bookings;
        this.guideNameResolver = guideNameResolver;

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
            try {
                var attraction = ObjectFinder.getAttraction(booking.getAid());
                attractionName = (attraction != null) ? attraction.getName() : "Removed Attraction";
            } catch (IOException e) {
                attractionName = "Removed Attraction";
            }
            Label attraction = new Label("Attraction: " + attractionName);

            Label guide = new Label();
            guide.setUserData("guideLabel_" + booking.getBookingId());
            guide.setText("Guide: " + guideNameResolver.apply(booking.getGuideId()));

            Label date = new Label("Date: " + booking.getBookingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            Label discount = new Label("Discount: " + booking.getDiscount() + "%");
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

            card.getChildren().addAll(attraction, guide, date, discount, description, cancelBtn);
            bookingContainer.getChildren().add(card);
        }
    }


    private void handleCancel(Booking booking) throws IOException{
        System.out.println("Cancel booking with ID: " + booking.getBookingId());
        booking.cancel();
        FileHandling.editBooking(booking.getBookingId(),booking);
        CacheHandler.ClearCache();
        Navigator.Navigate(NAVIGATIONS.booking,(Stage) bookingContainer.getScene().getWindow());
    }

    public void initializeBookings(List<Booking> bookings) {
        setBookings(bookings, guideNameResolver);
    }

    @FXML
    private void goHome() throws IOException {
        Navigator.Navigate(NAVIGATIONS.TOURIST,(Stage) bookingContainer.getScene().getWindow());
    }

    @FXML
    private void goAttractions() throws IOException {
        Navigator.Navigate(NAVIGATIONS.attraction,(Stage) bookingContainer.getScene().getWindow());
    }

    @FXML
    private void goFestivals() throws IOException{
        Navigator.Navigate(NAVIGATIONS.festive,(Stage) bookingContainer.getScene().getWindow());

    }

    @FXML
    public void goUserEdit() throws IOException{
        Navigator.Navigate(NAVIGATIONS.profileEditTourist,(Stage) bookingContainer.getScene().getWindow());
    }
}
