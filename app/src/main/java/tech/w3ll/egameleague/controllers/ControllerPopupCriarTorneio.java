package tech.w3ll.egameleague.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tech.w3ll.egameleague.HelloApplication;
import tech.w3ll.egameleague.modelos.Torneio;
import tech.w3ll.egameleague.repositores.listas.TorneioRepoLista;

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

    private String nomeTorneio;
    private String nomeJogo;

    public void nomearTorneio(){
        nomeTorneio = txtNomeTorneio.getText();
    }

    public String getNomeTorneio() {
        return nomeTorneio;
    }

    public void definirJogo(){
        nomeJogo = txtNomeJogo.getText();
    }

    public String getNomeJogo() {
        return nomeJogo;
    }

    public void abrirPopupEquipes() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("telas/gerTorneio/popupEquipes.fxml"));

        Torneio torneio = Torneio.getInstance();
        torneio.setNomeJogo(nomeJogo);
        torneio.setNomeDoTorneio(nomeTorneio);

        TorneioRepoLista repoLista = TorneioRepoLista.getInstance();
        repoLista.cadastrarTorneio(torneio);


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
