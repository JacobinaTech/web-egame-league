package tech.w3ll.egameleague.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import tech.w3ll.egameleague.modelos.Torneio;
import tech.w3ll.egameleague.repositores.listas.TorneioRepoLista;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPopupEquipes implements  Initializable {

    // instancia do obejeto torneio usado no popup atual e anterior
    public static Torneio torneio = new Torneio();

    @FXML
    private AnchorPane anchorPopupEquipes ;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField txtCabecalho;

    @FXML
    private Label lblNomeEquipe;

    @FXML
    private Button btnAddLinha;

    @FXML
    private Button btnRmvLinha;

    @FXML
    private TableView tblEquipes;

    private String nomeEquipe;

    public Label getLblNomeEquipe() {
        return lblNomeEquipe;
    }

    public void setLblNomeEquipe(String lblNomeEquipe) {
        this.lblNomeEquipe.setText(lblNomeEquipe);
    }


    public String getNomeEquipe() {
        return nomeEquipe;
    }

    public void setNomeEquipe(String nomeEquipe) {
        this.nomeEquipe = nomeEquipe;
    }

    public void adicionarLinha(){

    }

    public void removerLinha(){

    }

    ControllerPopupCriarTorneio controllerPopupCriarTorneio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lblNomeEquipe.setText(torneio.getNomeDoTorneio());
    }
}
