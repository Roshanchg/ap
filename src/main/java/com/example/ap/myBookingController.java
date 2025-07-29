package com.example.ap;

import com.example.ap.classes.Alerts;
import com.example.ap.classes.Booking;
import com.example.ap.classes.User;
import com.example.ap.classes.enums.NAVIGATIONS;
import com.example.ap.classes.enums.USERTYPE;
import com.example.ap.handlers.*;
import com.example.ap.subcontrollers.ReportEmergencyController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
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
        ResourceBundle bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
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
            Label attraction = new Label(bundle.getString("myBookingAttractionName")+ attractionName);

            Label guide = new Label();
            guide.setUserData("guideLabel_" + booking.getBookingId());
            guide.setText(bundle.getString("myBookingGuideName") + guideNameResolver.apply(booking.getGuideId()));

            Label date = new Label(bundle.getString("myBookingDate")+ booking.getBookingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            Label price=new Label(bundle.getString("priceBookingCard")+": "+booking.getPrice());
            Label discount = new Label(bundle.getString("myBookingDiscount")+ booking.getDiscount() + "%");
            Label description = new Label(bundle.getString("myBookingDesc"));
            description.setWrapText(true);
            Label total=new Label(bundle.getString("totalBookingCard")+": "+booking.getTotal());
            Button cancelBtn = new Button(bundle.getString("myBookingCancel"));
            cancelBtn.setStyle("-fx-background-color: #e63946; -fx-text-fill: white; -fx-background-radius: 20;");
            cancelBtn.setOnAction(e -> {
                try {
                    handleCancel(booking);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            card.getChildren().addAll(attraction, guide, date, price,discount, description,total, cancelBtn);
            bookingContainer.getChildren().add(card);
        }
    }


    private void handleCancel(Booking booking) throws IOException{
        booking.cancel();
        FileHandling.editBooking(booking.getBookingId(),booking);
        CacheHandler.ClearCache();
        showAlert("Booking Cancelled!!");
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

    @FXML
    public void reportEmergency() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ap/report_emergency.fxml"));
            Parent root = loader.load();

            Stage dialog = new Stage();
            dialog.setTitle("Report Emergency");
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL); // block main window
            dialog.showAndWait();

            ReportEmergencyController controller = loader.getController();
            String message = controller.getResult();

            if (message != null && !message.isEmpty()) {
                FileHandling.makeLogs("Emergency: "+ Objects.requireNonNull(
                        ObjectFinder.getUser(
                        SessionHandler.getInstance().getUserId()
                                , USERTYPE.Tourist)).getName()
                +". "+message);
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Emergency Log Success");
                alert.setContentText("Emergency Message sent!");
                alert.showAndWait();
            } else {
                dialog.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String message){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Booking Cancel");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
