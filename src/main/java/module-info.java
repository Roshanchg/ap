module com.example.ap {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql.rowset;
    requires java.desktop;

    opens com.example.ap to javafx.fxml;
    exports com.example.ap;
    opens com.example.ap.subcontrollers to javafx.fxml;
    opens com.example.ap.classes to javafx.base;
    exports com.example.ap.admincontrollers;
    opens com.example.ap.admincontrollers to javafx.fxml;
    exports com.example.ap.classes;

}