package modelos;

import java.util.ArrayList;

public class Equipe {
    private ArrayList<Usuario> listaDeUsuarios = new ArrayList<>();
    private String nomeDaEquipe;
    private int numeroDeUsuarios;
    private boolean eliminado;

    public Equipe(String nomeDaEquipe, int numeroDeUsuarios) {
        this.nomeDaEquipe = nomeDaEquipe;
        this.numeroDeUsuarios = numeroDeUsuarios;
    }

    public ArrayList<Usuario> getListaDeUsuarios() {
        return listaDeUsuarios;
    }

    public void setListaDeUsuarios(ArrayList<Usuario> listaDeUsuarios) {
        this.listaDeUsuarios = listaDeUsuarios;
    }

    public String getNomeDaEquipe() {
        return nomeDaEquipe;
    }

    public void setNomeDaEquipe(String nomeDaEquipe) {
        this.nomeDaEquipe = nomeDaEquipe;
    }

    public int getNumeroDeUsuarios() {
        return numeroDeUsuarios;
    }

    public void setNumeroDeUsuarios(int numeroDeUsuarios) {
        this.numeroDeUsuarios = numeroDeUsuarios;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public void adicionarUsuario(Usuario usuario){
        listaDeUsuarios.add(usuario);
    }

    public void removerUsuario(Usuario usuario){
        listaDeUsuarios.remove(usuario);
    }
}
