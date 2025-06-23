package tech.w3ll.egameleague.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import tech.w3ll.egameleague.HelloApplication;
import tech.w3ll.egameleague.modelos.Torneio;
import tech.w3ll.egameleague.repositores.listas.TorneioRepoLista;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerTelaTorneio implements Initializable {
    public static TorneioRepoLista torneioRepoLista = new TorneioRepoLista();

    @FXML
    private Button btnEntrar;

    @FXML
    private Button btnCriar;

    @FXML
    private Button btnAtualizar;

    @FXML
    private TableView<Torneio> tblTorneios;

    @FXML
    private TableColumn<Torneio, String> clnNomes;

    private ObservableList<Torneio> listaTorneios;

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

    @FXML
    public void atualizarTabela(){
        //testar
        ObservableList<Torneio> listaAtualizada = FXCollections.observableArrayList(torneioRepoLista.getTorneios());
        tblTorneios.setItems(listaAtualizada);
        tblTorneios.refresh(); // força a tabela a redesenhar, se necessário
        System.out.println(torneioRepoLista.getTorneios().getLast().getNomeDoTorneio());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        torneioRepoLista.cadastrarTorneio(new Torneio("torneio1"));
        torneioRepoLista.cadastrarTorneio(new Torneio("torneio2"));

        listaTorneios = FXCollections.observableArrayList(torneioRepoLista.getTorneios());
        tblTorneios.setItems(listaTorneios);
        clnNomes.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());

        atualizarTabela(); // primeira carga
    }
}
