package tech.w3ll.egameleague.repositores.listas;

import tech.w3ll.egameleague.modelos.Usuario;
import tech.w3ll.egameleague.repositores.LoginInterface;

public class LoginRepoList implements LoginInterface{
    private final UsuarioRepoList usuarioRepoList;

    public LoginRepoList(UsuarioRepoList usuarioRepoList){
        this.usuarioRepoList = usuarioRepoList;
    }

    public enum ResultadoLogin {
        SUCESSO,
        EMAIL_NAO_ENCONTRADO,
        SENHA_INCORRETA
    }

    public ResultadoLogin fazerLogin(String email, String senha){
        Usuario usuario = usuarioRepoList.buscarUsuarioPorEmail(email);
        if(usuario == null){
            return ResultadoLogin.EMAIL_NAO_ENCONTRADO;
        }
        if(!usuario.getSenha().equals(senha)){
            return ResultadoLogin.SENHA_INCORRETA;
        }
        return ResultadoLogin.SUCESSO;
    }
}
