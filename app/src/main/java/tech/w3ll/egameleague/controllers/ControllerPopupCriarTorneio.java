package tech.w3ll.egameleague.controllers;

import javafx.beans.property.SimpleStringProperty;
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
    private Button btnSalvar;

    @FXML
    private Button btnAddLinha;

    @FXML
    private Button btnRmvLinha;

    public static Torneio torneio;
    private String nomeTorneio;
    private String nomeJogo;
    private static String nomeEquipe;

    public static String getNomeEquipe() {
        return nomeEquipe;
    }

    public void setNomeEquipe(String nomeEquipe) {
        this.nomeEquipe = nomeEquipe;
    }

    public void nomearTorneio() {
        nomeTorneio = txtNomeTorneio.getText();
        torneio.setNomeDoTorneio(nomeTorneio);
    }

    public String getNomeTorneio() {
        return nomeTorneio;
    }

    public void definirJogo(){
        nomeJogo = txtNomeJogo.getText();
        torneio.setNomeJogo(nomeJogo);
    }

    public String getNomeJogo() {
        return nomeJogo;
    }

    public void abrirPopupEquipes() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("telas/gerTorneio/popupEquipes.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void salvar(){
        nomearTorneio();
        definirJogo();

        if (ControllerTelaTorneio.torneioRepoLista.buscarTorneioPorNome(torneio.getNomeDoTorneio()) == null){
            ControllerTelaTorneio.torneioRepoLista.cadastrarTorneio(torneio);
        }else {
            System.out.println("Já existe um torneio com esse nome");
        }

        //teste
        System.out.println("Equipes no Torneio:");
        for (Equipe t : torneio.getListaDeEquipes()) {
            System.out.println("- " + t.getNomeDaEquipe());
        }

        Stage stage = (Stage) btnSalvar.getScene().getWindow();
        stage.close();
    }

    public void adicionarLinha(){
        Equipe novaEquipe = new Equipe(""); // começa com nome vazio
        listaEquipes.add(novaEquipe);
        novaEquipe.setNomeDaEquipe("Equipe " + (listaEquipes.indexOf(novaEquipe)+1)); // muda o nome de acordo com a posição na lista
    }

    public void removerLinha(){
        listaEquipes.removeLast();
        torneio.getListaDeEquipes().removeLast();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // iniciando a instacia do objeto torneio
        torneio = new Torneio();

        // Inicializa a lista e vincula à tabela
        listaEquipes = FXCollections.observableArrayList();
        tblEquipes.setItems(listaEquipes);

        // Configura a coluna

        clnNomesEquipes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeDaEquipe()));

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
            torneio.adicionarEquipe(equipe);
        });

        // Selecionar linha e exibir nos campos
        tblEquipes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                 nomeEquipe = newSelection.getNomeDaEquipe();
            }
        });
    }
}
