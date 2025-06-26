package tech.w3ll.egameleague.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tech.w3ll.egameleague.modelos.Equipe;
import tech.w3ll.egameleague.modelos.Torneio;
import tech.w3ll.egameleague.modelos.Usuario;
import tech.w3ll.egameleague.repositores.listas.TorneioRepoLista;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPopupEquipes implements  Initializable {

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
    private Button btnPronto;

    @FXML
    private TableView<Usuario> tblUsuarios;

    @FXML
    private TableColumn<Usuario, String> clnUsuarios;

    private ObservableList<Usuario> listaUsuarios;

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
        Usuario novoUsuario = new Usuario(""); // começa com nome vazio
        listaUsuarios.add(novoUsuario);
    }

    public void removerLinha(){
        listaUsuarios.removeLast();
        ControllerPopupCriarTorneio.torneio.buscarEquipePorNome(nomeEquipe).getListaDeUsuarios().removeLast();
    }

    public void concluir(){
        Stage stage = (Stage) btnPronto.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaUsuarios = FXCollections.observableArrayList();
        tblUsuarios.setItems(listaUsuarios);

        clnUsuarios.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario()));

        clnUsuarios.setCellFactory(TextFieldTableCell.forTableColumn());
        clnUsuarios.setOnEditCommit(event -> {
            event.getRowValue().setUsuario(event.getNewValue());
        });

        // Permite edição
        tblUsuarios.setEditable(true);
        clnUsuarios.setCellFactory(TextFieldTableCell.forTableColumn());

        // Atualiza o valor no modelo quando editado
        clnUsuarios.setOnEditCommit(event -> {
            Usuario usuario = event.getRowValue();
            usuario.setUsuario(event.getNewValue());
            ControllerPopupCriarTorneio.torneio.buscarEquipePorNome(nomeEquipe).adicionarUsuario(usuario);

            //teste
            System.out.println("Usuários da equipe:" + nomeEquipe);
            for (Usuario t : ControllerPopupCriarTorneio.torneio.buscarEquipePorNome(nomeEquipe).getListaDeUsuarios()){
                System.out.println("- " + t.getUsuario());
            }

        });

        nomeEquipe = ControllerPopupCriarTorneio.getNomeEquipe();

        lblNomeEquipe.setText(nomeEquipe);
        System.out.println(nomeEquipe);
    }
}
