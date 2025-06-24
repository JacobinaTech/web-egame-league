package tech.w3ll.egameleague.controllers;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.cell.PropertyValueFactory;
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

    //testando de forma static
    private static ObservableList<Torneio> listaTorneios;

    //testando modelo do professor
    //private static ObservableList<Torneio> listaTorneios = FXCollections.observableArrayList();

    public static ObservableList<Torneio> getListaTorneios() {
        return listaTorneios;
    }

    public void setListaTorneios(ObservableList<Torneio> listaTorneios) {
        this.listaTorneios = listaTorneios;
    }
//
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
        // teste
        System.out.println("Itens no repositório:");
        for (Torneio t : torneioRepoLista.getTorneios()) {
            System.out.println("- " + t.getNomeDoTorneio());
        }
        //

        listaTorneios.setAll(torneioRepoLista.getTorneios());
        //testar
        //listaTorneios.clear();
        //listaTorneios.addAll(torneioRepoLista.getTorneios());

        // teste
        //clnNomes.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());

        tblTorneios.refresh(); // força a tabela a redesenhar, se necessário
        System.out.println(listaTorneios.getLast().getNomeDoTorneio());
        System.out.println(torneioRepoLista.getTorneios().getLast().getNomeDoTorneio());

        System.out.println("Tamanho da listaTorneios: " + listaTorneios.size());
        System.out.println("Tamanho do repositório: " + torneioRepoLista.getTorneios().size());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        torneioRepoLista.cadastrarTorneio(new Torneio("torneio1"));
        torneioRepoLista.cadastrarTorneio(new Torneio("torneio2"));

        listaTorneios = FXCollections.observableArrayList();
        tblTorneios.setItems(listaTorneios);
        //clnNomes.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
       // clnNomes.setCellValueFactory(new PropertyValueFactory<>("nomeDoTorneio"));
        clnNomes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeDoTorneio()));

        //teste
        //listaTorneios.add(new Torneio("Torneio teste manual"));

        atualizarTabela(); // primeira carga


    }
}
