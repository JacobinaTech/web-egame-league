package tech.w3ll.egameleague.repositores;

import tech.w3ll.egameleague.repositores.listas.LoginRepoList;

public interface LoginInterface {
    public LoginRepoList.ResultadoLogin fazerLogin(String email, String senha);
}
