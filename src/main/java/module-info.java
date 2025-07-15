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
}