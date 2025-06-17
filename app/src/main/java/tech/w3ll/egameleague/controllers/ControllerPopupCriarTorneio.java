package tech.w3ll.egameleague.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tech.w3ll.egameleague.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPopupCriarTorneio implements Initializable {
    @FXML
    private TextField txtNomeTorneio;

    @FXML
    private TextField txtNomeJogo;

    @FXML
    private TableView tblEquipes;

    @FXML
    private Button btnAvancar;

    @FXML
    private Button btnAddLinha;

    @FXML
    private Button btnRmvLinha;

    public void nomearTorneio(){
    }

    public void definirJogo(){

    }

    public void abrirPopupEquipes() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("telas/gerTorneio/popupEquipes.fxml"));

        ControllerPopupEquipes controllerPopupEquipes = loader.getController();
        controllerPopupEquipes.setNomeEquipe(txtNomeTorneio.getText());

        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void adicionarLinha(){

    }

    public void removerLinha(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
