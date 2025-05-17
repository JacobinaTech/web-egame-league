module tech.w3ll.egameleague.app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    exports tech.w3ll.egameleague;
    opens tech.w3ll.egameleague to javafx.fxml;
}