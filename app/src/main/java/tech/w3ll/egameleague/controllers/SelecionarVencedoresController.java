package tech.w3ll.egameleague.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tech.w3ll.egameleague.HelloApplication;
import tech.w3ll.egameleague.modelos.Partida;
import tech.w3ll.egameleague.modelos.Time;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class SelecionarVencedoresController {

    @FXML
    private VBox containerDePartidas;

    private ObservableList<Partida> partidasDaRodada;
    private TabelaController tabelaController;

    public void carregarPartidas(List<Partida> partidas, TabelaController controller) {
        this.partidasDaRodada = FXCollections.observableArrayList(partidas);
        this.tabelaController = controller;

        containerDePartidas.getChildren().clear();

        for (Partida partida : partidasDaRodada) {
            try {
                URL fxmlLocation = HelloApplication.class.getResource("/tech/w3ll/egameleague/torneio/partida-card.fxml");
                if (fxmlLocation == null) {
                    System.out.println("Nao achei o fxml do card de partida.");
                    continue;
                }

                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                Parent cardNode = loader.load();
                PartidaCardController cardController = loader.getController();
                cardController.initData(partida, this);

                cardNode.setUserData(cardController);

                containerDePartidas.getChildren().add(cardNode);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onVencedorSelecionado(Partida partida, Time vencedor) {
        partida.definirVencedor(vencedor);

        for (javafx.scene.Node node : containerDePartidas.getChildren()) {
            if (node.getUserData() instanceof PartidaCardController) {
                PartidaCardController cardController = (PartidaCardController) node.getUserData();
                if (cardController.getPartida().equals(partida)) {
                    Platform.runLater(cardController::updateUI);
                    break;
                }
            }
        }

        boolean todasDefinidas = true;
        for (Partida p : partidasDaRodada) {
            if (p.getVencedor() == null && p.getTime2() != null) {
                todasDefinidas = false;
                break;
            }
        }

        if (todasDefinidas) {
            if (tabelaController != null) {
                tabelaController.avancarRodada();
            }
            Stage stage = (Stage) containerDePartidas.getScene().getWindow();
            stage.close();
        }
    }
}