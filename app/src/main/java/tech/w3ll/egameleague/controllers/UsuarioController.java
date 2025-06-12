package tech.w3ll.egameleague.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class UsuarioController {
    private LoginController loginController;

    @FXML
    private Label username;
    @FXML
    private Label usernamePerfil;
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

    public Label getUsernamePerfil(){
        return usernamePerfil;
    }

    public void setUsernamePerfil(String usernamePerfil){
        this.usernamePerfil.setText(usernamePerfil);
    }

    public Label getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail.setText(userEmail);
    }

    public Label getUserTelefone() {
        return userTelefone;
    }

    public void setUserTelefone(String userTelefone) {
        this.userTelefone.setText(userTelefone);
    }

    public TableView getTabelaHistorico() {
        return tabelaHistorico;
    }

    public void setTabelaHistorico(TableView tabelaHistorico) {
        this.tabelaHistorico = tabelaHistorico;
    }
}
