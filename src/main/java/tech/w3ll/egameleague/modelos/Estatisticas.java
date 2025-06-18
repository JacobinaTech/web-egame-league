package tech.w3ll.egameleague.modelos;

import java.util.List;

public class Estatisticas {
    private String nomeJogadorOuEquipe;
    private String nomeTorneio;
    private int pontos;
    private int partidasJogadas;
    private int vitorias;
    private int derrotas;
    private int ranking;

    public Estatisticas(String nomeJogadorOuEquipe, String nomeTorneio, int partidasJogadas, int vitorias, int derrotas) {
        this.nomeJogadorOuEquipe = nomeJogadorOuEquipe;
        this.nomeTorneio = nomeTorneio;
        this.partidasJogadas = partidasJogadas;
        this.vitorias = vitorias;
        this.derrotas = derrotas;
        this.pontos = calcularPontuacao();
    }

    private int calcularPontuacao() {
        return (vitorias * 15) + (derrotas * 3);
    }

    public String getNomeJogadorOuEquipe() { return nomeJogadorOuEquipe; }
    public String getNomeTorneio() { return nomeTorneio; }
    public int getPontos() { return pontos; }
    public int getPartidasJogadas() { return partidasJogadas; }
    public int getVitorias() { return vitorias; }
    public int getDerrotas() { return derrotas; }
    public void setRanking ( int ranking ) { this.ranking = ranking; }
    public int getRanking () { return ranking; }

    public void adicionarVitoria() {
        this.vitorias++;
        this.partidasJogadas++;
        this.pontos = calcularPontuacao();
    }

    public void adicionarDerrota() {
        this.derrotas++;
        this.partidasJogadas++;
        this.pontos = calcularPontuacao();
    }

    public void setPartidasJogadas(int partidasJogadas) {
        this.partidasJogadas = partidasJogadas;
    }

    public void setVitorias(int vitorias) {
        this.vitorias = vitorias;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }
}