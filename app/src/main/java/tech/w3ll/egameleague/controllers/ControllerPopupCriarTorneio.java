package tech.w3ll.egameleague.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import tech.w3ll.egameleague.HelloApplication;
import tech.w3ll.egameleague.modelos.Equipe;
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
    private TableView<Equipe> tblEquipes;

    @FXML
    private TableColumn<Equipe, String> clnNomesEquipes;

    private ObservableList<Equipe> listaEquipes;

    @FXML
    private Button btnAvancar;

    @FXML
    private Button btnAddLinha;

    @FXML
    private Button btnRmvLinha;

    private String nomeTorneio;
    private String nomeJogo;

    public void nomearTorneio(ActionEvent actionEvent) {
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

    // teste
    //TorneioRepoLista torneioRepoLista = new TorneioRepoLista();

    public void abrirPopupEquipes() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("telas/gerTorneio/popupEquipes.fxml"));

        ControllerPopupEquipes.torneio.setNomeDoTorneio(nomeTorneio);
        ControllerPopupEquipes.torneio.setNomeJogo(nomeJogo);

        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void adicionarLinha(){
        Equipe novaEquipe = new Equipe(""); // começa com nome vazio
        listaEquipes.add(novaEquipe);
    }

    public void removerLinha(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicializa a lista e vincula à tabela
        listaEquipes = FXCollections.observableArrayList();
        tblEquipes.setItems(listaEquipes);

        // Configura a coluna
        clnNomesEquipes.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        clnNomesEquipes.setCellFactory(TextFieldTableCell.forTableColumn());
        clnNomesEquipes.setOnEditCommit(event -> {
            event.getRowValue().setNomeDaEquipe(event.getNewValue());
        });


        // Permite edição
        tblEquipes.setEditable(true);
        clnNomesEquipes.setCellFactory(TextFieldTableCell.forTableColumn());

        // Atualiza o valor no modelo quando editado
        clnNomesEquipes.setOnEditCommit(event -> {
            Equipe equipe = event.getRowValue();
            equipe.setNomeDaEquipe(event.getNewValue());
            ControllerPopupEquipes.torneio.adicionarEquipe(equipe);
        });



        clnNomesEquipes.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        // teste
        //System.out.println(ControllerPopupEquipes.torneio.listarEquipes());
        //System.out.println(equipe.getNomeDaEquipe());
    }
}
