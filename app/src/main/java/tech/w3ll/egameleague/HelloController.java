package tech.w3ll.egameleague;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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
    private PasswordField senhaTextField;
    @FXML
    private TextField telefoneTextField;
    @FXML
    private TextField emailLoginTextField;
    @FXML
    private PasswordField senhaLoginTextField;
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
        switch (usuarioRepoList.fazerCadastro(getUsuarioTextField(), getEmailTextField(), getSenhaTextField(), getTelefoneTextField())) {
            case SUCESSO:
                botaoCadastrar.setText("Usuário cadastrado com sucesso!");
                System.out.println(usuarioRepoList.getListaUsuarios());
                break;

            case EMAIL_JA_CADASTRADO:
                botaoCadastrar.setText("Email já cadastrado!");
                break;

            case USUARIO_JA_CADASTRADO:
                botaoCadastrar.setText("Usuário já cadastrado!");
                break;
        }
        pauseTransition.setOnFinished(event -> botaoCadastrar.setText("Cadastrar"));
        pauseTransition.play();
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
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to eGameLeague!");
    }
}