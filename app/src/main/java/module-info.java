module tech.w3ll.egameleague.app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens tech.w3ll.egameleague.app to javafx.fxml;
    exports tech.w3ll.egameleague.app;
}