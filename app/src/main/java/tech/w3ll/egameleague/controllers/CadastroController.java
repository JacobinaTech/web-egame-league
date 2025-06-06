package tech.w3ll.egameleague.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import tech.w3ll.egameleague.repositores.listas.LoginRepoList;
import tech.w3ll.egameleague.repositores.listas.UsuarioRepoList;

public class CadastroController {

    private final UsuarioRepoList usuarioRepoList = new UsuarioRepoList();
    private final LoginRepoList loginRepoList = new LoginRepoList(usuarioRepoList);

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
    private TextField emailLoginTextField;
    @FXML
    private PasswordField senhaLoginTextField;
    @FXML
    private TextField senhaLoginVisivelTextField;
    @FXML
    private Button botaoOlhoFechadoLogin;
    @FXML
    private Button botaoOlhoAbertoLogin;

    @FXML
    private Button botaoCadastrar;
    @FXML
    private Button botaoFazerLogin;

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

    public TextField getSenhaLoginTextField() {
        return senhaLoginTextField.isVisible() ? senhaLoginTextField : senhaLoginVisivelTextField;
    }

    public TextField getEmailLoginTextField() {
        return emailLoginTextField;
    }

    public void fazerCadastro() {
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
        switch (usuarioRepoList.fazerCadastro(getUsuarioTextField(), getEmailTextField(), getSenhaTextField(), getTelefoneTextField())) {
            case SUCESSO:
                botaoCadastrar.setText("Usuário cadastrado com sucesso!");
                System.out.println(usuarioRepoList.getListaUsuarios());
                pauseTransition.setOnFinished(event -> botaoCadastrar.setText("Cadastrar"));
                pauseTransition.play();
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

    public void fazerLogin() {
        String email = getEmailLoginTextField().getText();
        String senha = getSenhaLoginTextField().getText();

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));

        if(loginRepoList.fazerLogin(email, senha) == null){
            botaoFazerLogin.setText("E-mail ou senha incorretos!");
            pauseTransition.setOnFinished(event -> botaoFazerLogin.setText("Fazer login"));
            pauseTransition.play();
        } else {

            botaoFazerLogin.setText("Login efetuado com sucesso!");
            pauseTransition.setOnFinished(event -> botaoFazerLogin.setText("Fazer login"));
            pauseTransition.play();

            pauseTransition.setOnFinished(event -> botaoFazerLogin.setText("Fazer login"));
            pauseTransition.play();
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

    @FXML
    private void mostrarSenhaLogin() {
        senhaLoginVisivelTextField.setText(senhaLoginTextField.getText());
        senhaLoginTextField.setVisible(false);
        senhaLoginTextField.setManaged(false);
        senhaLoginVisivelTextField.setVisible(true);
        senhaLoginVisivelTextField.setManaged(true);
        botaoOlhoFechadoLogin.setVisible(false);
        botaoOlhoFechadoLogin.setManaged(false);
        botaoOlhoAbertoLogin.setVisible(true);
        botaoOlhoAbertoLogin.setManaged(true);
    }

    @FXML
    private void esconderSenhaLogin() {
        senhaLoginTextField.setText(senhaLoginVisivelTextField.getText());
        senhaLoginVisivelTextField.setVisible(false);
        senhaLoginVisivelTextField.setManaged(false);
        senhaLoginTextField.setVisible(true);
        senhaLoginTextField.setManaged(true);
        botaoOlhoAbertoLogin.setVisible(false);
        botaoOlhoAbertoLogin.setManaged(false);
        botaoOlhoFechadoLogin.setVisible(true);
        botaoOlhoFechadoLogin.setManaged(true);
    }
}