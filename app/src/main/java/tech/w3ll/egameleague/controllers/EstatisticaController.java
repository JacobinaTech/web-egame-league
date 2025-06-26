package tech.w3ll.egameleague.controllers;

import tech.w3ll.egameleague.modelos.Estatisticas;
import tech.w3ll.egameleague.repositores.listas.RepoListaEstatisticas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class EstatisticaController {

    @FXML private ComboBox<String> cbTorneio;
    @FXML private Button btnAplicar;
    @FXML private TableView<Estatisticas> tabela;
    @FXML private TableColumn<Estatisticas, String> colNome;
    @FXML private TableColumn<Estatisticas, Integer> colJogos;
    @FXML private TableColumn<Estatisticas, Integer> colVitorias;
    @FXML private TableColumn<Estatisticas, Integer> colDerrotas;
    @FXML private TableColumn<Estatisticas, Integer> colPontos;
    @FXML private TableColumn<Estatisticas, Integer> colRanking;
    @FXML private Label lblUltimaAtualizacao;

    private RepoListaEstatisticas repoEstatisticas = new RepoListaEstatisticas();

    @FXML
    public void initialize() {
        ObservableList<String> torneios = repoEstatisticas.listarTodos().stream()
                .map(Estatisticas::getNomeTorneio)
                .distinct()
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        cbTorneio.setItems(torneios);

        colNome.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getNomeJogadorOuEquipe()));
        colJogos.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getPartidasJogadas()).asObject());
        colVitorias.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getVitorias()).asObject());
        colDerrotas.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getDerrotas()).asObject());
        colPontos.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getPontos()).asObject());
        colRanking.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getRanking()).asObject());

        if (!torneios.isEmpty()) {
            cbTorneio.getSelectionModel().selectFirst();
        } else {
            tabela.setItems(FXCollections.observableArrayList());
        }

        atualizarUltimaAtualizacao();
    }

    @FXML
    public void aplicarFiltro() {
        String torneioSelecionado = cbTorneio.getValue();

        List<Estatisticas> listaFiltrada;
        if (torneioSelecionado == null || torneioSelecionado.isEmpty()) {
            listaFiltrada = repoEstatisticas.listarTodos();
        } else {
            listaFiltrada = repoEstatisticas.filtrarPorTorneio(torneioSelecionado);
        }
        tabela.setItems(FXCollections.observableArrayList(listaFiltrada));
        atualizarUltimaAtualizacao();
    }

    private void atualizarUltimaAtualizacao() {
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        lblUltimaAtualizacao.setText("Última atualização: " + dataHora);
    }
}