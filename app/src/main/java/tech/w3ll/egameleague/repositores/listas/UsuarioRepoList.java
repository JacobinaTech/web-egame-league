package tech.w3ll.egameleague.repositores.listas;

import tech.w3ll.egameleague.modelos.Usuario;
import tech.w3ll.egameleague.repositores.UsuarioInterface;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepoList implements UsuarioInterface {
    public static UsuarioRepoList usuarioRepoList;
     List<Usuario> listaUsuarios = new ArrayList<>();

    public static UsuarioRepoList getInstace(){
        if(usuarioRepoList == null){
            usuarioRepoList = new UsuarioRepoList();
        }
        return usuarioRepoList;
    }

    public List<Usuario> getListaUsuarios(){
        return listaUsuarios;
    }

    public enum ResultadoCadastro {
        SUCESSO,
        USUARIO_JA_CADASTRADO,
        EMAIL_JA_CADASTRADO
    }

    public ResultadoCadastro fazerCadastro(String usuario, String email, String senha, String telefone){
        if(buscarUsuarioPorUsuario(usuario) != null){
            return ResultadoCadastro.USUARIO_JA_CADASTRADO;
        }
        if(buscarUsuarioPorEmail(email) != null){
            return ResultadoCadastro.EMAIL_JA_CADASTRADO;
        }
        Usuario usuarioCadastrado = new Usuario(usuario, email, senha, telefone);
        listaUsuarios.add(usuarioCadastrado);
        return ResultadoCadastro.SUCESSO;
    }

    public boolean removerUsuario(Usuario usuarioRemover){
        boolean usuarioRemovido;
        try {
            listaUsuarios.removeIf(usuario -> usuarioRemover == usuario);
            return usuarioRemovido = true;
        } catch(Exception e){
            return usuarioRemovido = false;
        }
    }

    public Usuario buscarUsuarioPorUsuario(String usuario){
        for(Usuario usuarioBuscar : listaUsuarios){
            if(usuarioBuscar.getUsuario().equals(usuario)){
                return usuarioBuscar;
            }
        }
        return null;
    }

    public Usuario buscarUsuarioPorEmail(String email){
        for(Usuario usuarioBuscar : listaUsuarios){
            if(usuarioBuscar.getEmail().equals(email)){
                return usuarioBuscar;
            }
        }
        return null;
    }

    public Usuario buscarUsuarioParaLogin(String email, String senha){
        for(Usuario usuarioBuscar : listaUsuarios){
            if(usuarioBuscar.getEmail().equals(email) && usuarioBuscar.getSenha().equals(senha)){
                return usuarioBuscar;
            }
        }
        return null;
    }
}
