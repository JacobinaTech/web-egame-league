package repositorios.listas;

import modelos.Usuario;
import repositorios.UsuarioInterface;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepoList implements UsuarioInterface {
    List<Usuario> listaUsuarios = new ArrayList<>();

    public List<Usuario> getListaUsuarios(){
        return listaUsuarios;
    }

    public Usuario fazerCadastro(String usuario, String email, String senha, String telefone){
        return new Usuario(usuario, email, senha, telefone);
    }

    public void addUsuarios(Usuario usuario){
        listaUsuarios.add(usuario);
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

    public Usuario buscarUsuario(String usuario){
        for(Usuario usuarioBuscar : listaUsuarios){
            if(usuarioBuscar.getUsuario().equals(usuario)){
                return usuarioBuscar;
            }
        }
        return null;
    }

    public Usuario buscarUsuario(String email, String senha){
        for(Usuario usuarioBuscar : listaUsuarios){
            if(usuarioBuscar.getEmail().equals(email) && usuarioBuscar.getSenha().equals(senha)){
                return usuarioBuscar;
            }
        }
        return null;
    }
}
