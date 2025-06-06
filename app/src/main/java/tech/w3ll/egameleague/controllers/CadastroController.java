package tech.w3ll.egameleague.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import tech.w3ll.egameleague.HelloApplication;
import tech.w3ll.egameleague.repositores.listas.UsuarioRepoList;

import java.io.IOException;
import java.util.List;

public class CadastroController {
    private final UsuarioRepoList usuarioRepoList = new UsuarioRepoList();

    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usuarioTextField;
    @FXML
    private PasswordField senhaTextField;
    @FXML
    private TextField senhaVisivelTextField;
    @FXML
    private Button botaoOlhoFechado;
    @FXML
    private Button botaoOlhoAberto;
    @FXML
    private TextField telefoneTextField;

    @FXML
    private Button botaoCadastrar;

    public String getEmailTextField() {
        return emailTextField.getText();
    }

    public String getUsuarioTextField() {
        return usuarioTextField.getText();
    }

    public String getSenhaTextField() {
        return senhaTextField.isVisible() ? senhaTextField.getText() : senhaVisivelTextField.getText();
    }

    public String getTelefoneTextField() {
        return telefoneTextField.getText();
    }

    private void loadScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("telas/cadastro/interfaceGraficaLogin.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setHeight(200);
            stage.setWidth(200);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List getListaUsuarios(){
        return usuarioRepoList.getListaUsuarios();
    }

    public void fazerCadastro() {
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
        switch (usuarioRepoList.fazerCadastro(getUsuarioTextField(), getEmailTextField(), getSenhaTextField(), getTelefoneTextField())) {
            case SUCESSO:
                botaoCadastrar.setText("Usuário cadastrado com sucesso!");
                System.out.println(usuarioRepoList.getListaUsuarios());
                pauseTransition.setOnFinished(event -> botaoCadastrar.setText("Cadastrar"));
                pauseTransition.play();
                loadScreen();
                break;

            case EMAIL_JA_CADASTRADO:
                botaoCadastrar.setText("Email já cadastrado!");
                pauseTransition.setOnFinished(event -> botaoCadastrar.setText("Cadastrar"));
                pauseTransition.play();
                break;

            case USUARIO_JA_CADASTRADO:
                botaoCadastrar.setText("Usuário já cadastrado!");
                pauseTransition.setOnFinished(event -> botaoCadastrar.setText("Cadastrar"));
                pauseTransition.play();
                break;
        }
    }

    @FXML
    private void mostrarSenhaCadastro() {
        senhaVisivelTextField.setText(senhaTextField.getText());
        senhaTextField.setVisible(false);
        senhaTextField.setManaged(false);
        senhaVisivelTextField.setVisible(true);
        senhaVisivelTextField.setManaged(true);
        botaoOlhoFechado.setVisible(false);
        botaoOlhoFechado.setManaged(false);
        botaoOlhoAberto.setVisible(true);
        botaoOlhoAberto.setManaged(true);
    }

    @FXML
    private void esconderSenhaCadastro() {
        senhaTextField.setText(senhaVisivelTextField.getText());
        senhaVisivelTextField.setVisible(false);
        senhaVisivelTextField.setManaged(false);
        senhaTextField.setVisible(true);
        senhaTextField.setManaged(true);
        botaoOlhoAberto.setVisible(false);
        botaoOlhoAberto.setManaged(false);
        botaoOlhoFechado.setVisible(true);
        botaoOlhoFechado.setManaged(true);
    }
}