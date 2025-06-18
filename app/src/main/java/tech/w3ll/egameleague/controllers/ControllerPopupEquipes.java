package tech.w3ll.egameleague.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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

    private String nomeEquipe;

    public String getNomeEquipe() {
        return nomeEquipe;
    }

    public void setNomeEquipe(String nomeEquipe) {
        this.nomeEquipe = nomeEquipe;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblNomeEquipe.setText(nomeEquipe);
    }
}
