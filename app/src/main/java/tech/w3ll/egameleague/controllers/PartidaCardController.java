package tech.w3ll.egameleague.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import tech.w3ll.egameleague.modelos.Partida;

public class PartidaCardController {

    @FXML
    private VBox cardContainer;
    @FXML
    private Label labelTime1;
    @FXML
    private Label labelTime2;
    @FXML
    private Label statusLabel;

    private Partida partida;
    private SelecionarVencedoresController parentController;

    public Partida getPartida() {
        return partida;
    }

    public void initData(Partida partida, SelecionarVencedoresController parentController) {
        this.partida = partida;
        this.parentController = parentController;
        updateUI();
    }

    public void updateUI() {
        labelTime1.getStyleClass().removeAll("winner", "loser");
        labelTime2.getStyleClass().removeAll("winner", "loser");
        cardContainer.getStyleClass().removeAll("loser");

        labelTime1.setText(partida.getTime1().getNome());
        if (partida.getTime2() != null) {
            labelTime2.setText(partida.getTime2().getNome());
            labelTime2.setVisible(true);
        } else {
            labelTime2.setText("");
            labelTime2.setVisible(false);
        }

        statusLabel.getStyleClass().removeAll("status-live", "status-finished");

        if (partida.getVencedor() != null) {
            statusLabel.setText("Encerrada");
            statusLabel.getStyleClass().add("status-finished");

            if (partida.getVencedor().equals(partida.getTime1())) {
                labelTime1.getStyleClass().add("winner");
                labelTime2.getStyleClass().add("loser");
            } else {
                labelTime2.getStyleClass().add("winner");
                labelTime1.getStyleClass().add("loser");
            }
            cardContainer.getStyleClass().add("loser");

        } else if (partida.getTime2() == null) {
            statusLabel.setText("AVANÇOU");
            statusLabel.getStyleClass().add("status-live");
        } else {
            statusLabel.setText("Aguardando");
        }
    }

    @FXML
    void handleTime1Click(MouseEvent event) {
        if (partida.getVencedor() == null) {
            parentController.onVencedorSelecionado(partida, partida.getTime1());
        }
        labelTime1.getStyleClass().add("selected-team");
        labelTime2.getStyleClass().remove("selected-team");
    }

    @FXML
    void handleTime2Click(MouseEvent event) {
        if (partida.getTime2() != null && partida.getVencedor() == null) {
            parentController.onVencedorSelecionado(partida, partida.getTime2());
        }
        labelTime2.getStyleClass().add("selected-team");
        labelTime1.getStyleClass().remove("selected-team");
    }
}