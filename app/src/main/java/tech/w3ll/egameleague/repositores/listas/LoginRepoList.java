package repositorios.listas;

import modelos.Usuario;
import repositorios.LoginInterface;

import java.util.Scanner;

public class LoginRepoList implements LoginInterface{
    private final UsuarioRepoList usuarioRepoList;

    public LoginRepoList(UsuarioRepoList usuarioRepoList){
        this.usuarioRepoList = usuarioRepoList;
    }

    public boolean fazerLogin(String email, String senha){
        boolean logado; //Retorna boolean para sair do mainMenu e ir para o userMenu

        if(usuarioRepoList.buscarUsuario(email, senha) == null){
            System.out.println("Email ou senha incorretos! ");
            return logado = false;
        }
        else {
            Usuario usuario = usuarioRepoList.buscarUsuario(email, senha);
            System.out.println("Bem vindo (a)! " + usuario.getUsuario());
            return logado = true;
        }
    }
}
