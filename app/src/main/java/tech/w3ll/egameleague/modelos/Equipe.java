package tech.w3ll.egameleague.modelos;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Equipe {
    //teste
    private final SimpleStringProperty nome;

    private ArrayList<Usuario> listaDeUsuarios = new ArrayList<>();
    private int id;
    private String nomeDaEquipe;
    private int numeroDeUsuarios;
    private boolean eliminado;

    public Equipe(String nomeDaEquipe, int numeroDeUsuarios) {
        this.nomeDaEquipe = nomeDaEquipe;
        this.numeroDeUsuarios = numeroDeUsuarios;
        //teste
        nome = null;
    }

    public Equipe(String nomeDaEquipe){
        this.nomeDaEquipe = nomeDaEquipe;
        this.eliminado = false;
        //teste
        this.nome = new SimpleStringProperty(nomeDaEquipe);
    }

    public ArrayList<Usuario> getListaDeUsuarios() {
        return listaDeUsuarios;
    }

    public void setListaDeUsuarios(ArrayList<Usuario> listaDeUsuarios) {
        this.listaDeUsuarios = listaDeUsuarios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // teste
    public SimpleStringProperty nomeProperty() {
        return nome();
    }

    // teste
    private SimpleStringProperty nome() {
        return nome;
    }

    public String getNomeDaEquipe() {
        return nomeDaEquipe;
    }

    public void setNomeDaEquipe(String nomeDaEquipe) {
        this.nomeDaEquipe = nomeDaEquipe;
        //teste
        this.nome.set(nomeDaEquipe);
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

    public void eliminar() {
        this.eliminado = true;
    }

    public void anularEliminacao() {
        this.eliminado = false;
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
