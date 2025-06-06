package modelos;

import java.util.ArrayList;

public class Torneio {
    private ArrayList<Equipe> listaDeEquipes = new ArrayList<>();
    private int numeroDeEquipes;
    private String nomeDoTorneio;

    public Torneio(int numeroDeEquipes, String nomeDoTorneio) {
        this.numeroDeEquipes = numeroDeEquipes;
        this.nomeDoTorneio = nomeDoTorneio;
    }

    public ArrayList<Equipe> getListaDeEquipes() {
        return listaDeEquipes;
    }

    public void setListaDeEquipes(ArrayList<Equipe> listaDeEquipes) {
        this.listaDeEquipes = listaDeEquipes;
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

    public void setNomeDoTorneio(String nomeDoTorneio) {
        this.nomeDoTorneio = nomeDoTorneio;
    }

    public void adicionarEquipe(Equipe equipe){
        listaDeEquipes.add(equipe);
    }

    public void removerEquipe(Equipe equipe){
        listaDeEquipes.remove(equipe);
    }
}
