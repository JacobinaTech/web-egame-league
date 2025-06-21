package tech.w3ll.egameleague.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tech.w3ll.egameleague.HelloApplication;

import java.io.IOException;

public class ControllerTelaTorneio{
    @FXML
    private Button btnEntrar;

    @FXML
    private Button btnCriar;

    public void abrirPopupTorneio() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("telas/gerTorneio/popupCriarTorneio.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void entrarTorneio() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("telas/tabela/LayoutTabela.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
