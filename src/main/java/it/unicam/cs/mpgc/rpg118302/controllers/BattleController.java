package it.unicam.cs.mpgc.rpg118302.controllers;

import it.unicam.cs.mpgc.rpg118302.models.entities.Battle;
import it.unicam.cs.mpgc.rpg118302.models.entities.Character;
import it.unicam.cs.mpgc.rpg118302.models.enums.BattleAction;
import it.unicam.cs.mpgc.rpg118302.models.enums.BattleStatus;
import it.unicam.cs.mpgc.rpg118302.services.factory.ServiceFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller per lo schermo di battaglia.
 */
public class BattleController {
    private Stage stage;
    private Character currentCharacter;
    private Battle currentBattle;
    private ServiceFactory serviceFactory;
    private Runnable onBattleContinue;
    private Runnable onBattleEnd;

    @FXML
    private ImageView enemyImage;

    @FXML
    private Label charName;

    @FXML
    private Label charStats;

    @FXML
    private Label enemyName;

    @FXML
    private Label enemyStats;

    @FXML
    private TextArea battleLog;

    @FXML
    private HBox actionBox;

    // COSTRUTTORE NO-ARGS PER FXML
    public BattleController() {
    }

    // COSTRUTTORE CON PARAMETRI PER USAGE
    public BattleController(Stage stage, ServiceFactory serviceFactory) {
        this.stage = stage;
        this.serviceFactory = serviceFactory;
    }

    public void setCurrentCharacter(Character character) {
        this.currentCharacter = character;
    }

    public void setCurrentBattle(Battle battle) {
        this.currentBattle = battle;
    }

    public void setOnBattleContinue(Runnable callback) {
        this.onBattleContinue = callback;
    }

    public void setOnBattleEnd(Runnable callback) {
        this.onBattleEnd = callback;
    }

    @FXML
    public void initialize() {
        if (currentBattle != null) {
            loadEnemyImage();
            updateStats();
            createActionButtons();
            battleLog.setText("Battaglia iniziata!\n");
        }
    }

    private void loadEnemyImage() {
        String imagePath = "/images/" + currentBattle.getEnemy().getType().name().toLowerCase() + ".png";
        try {
            java.net.URL resourceUrl = getClass().getResource(imagePath);
            if (resourceUrl != null) {
                Image image = new Image(resourceUrl.toExternalForm());
                enemyImage.setImage(image);
            }
        } catch (Exception e) {
            System.err.println("Errore caricamento immagine: " + e.getMessage());
        }
    }

    private void updateStats() {
        charName.setText(currentCharacter.getName().toUpperCase());
        charStats.setText(String.format("❤ %d/%d",
                currentCharacter.getCurrentHealth(), currentCharacter.getMaxHealth()));

        enemyName.setText(currentBattle.getEnemy().getName().toUpperCase());
        enemyStats.setText(String.format("❤ %d/%d",
                currentBattle.getEnemy().getCurrentHealth(), currentBattle.getEnemy().getMaxHealth()));
    }

    private void createActionButtons() {
        actionBox.getChildren().clear();
        for (BattleAction action : BattleAction.values()) {
            Button btn = new Button(action.getDisplayName());
            btn.setStyle("-fx-padding: 10px 18px; -fx-font-size: 11px; -fx-font-weight: bold; " +
                    "-fx-background-color: #d4af37; -fx-text-fill: #1a0f3e; -fx-border-radius: 5;");
            btn.setPrefWidth(110);
            btn.setWrapText(true);
            btn.setOnAction(e -> onActionButtonClicked(action));
            actionBox.getChildren().add(btn);
        }
    }

    private void onActionButtonClicked(BattleAction action) {
        try {
            String result = serviceFactory.getBattleService().executeAction(currentBattle, action);
            battleLog.appendText(result + "\n");
            updateStats();

            if (currentBattle.getStatus() != BattleStatus.IN_PROGRESS) {
                disableActionButtons();
                battleLog.appendText("\n=== BATTAGLIA TERMINATA ===\n");
                showBattleEndScreen();
            }
        } catch (Exception ex) {
            showAlert("ERRORE", "Errore esecuzione azione", ex.getMessage());
        }
    }

    private void disableActionButtons() {
        for (javafx.scene.Node node : actionBox.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).setDisable(true);
            }
        }
    }

    private void showBattleEndScreen() {
        actionBox.getChildren().clear();

        VBox endBattleBox = new VBox(10);
        endBattleBox.setStyle("-fx-alignment: center; -fx-spacing: 10; -fx-padding: 10;");

        if (currentBattle.getStatus() == BattleStatus.PLAYER_WON) {
            Label victoryMsg = new Label("🎉 VITTORIA!");
            victoryMsg.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #2E7D32;");

            HBox endActionBox = new HBox(10);
            endActionBox.setStyle("-fx-alignment: center; -fx-spacing: 10;");

            Button continueBtn = new Button("Continua");
            continueBtn.setStyle("-fx-padding: 8px 20px; -fx-font-size: 11px; -fx-background-color: #4CAF50; " +
                    "-fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5;");
            continueBtn.setOnAction(e -> {
                if (onBattleContinue != null) {
                    onBattleContinue.run();
                }
            });

            Button backBtn = new Button("Menu");
            backBtn.setStyle("-fx-padding: 8px 20px; -fx-font-size: 11px; -fx-background-color: #d4af37; " +
                    "-fx-text-fill: #1a0f3e; -fx-font-weight: bold; -fx-border-radius: 5;");
            backBtn.setOnAction(e -> {
                if (onBattleEnd != null) {
                    onBattleEnd.run();
                }
            });

            endActionBox.getChildren().addAll(continueBtn, backBtn);
            endBattleBox.getChildren().addAll(victoryMsg, endActionBox);
        } else {
            Label defeatMsg = new Label("💀 SCONFITTA!");
            defeatMsg.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #C62828;");

            Button backBtn = new Button("Torna al Menu");
            backBtn.setStyle("-fx-padding: 8px 20px; -fx-font-size: 11px; -fx-background-color: #D32F2F; " +
                    "-fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5;");
            backBtn.setOnAction(e -> {
                if (onBattleEnd != null) {
                    onBattleEnd.run();
                }
            });

            endBattleBox.getChildren().addAll(defeatMsg, backBtn);
        }

        actionBox.getChildren().add(endBattleBox);
    }

    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Battle.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load(), 900, 800);
            stage.setScene(scene);
            stage.setTitle("Dungeon Legend - Battaglia");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("ERRORE", "Errore caricamento", "Impossibile caricare l'interfaccia: " + e.getMessage());
        }
    }

    private void showAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}