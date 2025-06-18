package tech.w3ll.egameleague.controllers;

import tech.w3ll.egameleague.modelos.Historico;
import tech.w3ll.egameleague.repositores.listas.RepoListaHistorico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.application.Platform; // Importar Platform

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
// import java.util.Arrays; // Não é mais necessário se remover os dados de teste

public class HistoricoController {

    @FXML
    private Button botaoInicio;
    @FXML
    private Button botaoCriarT;
    @FXML
    private Button botaoEspectarT;

    @FXML
    private ComboBox<String> cbJogoHistorico;
    @FXML
    private TableView<Historico> tabelaHistorico;
    @FXML
    private TableColumn<Historico, String> colDataHora;
    @FXML
    private TableColumn<Historico, String> colTipoEvento;
    @FXML
    private TableColumn<Historico, Integer> colPontos;
    @FXML
    private TableColumn<Historico, String> colJogadores;
    @FXML
    private TableColumn<Historico, Integer> colPosicao;
    @FXML
    private TableColumn<Historico, String> colJogo;
    @FXML
    private Label lblUltimaAtualizacaoHistorico;

    private RepoListaHistorico repoHistorico = new RepoListaHistorico();

    @FXML
    public void initialize() {
        System.out.println("HistoricoController: initialize() chamado.");

        // --- REMOVIDO: DADOS DE TESTE TEMPORÁRIOS ---
        // A tabela agora será populada APENAS com dados do RepoListaHistorico,
        // que devem vir do SelecionarVencedoresController.
        System.out.println("HistoricoController: Repositório de histórico contém: " + repoHistorico.listarTodos().size() + " itens.");


        // Configura as CellValueFactory
        colDataHora.setCellValueFactory(cell -> {
            String formattedDate = cell.getValue().getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            return new javafx.beans.property.SimpleStringProperty(formattedDate);
        });
        colTipoEvento.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTipoEvento()));
        colPontos.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getPontos()).asObject());
        colJogadores.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(
                String.join(", ", cell.getValue().getJogadores())
        ));
        colPosicao.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getPosicaoJogador()).asObject());
        colJogo.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getNomeJogo()));

        // Popula a TableView e o ComboBox
        // Usar Platform.runLater para garantir que a atualização da UI ocorra na thread da aplicação JavaFX
        Platform.runLater(() -> {
            ObservableList<Historico> dadosHistorico = FXCollections.observableArrayList(repoHistorico.listarTodos());
            System.out.println("HistoricoController: Dados a serem exibidos na tabela (após observable): " + dadosHistorico.size());
            tabelaHistorico.setItems(dadosHistorico);
            tabelaHistorico.refresh(); // Força a atualização da tabela, crucial para dados dinâmicos

            ObservableList<String> jogos = dadosHistorico.stream()
                    .map(Historico::getNomeJogo)
                    .distinct()
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            cbJogoHistorico.setItems(jogos);
            System.out.println("HistoricoController: Jogos no ComboBox: " + jogos.size());
        });

        atualizarUltimaAtualizacao();
        System.out.println("HistoricoController: initialize() finalizado.");
    }

    @FXML
    public void aplicarFiltroHistorico() {
        System.out.println("HistoricoController: aplicando filtro.");
        String jogoSelecionado = cbJogoHistorico.getValue();

        List<Historico> listaFiltrada;
        if (jogoSelecionado == null || jogoSelecionado.isEmpty()) {
            listaFiltrada = repoHistorico.listarTodos();
            System.out.println("HistoricoController: Filtro desativado. Exibindo todos os " + listaFiltrada.size() + " itens.");
        } else {
            listaFiltrada = repoHistorico.filtrarPorJogo(jogoSelecionado);
            System.out.println("HistoricoController: Filtrando por jogo '" + jogoSelecionado + "'. Encontrados: " + listaFiltrada.size() + " itens.");
        }
        Platform.runLater(() -> {
            tabelaHistorico.setItems(FXCollections.observableArrayList(listaFiltrada));
            tabelaHistorico.refresh(); // Força a atualização da tabela
        });
        atualizarUltimaAtualizacao();
    }

    private void atualizarUltimaAtualizacao() {
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        lblUltimaAtualizacaoHistorico.setText("Última atualização: " + dataHora);
    }

    @FXML
    private void irParaInicio() throws IOException {
        System.out.println("HistoricoController: Navegando para Início.");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tech/w3ll/egameleague/telas/TelaMenu.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) botaoInicio.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("eGameLeague - Menu Principal");
        stage.show();
    }

    @FXML
    private void abrirTelaCriarTorneio() throws IOException {
        System.out.println("HistoricoController: Abrindo tela Criar Torneio.");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tech/w3ll/egameleague/telas/torneio/LayoutTabela.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) botaoCriarT.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("eGameLeague - Criar Torneio");
        stage.show();
    }

    @FXML
    private void abrirTelaEspectarTorneio() throws IOException {
        System.out.println("HistoricoController: Abrindo tela Espectar Torneio.");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tech/w3ll/egameleague/telas/TelaEspectarTorneio.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) botaoEspectarT.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("eGameLeague - Espectar Torneio");
        stage.show();
    }
}