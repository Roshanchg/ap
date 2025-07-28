package com.example.ap;

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
        ResourceBundle bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
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

            Label user = new Label(bundle.getString("guideBookingCardUserName") + touristName);
            Label attraction = new Label(bundle.getString("guideBookingCardAttractionName") + attractionName);
            Label date = new Label(bundle.getString("guideBookingCardDate")+ booking.getBookingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            Label description = new Label(bundle.getString("guideBookingCardDesc"));
            description.setWrapText(true);

            Button cancelBtn = new Button(bundle.getString("guideBookingCardButton"));
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

    public void onReportEmergency(ActionEvent actionEvent) {
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
                                , USERTYPE.Guide)).getName()
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

}
