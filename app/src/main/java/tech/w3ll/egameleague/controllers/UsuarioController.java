package tech.w3ll.egameleague.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import tech.w3ll.egameleague.repositores.listas.Sessao;
import javafx.fxml.Initializable;
import tech.w3ll.egameleague.repositores.listas.UsuarioRepoList;

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
    private TextField alterarUsuario;
    @FXML
    private Button btnAlterarUsuario;
    @FXML
    private TextField alterarTelefone;
    @FXML
    private Button btnAlterarTelefone;
    @FXML
    private PasswordField novaSenha;
    @FXML
    private PasswordField alterarSenha;
    @FXML
    private Button btnAlterarSenha;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            username.setText(sessao.getUsuarioLogado().getUsuario());
            usernamePerfil.setText(sessao.getUsuarioLogado().getUsuario());
            userEmail.setText(sessao.getUsuarioLogado().getEmail());
            userTelefone.setText(sessao.getUsuarioLogado().getTelefone());
        } catch(Exception e){
            username.setText("Convidado");
            usernamePerfil.setText("Convidado");
            userEmail.setText("");
            userTelefone.setText("");
        }
    }

    public void alterarUsuario(){
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
        if(UsuarioRepoList.getInstace().buscarUsuarioPorUsuario(alterarUsuario.getText()) == null){
            sessao.getUsuarioLogado().setUsuario(alterarUsuario.getText());
            btnAlterarUsuario.setText("Alterado!");
            pauseTransition.setOnFinished(event -> btnAlterarUsuario.setText("Alterar"));
            pauseTransition.play();
            username.setText(sessao.getUsuarioLogado().getUsuario());
        }
        else{
            btnAlterarUsuario.setText("Usuário já existe!");
            pauseTransition.setOnFinished(event -> btnAlterarUsuario.setText("Alterar"));
            pauseTransition.play();
        }
    }

    public void alterarTelefone(){
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
        sessao.getUsuarioLogado().setTelefone(alterarTelefone.getText());
        btnAlterarTelefone.setText("Alterado!");
        pauseTransition.setOnFinished(event -> btnAlterarTelefone.setText("Alterar"));
        pauseTransition.play();
        userTelefone.setText(alterarTelefone.getText());
    }

    public void alterarSenha(){
        System.out.println("uwu");
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




}
