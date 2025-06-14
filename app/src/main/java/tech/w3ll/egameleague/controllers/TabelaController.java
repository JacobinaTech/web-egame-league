package tech.w3ll.egameleague.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import tech.w3ll.egameleague.HelloApplication;
import tech.w3ll.egameleague.modelos.Partida;
import tech.w3ll.egameleague.modelos.Tabela;
import tech.w3ll.egameleague.modelos.Time;
import tech.w3ll.egameleague.repositores.listas.TimeRepo;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TabelaController {

    private enum Fase {
        INICIAL, SEMIFINAL, FINAL, CONCLUIDO
    }

    @FXML private TableView<Partida> tabelaDaRodadaInicial;
    @FXML private TableColumn<Partida, Time> colunaTimeA_Inicial;
    @FXML private TableColumn<Partida, Void> colunaVs_Inicial;
    @FXML private TableColumn<Partida, Time> colunaTimeB_Inicial;
    @FXML private HBox tituloPartidasIniciais;

    @FXML private TableView<Partida> tabelaDaSemifinal;
    @FXML private TableColumn<Partida, Time> colunaTimeA_Semifinal;
    @FXML private TableColumn<Partida, Void> colunaVs_Semifinal;
    @FXML private TableColumn<Partida, Time> colunaTimeB_Semifinal;
    @FXML private HBox tituloSemifinal;

    @FXML private TableView<Partida> tabelaDaFinal;
    @FXML private TableColumn<Partida, Time> colunaTimeA_Final;
    @FXML private TableColumn<Partida, Void> colunaVs_Final;
    @FXML private TableColumn<Partida, Time> colunaTimeB_Final;
    @FXML private HBox tituloFinal;

    @FXML private Button botaoJogarRodada;
    @FXML private Button botaoEditarRodada;

    private Tabela tabela;
    private Fase faseAtual;
    private static final double ALTURA_DA_LINHA = 48.0;
    private boolean isEditing = false;
    private ObservableList<Partida> partidasDaRodadaAnterior;

    @FXML
    public void initialize() {
        this.configurarTabelas();
        this.carregarDadosIniciais();
    }

    private void configurarTabelas() {
        this.tabelaDaRodadaInicial.getStyleClass().add("results-table-view");
        this.tabelaDaSemifinal.getStyleClass().add("results-table-view");
        this.tabelaDaFinal.getStyleClass().add("results-table-view");

        this.colunaTimeA_Inicial.setCellValueFactory(new PropertyValueFactory<>("time1"));
        this.colunaTimeB_Inicial.setCellValueFactory(new PropertyValueFactory<>("time2"));
        this.colunaTimeA_Semifinal.setCellValueFactory(new PropertyValueFactory<>("time1"));
        this.colunaTimeB_Semifinal.setCellValueFactory(new PropertyValueFactory<>("time2"));
        this.colunaTimeA_Final.setCellValueFactory(new PropertyValueFactory<>("time1"));
        this.colunaTimeB_Final.setCellValueFactory(new PropertyValueFactory<>("time2"));

        Callback<TableColumn<Partida, Time>, TableCell<Partida, Time>> fabricaDeCelulas = coluna -> new TableCell<>() {
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

        this.colunaTimeA_Inicial.setCellFactory(fabricaDeCelulas);
        this.colunaTimeB_Inicial.setCellFactory(fabricaDeCelulas);
        this.colunaTimeA_Semifinal.setCellFactory(fabricaDeCelulas);
        this.colunaTimeB_Semifinal.setCellFactory(fabricaDeCelulas);
        this.colunaTimeA_Final.setCellFactory(fabricaDeCelulas);
        this.colunaTimeB_Final.setCellFactory(fabricaDeCelulas);

        Callback<TableColumn<Partida, Void>, TableCell<Partida, Void>> fabricaDeCelulasVs = coluna -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText("x");
                    setStyle("-fx-text-fill: #b0b3c2;");
                }
            }
        };

        this.colunaVs_Inicial.setCellFactory(fabricaDeCelulasVs);
        this.colunaVs_Semifinal.setCellFactory(fabricaDeCelulasVs);
        this.colunaVs_Final.setCellFactory(fabricaDeCelulasVs);
    }

    private void ajustarVisibilidadeEAltura(TableView<Partida> tabela, HBox titulo) {
        boolean estaVazia = tabela.getItems() == null || tabela.getItems().isEmpty();
        tabela.setVisible(!estaVazia);
        tabela.setManaged(!estaVazia);
        titulo.setVisible(!estaVazia);
        titulo.setManaged(!estaVazia);

        tabela.prefHeightProperty().unbind();

        if (!estaVazia) {
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

    private void carregarDadosIniciais() {
        TimeRepo repositorioDeTimes = new TimeRepo();
        repositorioDeTimes.adicionar(new Time("Flamengo"));
        repositorioDeTimes.adicionar(new Time("Palmeiras"));
        repositorioDeTimes.adicionar(new Time("Corinthians"));
        repositorioDeTimes.adicionar(new Time("São Paulo"));
        repositorioDeTimes.adicionar(new Time("Grêmio"));
        repositorioDeTimes.adicionar(new Time("Internacional"));
        repositorioDeTimes.adicionar(new Time("Bahia"));
        repositorioDeTimes.adicionar(new Time("Fluminense"));

        this.tabela = new Tabela(new ArrayList<>(repositorioDeTimes.listarTodos()));
        this.tabela.gerarConfrontos();
        this.tabelaDaRodadaInicial.setItems(FXCollections.observableArrayList(this.tabela.getPartidasAtuais()));
        this.faseAtual = Fase.INICIAL;
        this.botaoJogarRodada.setText("Definir Vencedor");

        atualizarTodasAsTabelas();
    }

    private void abrirJanelaVencedores(ObservableList<Partida> partidas) {
        if (partidas == null || partidas.isEmpty()) {
            return;
        }
        try {
            URL fxmlLocal = HelloApplication.class.getResource("/tech/w3ll/egameleague/torneio/selecionar-vencedores-view.fxml");
            FXMLLoader carregador = new FXMLLoader(fxmlLocal);
            Parent raiz = carregador.load();

            SelecionarVencedoresController controlador = carregador.getController();
            controlador.carregarPartidas(partidas, this, this.isEditing);

            Stage janela = new Stage();
            janela.setTitle("Definir Vencedores da Rodada");
            janela.initModality(Modality.APPLICATION_MODAL);
            janela.setScene(new Scene(raiz));
            janela.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void atualizarTodasAsTabelas() {
        tabelaDaRodadaInicial.refresh();
        tabelaDaSemifinal.refresh();
        tabelaDaFinal.refresh();

        ajustarVisibilidadeEAltura(tabelaDaRodadaInicial, tituloPartidasIniciais);
        ajustarVisibilidadeEAltura(tabelaDaSemifinal, tituloSemifinal);
        ajustarVisibilidadeEAltura(tabelaDaFinal, tituloFinal);
    }

    private TableView<Partida> getTabelaAtiva() {
        switch (this.faseAtual) {
            case SEMIFINAL:
                return this.tabelaDaSemifinal;
            case FINAL:
            case CONCLUIDO:
                return this.tabelaDaFinal;
            case INICIAL:
            default:
                return this.tabelaDaRodadaInicial;
        }
    }

    public void atualizarVisualDaTabelaAtiva() {
        TableView<Partida> tabelaAtiva = getTabelaAtiva();
        if (tabelaAtiva != null) {
            tabelaAtiva.refresh();
        }
    }

    private TableView<Partida> getTabelaParaFase(Fase fase) {
        switch (fase) {
            case SEMIFINAL:
                return this.tabelaDaSemifinal;
            case FINAL:
                return this.tabelaDaFinal;
            default:
                return null;
        }
    }

    @FXML
    void aoClicarJogarRodada() {
        String textoDoBotao = this.botaoJogarRodada.getText();

        if (textoDoBotao.equals("Próximo")) {
            gerarProximaRodada();
        } else {
            this.isEditing = false;
            abrirJanelaVencedores(getTabelaAtiva().getItems());
        }
    }

    @FXML
    void aoClicarEditarRodada() {
        this.isEditing = true;
        abrirJanelaVencedores(getTabelaAtiva().getItems());
    }

    public void habilitarBotaoDeEdicao() {
        this.botaoEditarRodada.setVisible(true);
    }

    public void rodadaConcluida() {
        this.isEditing = false;
        atualizarTodasAsTabelas();
        this.botaoEditarRodada.setVisible(true);
        this.partidasDaRodadaAnterior = FXCollections.observableArrayList(getTabelaAtiva().getItems());

        if (this.faseAtual == Fase.FINAL) {
            this.botaoJogarRodada.setText("Fim de Jogo");
            this.botaoJogarRodada.setDisable(true);
            this.faseAtual = Fase.CONCLUIDO;
        } else {
            this.botaoJogarRodada.setText("Próximo");
        }
    }

    public void gerarProximaRodada() {
        if (this.faseAtual == Fase.INICIAL) {
            this.faseAtual = Fase.SEMIFINAL;
        } else if (this.faseAtual == Fase.SEMIFINAL) {
            this.faseAtual = Fase.FINAL;
        }

        this.tabela.avancarParaProximaFase(this.partidasDaRodadaAnterior);
        this.botaoEditarRodada.setVisible(false);
        this.tabela.gerarConfrontos();

        List<Partida> novasPartidas = this.tabela.getPartidasAtuais();
        ObservableList<Partida> proximasPartidas = FXCollections.observableArrayList(novasPartidas);

        TableView<Partida> proximaTabela = getTabelaParaFase(this.faseAtual);
        if (proximaTabela != null) {
            proximaTabela.setItems(proximasPartidas);
        }

        if (this.faseAtual == Fase.SEMIFINAL) {
            this.botaoJogarRodada.setText("Jogar Semifinal");
        } else if (this.faseAtual == Fase.FINAL) {
            this.botaoJogarRodada.setText("Jogar a Final");
        }

        atualizarTodasAsTabelas();
    }
}