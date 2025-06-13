package tech.w3ll.egameleague.repositores.listas;

import tech.w3ll.egameleague.modelos.Usuario;

public class Sessao {
    public static Usuario usuarioLogado;

    public Usuario getUsuarioLogado(){
        return usuarioLogado;
    }

    public void setUsuarioLogado(String email, String senha){
        usuarioLogado = UsuarioRepoList.getInstace().buscarUsuarioParaLogin(email, senha);
    }
}
