package tech.w3ll.egameleague.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class UsuarioController {
    private LoginController loginController;

    @FXML
    private Label username;
    @FXML
    private Label userEmail;
    @FXML
    private Label userTelefone;
    @FXML
    private TableView tabelaHistorico;

    public LoginController getLoginController() {
        return loginController;
    }

    public Label getUsername() {
        return username;
    }

    public void setUsername(Label username) {
        this.username = username;
    }

    public Label getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(Label userEmail) {
        this.userEmail = userEmail;
    }

    public Label getUserTelefone() {
        return userTelefone;
    }

    public void setUserTelefone(Label userTelefone) {
        this.userTelefone = userTelefone;
    }

    public TableView getTabelaHistorico() {
        return tabelaHistorico;
    }

    public void setTabelaHistorico(TableView tabelaHistorico) {
        this.tabelaHistorico = tabelaHistorico;
    }
}
