package tech.w3ll.egameleague.modelos;

import javafx.beans.property.SimpleStringProperty;
import tech.w3ll.egameleague.repositores.listas.TorneioRepoLista;

import java.util.ArrayList;

public class Torneio {
    //teste
    private SimpleStringProperty nome;

    private static Torneio torneio;
    private ArrayList<Equipe> listaDeEquipes;
    private int numeroDeEquipes;
    private String nomeDoTorneio;
    private String nomeJogo;

    public Torneio() {
        listaDeEquipes = new ArrayList<>();
    }

    public Torneio(String nomeDoTorneio) {
        this.nomeDoTorneio = nomeDoTorneio;
        listaDeEquipes = new ArrayList<>();
        //teste
        this.nome = new SimpleStringProperty(nomeDoTorneio);
    }

    public Torneio(String nomeDoTorneio, String nomeJogo) {
        this.nomeDoTorneio = nomeDoTorneio;
        this.nomeJogo = nomeJogo;
        listaDeEquipes = new ArrayList<>();
        //teste
        this.nome = new SimpleStringProperty(nomeDoTorneio);
    }

    public static Torneio getInstance() {
        if (torneio == null)
            torneio = new Torneio();
        return torneio;
    }

    public ArrayList<Equipe> getListaDeEquipes() {
        return listaDeEquipes;
    }

    public void setListaDeEquipes(ArrayList<Equipe> listaDeEquipes) {
        this.listaDeEquipes = listaDeEquipes;
    }

    public String listarEquipes() {
        String[] lista = new String[0];
        int contador = 0;
        for (Equipe equipe : listaDeEquipes) {
            contador++;
            lista[contador] = equipe.getNomeDaEquipe();
        }
        return lista.toString();
    }

    public int getNumeroDeEquipes() {
        return numeroDeEquipes;
    }

    public void setNumeroDeEquipes(int numeroDeEquipes) {
        this.numeroDeEquipes = numeroDeEquipes;
    }

    public String getNomeDoTorneio() {
        return nomeDoTorneio;
    }

    //teste
    public SimpleStringProperty nomeProperty() {
        return nome;
    }

    public void setNomeDoTorneio(String nomeDoTorneio) {
        this.nomeDoTorneio = nomeDoTorneio;
    }

    public String getNomeJogo() {
        return nomeJogo;
    }

    public void setNomeJogo(String nomeJogo) {
        this.nomeJogo = nomeJogo;
    }

    public void adicionarEquipe(Equipe equipe){
        listaDeEquipes.add(equipe);
    }

    public void removerEquipe(Equipe equipe){
        listaDeEquipes.remove(equipe);
    }
}
