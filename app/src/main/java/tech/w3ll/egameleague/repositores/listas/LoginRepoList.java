package tech.w3ll.egameleague.repositores.listas;

import tech.w3ll.egameleague.modelos.Usuario;
import tech.w3ll.egameleague.repositores.LoginInterface;

public class LoginRepoList implements LoginInterface{
    private final UsuarioRepoList usuarioRepoList;

    public LoginRepoList(UsuarioRepoList usuarioRepoList){
        this.usuarioRepoList = usuarioRepoList;
    }

    public Usuario fazerLogin(String email, String senha){
        Usuario usuario = usuarioRepoList.buscarUsuarioPorEmail(email);
        if(usuario == null){
            return null;
        }
        if(!usuario.getSenha().equals(senha)){
            return null;
        }
        return usuario;
    }
}
