package tech.w3ll.egameleague.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.IOException;


import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPopupEquipes  {
    @FXML
    private AnchorPane anchorPopupEquipes ;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField txtCabecalho;

    private void loadScreen(String fxmlPath) {
        try {
            // Carrega o FXML da tela filha
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node screen = loader.load();

            // Limpa o contentPane e adiciona a nova tela
            anchorPopupEquipes.getChildren().clear();
            anchorPopupEquipes.getChildren().add(screen);

            // Ajusta o conteúdo para preencher o espaço disponível
            AnchorPane.setTopAnchor(screen, 0.0);
            AnchorPane.setBottomAnchor(screen, 0.0);
            AnchorPane.setLeftAnchor(screen, 0.0);
            AnchorPane.setRightAnchor(screen, 0.0);

            // Opcional: ajusta o scroll para o topo
            scrollPane.setFitToWidth(true);
            scrollPane.setVvalue(0);
            scrollPane.setHvalue(0);

        } catch (IOException e) {
            e.printStackTrace();
            // Aqui você pode adicionar tratamento de erro adequado
        }
    }





}
