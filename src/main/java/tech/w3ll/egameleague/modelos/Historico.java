package tech.w3ll.egameleague.modelos;

import java.time.LocalDateTime;
import java.util.List;

public class Historico {
    private LocalDateTime dataHora;
    private String tipoEvento;
    private int pontos;
    private List<String> jogadores;
    private int posicaoJogador;
    private String nomeJogo;

    public Historico(LocalDateTime dataHora, String tipoEvento, int pontos, List<String> jogadores, int posicaoJogador, String nomeJogo) {
        this.dataHora = dataHora;
        this.tipoEvento = tipoEvento;
        this.pontos = pontos;
        this.jogadores = jogadores;
        this.posicaoJogador = posicaoJogador;
        this.nomeJogo = nomeJogo;
    }

    public LocalDateTime getDataHora() { return dataHora; }
    public String getTipoEvento() { return tipoEvento; }
    public int getPontos() { return pontos; }
    public List<String> getJogadores() { return jogadores; }
    public int getPosicaoJogador() { return posicaoJogador; }
    public String getNomeJogo() { return nomeJogo; }

    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public void setTipoEvento(String tipoEvento) { this.tipoEvento = tipoEvento; }
    public void setPontos(int pontos) { this.pontos = pontos; }
    public void setJogadores(List<String> jogadores) { this.jogadores = jogadores; }
    public void setPosicaoJogador(int posicaoJogador) { this.posicaoJogador = posicaoJogador; }
    public void setNomeJogo(String nomeJogo) { this.nomeJogo = nomeJogo; }
}