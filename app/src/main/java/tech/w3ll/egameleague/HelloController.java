package tech.w3ll.egameleague;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import tech.w3ll.egameleague.repositores.listas.LoginRepoList;
import tech.w3ll.egameleague.repositores.listas.UsuarioRepoList;

public class HelloController {
    private final UsuarioRepoList usuarioRepoList = new UsuarioRepoList();
    private final LoginRepoList loginRepoList = new LoginRepoList(usuarioRepoList);

    @FXML
    private Label welcomeText;

    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usuarioTextField;
    @FXML
    private TextField senhaTextField;
    @FXML
    private TextField telefoneTextField;
    @FXML
    private TextField emailLoginTextField;
    @FXML
    private TextField senhaLoginTextField;
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
        return senhaTextField.getText();
    }

    public String getTelefoneTextField() {
        return telefoneTextField.getText();
    }

    public TextField getSenhaLoginTextField() {
        return senhaLoginTextField;
    }

    public TextField getEmailLoginTextField() {
        return emailLoginTextField;
    }

    public void fazerCadastro() {
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
        switch (usuarioRepoList.fazerCadastro(getUsuarioTextField(), getEmailTextField(), getSenhaTextField(), getTelefoneTextField())){
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

    public boolean fazerLogin() {
        boolean logado = false;

        if(!loginRepoList.fazerLogin(getEmailLoginTextField().getText(), getSenhaLoginTextField().getText())){
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
            botaoFazerLogin.setText("Email ou senha inválidos!");
            pauseTransition.setOnFinished(event -> botaoFazerLogin.setText("Fazer login"));
            pauseTransition.play();
        }
        else{
            return logado = true;
        }
        return logado;
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to eGameLeague!");
    }
}