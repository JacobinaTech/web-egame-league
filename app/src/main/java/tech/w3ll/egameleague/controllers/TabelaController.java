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

    @FXML private TableView<Partida> tabelaRodadasIniciais;
    @FXML private TableColumn<Partida, Time> colTimeAInicial;
    @FXML private TableColumn<Partida, Void> colVsInicial;
    @FXML private TableColumn<Partida, Time> colTimeBInicial;

    @FXML private TableView<Partida> tabelaSemifinal;
    @FXML private TableColumn<Partida, Time> colTimeASemifinal;
    @FXML private TableColumn<Partida, Void> colVsSemifinal;
    @FXML private TableColumn<Partida, Time> colTimeBSemifinal;

    @FXML private TableView<Partida> tabelaFinal;
    @FXML private TableColumn<Partida, Time> colTimeAFinal;
    @FXML private TableColumn<Partida, Void> colVsFinal;
    @FXML private TableColumn<Partida, Time> colTimeBFinal;

    @FXML private Button btnJogarRodada;

    private Tabela tabela;

    @FXML
    public void initialize() {
        tabelaRodadasIniciais.getStyleClass().add("results-table-view");
        tabelaSemifinal.getStyleClass().add("results-table-view");
        tabelaFinal.getStyleClass().add("results-table-view");

        colTimeAInicial.setCellValueFactory(new PropertyValueFactory<>("time1"));
        colTimeBInicial.setCellValueFactory(new PropertyValueFactory<>("time2"));
        colTimeASemifinal.setCellValueFactory(new PropertyValueFactory<>("time1"));
        colTimeBSemifinal.setCellValueFactory(new PropertyValueFactory<>("time2"));
        colTimeAFinal.setCellValueFactory(new PropertyValueFactory<>("time1"));
        colTimeBFinal.setCellValueFactory(new PropertyValueFactory<>("time2"));

        Callback<TableColumn<Partida, Time>, TableCell<Partida, Time>> teamCellFactory = new Callback<>() {
            @Override
            public TableCell<Partida, Time> call(TableColumn<Partida, Time> col) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Time item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle(null);
                            getStyleClass().removeAll("winner-cell", "loser-cell");
                        } else {
                            setText(item.getNome());
                            Partida partida = getTableRow().getItem();
                            if (partida != null && partida.getVencedor() != null) {
                                getStyleClass().removeAll("winner-cell", "loser-cell");
                                if (partida.getVencedor().equals(item)) {
                                    getStyleClass().add("winner-cell");
                                } else {
                                    getStyleClass().add("loser-cell");
                                }
                            } else {
                                getStyleClass().removeAll("winner-cell", "loser-cell");
                            }
                        }
                    }
                };
            }
        };

        colTimeAInicial.setCellFactory(teamCellFactory);
        colTimeBInicial.setCellFactory(teamCellFactory);
        colTimeASemifinal.setCellFactory(teamCellFactory);
        colTimeBSemifinal.setCellFactory(teamCellFactory);
        colTimeAFinal.setCellFactory(teamCellFactory);
        colTimeBFinal.setCellFactory(teamCellFactory);

        Callback<TableColumn<Partida, Void>, TableCell<Partida, Void>> vsCellFactory = new Callback<>() {
            @Override
            public TableCell<Partida, Void> call(TableColumn<Partida, Void> col) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                            setText(null);
                        } else {
                            setText("x");
                            setStyle("-fx-font-size: 14px; -fx-text-fill: #777777;");
                        }
                    }
                };
            }
        };

        colVsInicial.setCellFactory(vsCellFactory);
        colVsSemifinal.setCellFactory(vsCellFactory);
        colVsFinal.setCellFactory(vsCellFactory);

        carregarDadosIniciais();
    }

    private void carregarDadosIniciais() {
        TimeRepo timeRepo = new TimeRepo();
        timeRepo.adicionar(new Time("Flamengo"));
        timeRepo.adicionar(new Time("Palmeiras"));
        timeRepo.adicionar(new Time("Corinthians"));
        timeRepo.adicionar(new Time("São Paulo"));
        timeRepo.adicionar(new Time("Grêmio"));
        timeRepo.adicionar(new Time("Internacional"));
        timeRepo.adicionar(new Time("Bahia"));
        timeRepo.adicionar(new Time("Fluminense"));

        tabela = new Tabela(new ArrayList<>(timeRepo.listarTodos()));

        tabela.gerarConfrontos();
        ObservableList<Partida> partidasIniciais = FXCollections.observableArrayList(tabela.getPartidasAtuais());
        tabelaRodadasIniciais.setItems(partidasIniciais);
    }

    @FXML
    void handleJogarRodada() {
        List<Partida> partidasDaRodada = tabela.getPartidasAtuais();
        if (partidasDaRodada == null) {
            System.out.println("Nenhuma partida para jogar.");
            return;
        }

        try {
            URL fxmlLocation = HelloApplication.class.getResource("/tech/w3ll/egameleague/torneio/selecionar-vencedores-view.fxml");
            if (fxmlLocation == null) {
                System.out.println("Nao achei o fxml de selecionar vencedores.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            SelecionarVencedoresController controller = loader.getController();
            controller.carregarPartidas(partidasDaRodada, this);

            Stage stage = new Stage();
            stage.setTitle("Definir Vencedores da Rodada");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            tabelaRodadasIniciais.refresh();
            tabelaSemifinal.refresh();
            tabelaFinal.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void avancarRodada() {
        tabelaRodadasIniciais.refresh();
        tabelaSemifinal.refresh();
        tabelaFinal.refresh();

        tabela.avancarParaProximaFase();

        if (tabela.temCampeao()) {
            btnJogarRodada.setDisable(true);
            tabelaFinal.refresh();
            System.out.println("Campeão: " + tabela.getCampeao().getNome());
            return;
        }

        tabela.gerarConfrontos();
        List<Partida> novasPartidas = tabela.getPartidasAtuais();

        if (tabelaSemifinal.getItems() == null || tabelaSemifinal.getItems().isEmpty()) {
            tabelaSemifinal.setItems(FXCollections.observableArrayList(novasPartidas));
        } else if (tabelaFinal.getItems() == null || tabelaFinal.getItems().isEmpty()) {
            tabelaFinal.setItems(FXCollections.observableArrayList(novasPartidas));
        }
    }
}