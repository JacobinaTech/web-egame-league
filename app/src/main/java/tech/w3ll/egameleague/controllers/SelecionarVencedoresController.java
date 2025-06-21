package tech.w3ll.egameleague.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tech.w3ll.egameleague.HelloApplication;
import tech.w3ll.egameleague.modelos.Partida;
import tech.w3ll.egameleague.modelos.Equipe;
import tech.w3ll.egameleague.modelos.Historico;
import tech.w3ll.egameleague.repositores.listas.RepoListaHistorico;
import tech.w3ll.egameleague.repositores.listas.RepoListaEstatisticas;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class SelecionarVencedoresController {

    @FXML
    private VBox containerDePartidas;
    @FXML
    private Button botaoEmbaralhar;

    private List<Partida> partidasDaRodada;
    private TabelaController tabelaController;
    private boolean isEditingMode = false;
    private String nomeDoTorneio;

    private RepoListaHistorico repoHistorico = new RepoListaHistorico();
    private RepoListaEstatisticas repoEstatisticas = new RepoListaEstatisticas();


    public void carregarPartidas(List<Partida> partidas, TabelaController controller, boolean isEditing, String nomeTorneio) {
        this.partidasDaRodada = partidas;
        this.tabelaController = controller;
        this.isEditingMode = isEditing;
        this.nomeDoTorneio = nomeTorneio;
        this.atualizarCards();
        this.verificarEstadoDoEmbaralhamento();
        System.out.println("SelecionarVencedoresController: Carregando partidas para o torneio: " + nomeDoTorneio);
    }

    private void atualizarCards() {
        this.containerDePartidas.getChildren().clear();
        for (Partida partidaAtual : this.partidasDaRodada) {
            try {
                URL fxmlLocal = HelloApplication.class.getResource("/tech/w3ll/egameleague/telas/torneio/partida-card.fxml");
                if (fxmlLocal == null) {
                    System.err.println("FXML para PartidaCardController não encontrado: /tech/w3ll/egameleague/telas/torneio/partida-card.fxml");
                    continue;
                }

                FXMLLoader carregador = new FXMLLoader(fxmlLocal);
                Parent noDoCard = carregador.load();

                PartidaCardController controladorDoCard = carregador.getController();
                controladorDoCard.initData(partidaAtual, this, this.isEditingMode);

                noDoCard.setUserData(controladorDoCard);
                this.containerDePartidas.getChildren().add(noDoCard);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void verificarEstadoDoEmbaralhamento() {
        boolean algumVencedorDefinido = this.partidasDaRodada.stream()
                .anyMatch(p -> p.getVencedor() != null || p.getTime2() == null);
        this.botaoEmbaralhar.setDisable(algumVencedorDefinido);
    }

    public void onVencedorSelecionado(Partida partida, Equipe vencedor) {
        System.out.println("SelecionarVencedoresController: onVencedorSelecionado() chamado.");
        partida.definirVencedor(vencedor);
        this.verificarEstadoDoEmbaralhamento();

        // Salva histórico e atualiza estatísticas
        salvarHistoricoEstatisticasDaPartida(partida);

        if (this.tabelaController != null) {
            this.tabelaController.habilitarBotaoDeEdicao();
            this.tabelaController.atualizarVisualDaTabelaAtiva();
        }

        for (javafx.scene.Node no : this.containerDePartidas.getChildren()) {
            if (no.getUserData() instanceof PartidaCardController) {
                final PartidaCardController controladorDoCard = (PartidaCardController) no.getUserData();
                if (controladorDoCard.getPartida().equals(partida)) {
                    Platform.runLater(controladorDoCard::atualizarVisual);
                    break;
                }
            }
        }

        if (!this.isEditingMode) {
            boolean todasAsPartidasDefinidas = this.partidasDaRodada.stream()
                    .allMatch(p -> p.getVencedor() != null || p.getTime2() == null);

            if (todasAsPartidasDefinidas) {
                System.out.println("SelecionarVencedoresController: Todas as partidas da rodada definidas. Chamando rodadaConcluida().");
                if (this.tabelaController != null) {
                    this.tabelaController.rodadaConcluida();
                }
                Stage janela = (Stage) this.containerDePartidas.getScene().getWindow();
                janela.close();
            }
        }
    }

    private void salvarHistoricoEstatisticasDaPartida(Partida partida) {
        System.out.println("SelecionarVencedoresController: salvarHistoricoEstatisticasDaPartida() chamado.");
        LocalDateTime agora = LocalDateTime.now();

        String tipoEvento;
        int pontosGanhos = 0;
        List<String> jogadoresEnvolvidos = new ArrayList<>();
        int posicaoJogador = 0;

        if (partida.getTime2() == null) { // Caso de avanço automático (Bye)
            tipoEvento = "Avanço Automático";
            pontosGanhos = 10;

            jogadoresEnvolvidos.add(partida.getTime1().getNomeDaEquipe());
            posicaoJogador = 1;
            repoHistorico.adicionar(new Historico(agora, tipoEvento, pontosGanhos, jogadoresEnvolvidos, posicaoJogador, this.nomeDoTorneio));
            System.out.println("  -> Historico: Avanço automático para " + partida.getTime1().getNomeDaEquipe());
            repoEstatisticas.registrarVitoria(partida.getTime1().getNomeDaEquipe(), this.nomeDoTorneio);
            System.out.println("  -> Estatisticas: Vitória registrada para " + partida.getTime1().getNomeDaEquipe());

        } else { // Partida com dois times
            Equipe vencedor = partida.getVencedor();
            Equipe perdedor = (vencedor != null) ? (vencedor.equals(partida.getTime1()) ? partida.getTime2() : partida.getTime1()) : null;

            if (vencedor != null) {
                tipoEvento = "Partida Concluída";

                // Adiciona histórico para o vencedor
                pontosGanhos = 15;
                repoHistorico.adicionar(new Historico(agora, tipoEvento, pontosGanhos, List.of(vencedor.getNomeDaEquipe(), perdedor.getNomeDaEquipe()), 1, this.nomeDoTorneio));
                System.out.println("  -> Historico: Vitoria registrada para " + vencedor.getNomeDaEquipe());

                // Adiciona histórico para o perdedor (se houver)
                if (perdedor != null) {
                    pontosGanhos = 5;
                    repoHistorico.adicionar(new Historico(agora, tipoEvento, pontosGanhos, List.of(perdedor.getNomeDaEquipe(), vencedor.getNomeDaEquipe()), 2, this.nomeDoTorneio));
                    System.out.println("  -> Historico: Derrota registrada para " + perdedor.getNomeDaEquipe());
                }

                // Atualiza Estatísticas
                repoEstatisticas.registrarVitoria(vencedor.getNomeDaEquipe(), this.nomeDoTorneio);
                System.out.println("  -> Estatisticas: Vitória registrada para " + vencedor.getNomeDaEquipe());
                if (perdedor != null) {
                    repoEstatisticas.registrarDerrota(perdedor.getNomeDaEquipe(), this.nomeDoTorneio);
                    System.out.println("  -> Estatisticas: Derrota registrada para " + perdedor.getNomeDaEquipe());
                }
            }
        }
        System.out.println("SelecionarVencedoresController: Tamanho atual do RepoListaHistorico: " + repoHistorico.listarTodos().size());
        System.out.println("SelecionarVencedoresController: Tamanho atual do RepoListaEstatisticas: " + repoEstatisticas.listarTodos().size());
    }

    @FXML
    void aoClicarEmbaralhar() {
        System.out.println("SelecionarVencedoresController: aoClicarEmbaralhar() chamado.");
        List<Equipe> timesNaRodada = new ArrayList<>();
        for (Partida p : this.partidasDaRodada) {
            if (isEditingMode && p.getVencedor() != null) {
                p.resetar();
            }
            if (p.getVencedor() == null) {
                if (p.getTime1() != null) timesNaRodada.add(p.getTime1());
                if (p.getTime2() != null) timesNaRodada.add(p.getTime2());
            }
        }

        Collections.shuffle(timesNaRodada);

        List<Partida> novasPartidas = new ArrayList<>();
        for (int i = 0; i < timesNaRodada.size(); i += 2) {
            Equipe time1 = timesNaRodada.get(i);
            Equipe time2 = (i + 1 < timesNaRodada.size()) ? timesNaRodada.get(i + 1) : null;
            novasPartidas.add(new Partida(time1, time2));
        }

        this.partidasDaRodada.clear();
        this.partidasDaRodada.addAll(novasPartidas);

        for (Partida p : this.partidasDaRodada) {
            p.resetar();
        }

        this.atualizarCards();
        if (this.tabelaController != null) {
            this.tabelaController.atualizarVisualDaTabelaAtiva();
        }
        this.verificarEstadoDoEmbaralhamento();
        System.out.println("SelecionarVencedoresController: Embaralhamento concluído.");
    }
}