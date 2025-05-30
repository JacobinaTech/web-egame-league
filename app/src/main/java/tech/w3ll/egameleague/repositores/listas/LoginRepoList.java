package tech.w3ll.egameleague.repositores.listas;

import tech.w3ll.egameleague.modelos.Usuario;
import tech.w3ll.egameleague.repositores.LoginInterface;

public class LoginRepoList implements LoginInterface{
    private final UsuarioRepoList usuarioRepoList;

    public LoginRepoList(UsuarioRepoList usuarioRepoList){
        this.usuarioRepoList = usuarioRepoList;
    }

    public boolean fazerLogin(String email, String senha){
        boolean logado; //Retorna boolean para sair do mainMenu e ir para o userMenu

        if(usuarioRepoList.buscarUsuarioParaLogin(email, senha) == null){
            return logado = false;
        }
        else {
            Usuario usuario = usuarioRepoList.buscarUsuarioParaLogin(email, senha);
            System.out.println("Bem vindo (a)! " + usuario.getUsuario());
            return logado = true;
        }
    }
}
