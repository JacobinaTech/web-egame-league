package tech.w3ll.egameleague.controllers;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import tech.w3ll.egameleague.HelloApplication;
import tech.w3ll.egameleague.modelos.Partida;
import tech.w3ll.egameleague.modelos.Tabela;
import tech.w3ll.egameleague.modelos.Time;
import tech.w3ll.egameleague.repositores.listas.TimeRepoLista;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TabelaController {

    @FXML private VBox containerDasRodadas;
    @FXML private Button botaoJogarRodada;
    @FXML private Button botaoEditarRodada;
    @FXML private ScrollPane scrollPanePrincipal;

    private Tabela tabela;
    private int rodadaAtualNumero = 0;
    private boolean isEditing = false;
    private List<ObservableList<Partida>> historicoDeRodadas = new ArrayList<>();
    private String nomeDoTorneio = "Campeonato eGameLeague";

    private static final double ALTURA_DA_LINHA = 48.0;

    @FXML
    public void initialize() {
        System.out.println("TabelaController: initialize() chamado.");
        carregarDadosIniciais();
    }

    private void carregarDadosIniciais() {
        System.out.println("TabelaController: Carregando dados iniciais.");
        try {
            TimeRepoLista repositorioDeTimes = new TimeRepoLista();
            repositorioDeTimes.resetarTabela();

            repositorioDeTimes.adicionar(new Time("Breno"));
            repositorioDeTimes.adicionar(new Time("Dalmatas"));
            repositorioDeTimes.adicionar(new Time("Kauã"));
            repositorioDeTimes.adicionar(new Time("Davi"));
            repositorioDeTimes.adicionar(new Time("Pedro"));
            repositorioDeTimes.adicionar(new Time("Paulo"));
            repositorioDeTimes.adicionar(new Time("Lucas"));
            repositorioDeTimes.adicionar(new Time("Gabriel"));
            repositorioDeTimes.adicionar(new Time("Matheus"));
            repositorioDeTimes.adicionar(new Time("Felipe"));
            repositorioDeTimes.adicionar(new Time("Gustavo"));
            repositorioDeTimes.adicionar(new Time("Rafael"));
            repositorioDeTimes.adicionar(new Time("Bruno"));
            repositorioDeTimes.adicionar(new Time("Thiago"));
            repositorioDeTimes.adicionar(new Time("Rodrigo"));
            repositorioDeTimes.adicionar(new Time("Vinicius"));

            iniciarTorneio(new ArrayList<>(repositorioDeTimes.listarTodos()));
            System.out.println("TabelaController: Dados iniciais carregados e torneio iniciado.");

        } catch (Exception e) {
            System.out.println("Erro ao carregar dados iniciais: ");
            e.printStackTrace();
        }
    }

    public void iniciarTorneio(ArrayList<Time> times) {
        System.out.println("TabelaController: Iniciando torneio com " + times.size() + " times.");
        if (times.size() < 2) {
            return;
        }

        this.tabela = new Tabela(times);
        this.tabela.embaralharTimes();
        this.rodadaAtualNumero = 0;
        this.historicoDeRodadas.clear();
        this.containerDasRodadas.getChildren().clear();

        avancarParaProximaRodada();
    }

    private void avancarParaProximaRodada() {
        System.out.println("TabelaController: Avançando para a próxima rodada.");
        if (rodadaAtualNumero > 0) {
            ObservableList<Partida> ultimaRodada = historicoDeRodadas.get(historicoDeRodadas.size() - 1);
            tabela.avancarParaProximaFase(ultimaRodada);
            desabilitarTabelaAnterior();
        }

        if (tabela.temCampeao()) {
            exibirCampeao();
            return;
        }

        rodadaAtualNumero++;
        tabela.gerarConfrontos();
        List<Partida> novasPartidas = tabela.getPartidasAtuais();
        ObservableList<Partida> partidasObservaveis = FXCollections.observableArrayList(novasPartidas);
        historicoDeRodadas.add(partidasObservaveis);

        adicionarVisualDaRodada(partidasObservaveis);

        botaoJogarRodada.setText("Jogar " + gerarNomeDaRodada(partidasObservaveis.size()));
        botaoJogarRodada.setDisable(false);
        // O botão "Jogar Rodada" chama aoClicarJogarRodada, que abre SelecionarVencedoresController
        botaoJogarRodada.setOnAction(event -> aoClicarJogarRodada());
        botaoEditarRodada.setVisible(false);

        scrollarSuavemente();
        System.out.println("TabelaController: Rodada " + rodadaAtualNumero + " gerada com " + novasPartidas.size() + " partidas.");
    }

    private void scrollarSuavemente() {
        containerDasRodadas.heightProperty().addListener((obs, oldVal, newVal) -> {
            Platform.runLater(() -> {
                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(scrollPanePrincipal.vvalueProperty(), 1.0, Interpolator.EASE_BOTH);
                KeyFrame kf = new KeyFrame(Duration.millis(400), kv);
                timeline.getKeyFrames().add(kf);
                timeline.play();
            });
        });
    }

    private void adicionarVisualDaRodada(ObservableList<Partida> partidas) {
        String nomeRodada = gerarNomeDaRodada(partidas.size());
        Label tituloRodada = new Label(nomeRodada);
        tituloRodada.getStyleClass().add("rodada-title");

        TableView<Partida> tabelaDaRodada = criarTabelaParaRodada();
        tabelaDaRodada.setItems(partidas);
        ajustarAlturaDaTabela(tabelaDaRodada);

        containerDasRodadas.getChildren().addAll(tituloRodada, tabelaDaRodada);
    }

    private TableView<Partida> criarTabelaParaRodada() {
        TableView<Partida> tabela = new TableView<>();
        tabela.getStyleClass().add("results-table-view");

        TableColumn<Partida, Time> colunaTimeA = new TableColumn<>("Time A");
        colunaTimeA.setCellValueFactory(new PropertyValueFactory<>("time1"));

        TableColumn<Partida, Time> colunaTimeB = new TableColumn<>("Time B");
        colunaTimeB.setCellValueFactory(new PropertyValueFactory<>("time2"));

        TableColumn<Partida, Void> colunaVs = new TableColumn<>("");
        colunaVs.setMaxWidth(80);
        colunaVs.setMinWidth(80);
        colunaVs.setStyle("-fx-alignment: CENTER;");

        Callback<TableColumn<Partida, Time>, TableCell<Partida, Time>> fabricaDeCelulas = criarFabricaDeCelulasDeTime();
        colunaTimeA.setCellFactory(fabricaDeCelulas);
        colunaTimeB.setCellFactory(fabricaDeCelulas);
        colunaVs.setCellFactory(criarFabricaDeCelulasVs());

        tabela.getColumns().addAll(colunaTimeA, colunaVs, colunaTimeB);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return tabela;
    }

    @FXML
    void aoClicarJogarRodada() {
        System.out.println("TabelaController: Clicou Jogar Rodada.");
        this.isEditing = false;
        abrirJanelaVencedores(historicoDeRodadas.get(historicoDeRodadas.size() - 1));
    }

    @FXML
    void aoClicarEditarRodada() {
        System.out.println("TabelaController: Clicou Editar Rodada.");
        this.isEditing = true;
        abrirJanelaVencedores(historicoDeRodadas.get(historicoDeRodadas.size() - 1));
    }

    public void rodadaConcluida() {
        System.out.println("TabelaController: rodadaConcluida() chamada.");
        this.isEditing = false;
        atualizarVisualDaTabelaAtiva();
        botaoEditarRodada.setVisible(true);

        if (tabela.temCampeao()) {
            exibirCampeao();
        } else {
            // Se o torneio não acabou, prepara para a próxima rodada
            botaoJogarRodada.setText("Próxima Rodada");
            botaoJogarRodada.setOnAction(event -> avancarParaProximaRodada());
        }
    }

    public void atualizarVisualDaTabelaAtiva() {
        if (!containerDasRodadas.getChildren().isEmpty()) {
            Node ultimoNo = containerDasRodadas.getChildren().get(containerDasRodadas.getChildren().size() - 1);
            if (ultimoNo instanceof TableView) {
                ((TableView<Partida>) ultimoNo).refresh();
                System.out.println("TabelaController: Tabela ativa atualizada.");
            }
        }
    }

    public void habilitarBotaoDeEdicao() {
        this.botaoEditarRodada.setVisible(true);
    }

    private void desabilitarTabelaAnterior() {
        if (containerDasRodadas.getChildren().size() >= 2) {
            Node tabelaAnterior = containerDasRodadas.getChildren().get(containerDasRodadas.getChildren().size() - 1);
            Node tituloAnterior = containerDasRodadas.getChildren().get(containerDasRodadas.getChildren().size() - 2);
            tabelaAnterior.setDisable(true);
            tituloAnterior.setDisable(true);
        }
    }

    private void exibirCampeao() {
        System.out.println("TabelaController: Exibindo campeão. Campeão: " + (tabela.getCampeao() != null ? tabela.getCampeao().getNome() : "N/A"));
        atualizarVisualDaTabelaAtiva();
        desabilitarTabelaAnterior();

        // Altera a ação do botão para exibir estatísticas quando o torneio termina
        botaoJogarRodada.setText("Exibir Estatísticas");
        // O evento onAction do botão JogarRodada agora chamará aoClicarExibirEstatisticas
        botaoJogarRodada.setOnAction(event -> aoClicarExibirEstatisticas());
        botaoJogarRodada.setDisable(false); // Garante que o botão esteja habilitado
        botaoEditarRodada.setVisible(false);
    }

    // NOVO MÉTODO: Abre a tela de estatísticas
    @FXML
    private void aoClicarExibirEstatisticas() {
        System.out.println("TabelaController: Clicou Exibir Estatísticas. Abrindo estatistica-list.fxml.");
        try {
            // Caminho para o FXML da tela de estatísticas
            URL fxmlLocation = HelloApplication.class.getResource("/tech/w3ll/egameleague/telas/estatisticas/estatistica-list.fxml"); // Asumi que está aqui
            if (fxmlLocation == null) {
                System.err.println("Erro: FXML de estatísticas não encontrado: /tech/w3ll/egameleague/telas/estatisticas/estatistica-list.fxml");
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) botaoJogarRodada.getScene().getWindow(); // Reutiliza o Stage atual
            stage.setScene(new Scene(root));
            stage.setTitle("eGameLeague - Estatísticas do Torneio");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar a tela de estatísticas: " + e.getMessage());
        }
    }


    private void abrirJanelaVencedores(ObservableList<Partida> partidas) {
        System.out.println("TabelaController: Abrindo janela de vencedores para " + partidas.size() + " partidas.");
        if (partidas == null || partidas.isEmpty()) return;
        try {
            URL fxmlLocal = HelloApplication.class.getResource("/tech/w3ll/egameleague/telas/torneio/selecionar-vencedores-view.fxml");
            FXMLLoader carregador = new FXMLLoader(fxmlLocal);
            Parent raiz = carregador.load();

            SelecionarVencedoresController controlador = carregador.getController();
            controlador.carregarPartidas(partidas, this, this.isEditing, this.nomeDoTorneio);

            Stage janela = new Stage();
            janela.setTitle("Definir Vencedores da Rodada - " + nomeDoTorneio);
            janela.initModality(Modality.APPLICATION_MODAL);

            double alturaBase = 200.0;
            double alturaPorPartida = 100.0;
            double alturaCalculada = alturaBase + (partidas.size() * alturaPorPartida);
            double alturaMaxima = 700.0;

            double alturaFinal = Math.min(alturaCalculada, alturaMaxima);

            janela.setScene(new Scene(raiz, 450, alturaFinal));

            janela.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String gerarNomeDaRodada(int numeroDePartidas) {
        if (numeroDePartidas == 1) return "Final";
        if (numeroDePartidas == 2) return "Semifinais";
        if (numeroDePartidas <= 4) return "Quartas de Final";
        if (numeroDePartidas <= 8) return "Oitavas de Final";
        if (numeroDePartidas <= 16) return "16-avos de Final";
        return "Rodada " + this.rodadaAtualNumero;
    }

    private Callback<TableColumn<Partida, Time>, TableCell<Partida, Time>> criarFabricaDeCelulasDeTime() {
        return coluna -> new TableCell<>() {
            @Override
            protected void updateItem(Time item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    getStyleClass().removeAll("winner-cell", "loser-cell");
                } else {
                    setText(item.getNome());
                    Partida partida = getTableRow().getItem();
                    getStyleClass().removeAll("winner-cell", "loser-cell");
                    if (partida != null && partida.getVencedor() != null) {
                        if (partida.getVencedor().equals(item)) {
                            getStyleClass().add("winner-cell");
                        } else {
                            getStyleClass().add("loser-cell");
                        }
                    }
                }
            }
        };
    }

    private Callback<TableColumn<Partida, Void>, TableCell<Partida, Void>> criarFabricaDeCelulasVs() {
        return coluna -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    setText("x");
                }
            }
        };
    }

    private void ajustarAlturaDaTabela(TableView<Partida> tabela) {
        tabela.setFixedCellSize(ALTURA_DA_LINHA);
        Region cabecalho = (Region) tabela.lookup(".column-header-background");
        double alturaCabecalho = 45.0;
        if (cabecalho != null && cabecalho.isVisible()) {
            alturaCabecalho = cabecalho.getHeight();
        }
        int numeroDeLinhas = tabela.getItems().size();
        double alturaTotal = alturaCabecalho + (numeroDeLinhas * ALTURA_DA_LINHA) + 2;
        tabela.setPrefHeight(alturaTotal);
    }
}