package tech.w3ll.egameleague.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import tech.w3ll.egameleague.repositores.listas.Sessao;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class UsuarioController implements Initializable {
    private LoginController loginController;
    private Sessao sessao = new Sessao();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(sessao.getUsuarioLogado().getUsuario());
        usernamePerfil.setText(sessao.getUsuarioLogado().getUsuario());
        userEmail.setText(sessao.getUsuarioLogado().getEmail());
        userTelefone.setText(sessao.getUsuarioLogado().getTelefone());
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public Label getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username.setText(username);
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
