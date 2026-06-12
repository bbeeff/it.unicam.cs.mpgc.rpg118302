package it.unicam.cs.mpgc.rpg118302.controllers;

import it.unicam.cs.mpgc.rpg118302.models.entities.Battle;
import it.unicam.cs.mpgc.rpg118302.models.entities.Character;
import it.unicam.cs.mpgc.rpg118302.models.enums.BattleAction;
import it.unicam.cs.mpgc.rpg118302.models.enums.BattleStatus;
import it.unicam.cs.mpgc.rpg118302.services.factory.ServiceFactory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

    public void show() {
        VBox root = new VBox(0);
        root.setStyle("-fx-background-color: #0a0e27;");

        // HEADER
        VBox headerBox = new VBox(15);
        headerBox.setPadding(new Insets(30, 30, 30, 30));
        headerBox.setStyle("-fx-alignment: center; " +
                "-fx-background: linear-gradient(to bottom, #1a1a3e 0%, #2d1b4e 50%, #1a0f3e 100%);");

        Label topDecoration = new Label("═══════════════════════════════════════════════════════");
        topDecoration.setStyle("-fx-font-size: 11px; -fx-text-fill: #d4af37; -fx-font-family: 'Courier New';");

        Label mainTitle = new Label("⚔ BATTAGLIA ⚔");
        mainTitle.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; " +
                "-fx-text-fill: #d4af37; " +
                "-fx-effect: dropshadow(gaussian, #000000, 8, 0, 0, 2);");

        Label bottomDecoration = new Label("═══════════════════════════════════════════════════════");
        bottomDecoration.setStyle("-fx-font-size: 11px; -fx-text-fill: #d4af37; -fx-font-family: 'Courier New';");

        headerBox.getChildren().addAll(topDecoration, mainTitle, bottomDecoration);

        // CENTER
        VBox center = new VBox(12);
        center.setPadding(new Insets(15));
        center.setStyle("-fx-spacing: 12; -fx-background-color: #0a0e27;");

        // SEZIONE IMMAGINE
        ImageView enemyImage = loadEnemyImage(currentBattle.getEnemy());
        enemyImage.setFitWidth(220);
        enemyImage.setFitHeight(220);
        enemyImage.setPreserveRatio(true);
        enemyImage.setSmooth(true);

        HBox imageContainer = new HBox();
        imageContainer.setStyle("-fx-alignment: center; -fx-border-color: #d4af37; -fx-border-width: 2; " +
                "-fx-padding: 15; -fx-background-color: #1a1a3e; -fx-border-radius: 8;");
        imageContainer.setPrefHeight(250);
        imageContainer.getChildren().add(enemyImage);

        // SEZIONE STATISTICHE
        HBox statsBox = new HBox(15);
        statsBox.setStyle("-fx-padding: 10; -fx-spacing: 15; -fx-alignment: center;");

        VBox playerStatsBox = new VBox(5);
        playerStatsBox.setStyle("-fx-alignment: center; -fx-padding: 12; -fx-border-color: #4CAF50; " +
                "-fx-border-width: 2; -fx-background-color: #e8f5e9; -fx-border-radius: 8;");
        playerStatsBox.setPrefWidth(150);

        Label charName = new Label(currentCharacter.getName().toUpperCase());
        charName.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1B5E20; -fx-wrap-text: true;");

        Label charStats = new Label(String.format("❤ %d/%d", currentCharacter.getCurrentHealth(), currentCharacter.getMaxHealth()));
        charStats.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #1565C0;");

        playerStatsBox.getChildren().addAll(charName, charStats);

        VBox enemyStatsBox = new VBox(5);
        enemyStatsBox.setStyle("-fx-alignment: center; -fx-padding: 12; -fx-border-color: #D32F2F; " +
                "-fx-border-width: 2; -fx-background-color: #ffebee; -fx-border-radius: 8;");
        enemyStatsBox.setPrefWidth(150);

        Label enemyName = new Label(currentBattle.getEnemy().getName().toUpperCase());
        enemyName.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #B71C1C; -fx-wrap-text: true;");

        Label enemyStats = new Label(String.format("❤ %d/%d", currentBattle.getEnemy().getCurrentHealth(), currentBattle.getEnemy().getMaxHealth()));
        enemyStats.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #D32F2F;");

        enemyStatsBox.getChildren().addAll(enemyName, enemyStats);

        statsBox.getChildren().addAll(playerStatsBox, enemyStatsBox);

        // SEZIONE LOG BATTAGLIA
        VBox battleLogContainer = new VBox(6);
        battleLogContainer.setStyle("-fx-border-color: #d4af37; -fx-border-width: 2; -fx-padding: 10; " +
                "-fx-background-color: #1a1a3e; -fx-border-radius: 8;");
        battleLogContainer.setPrefHeight(220);

        Label logTitle = new Label("📜 LOG BATTAGLIA");
        logTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #d4af37;");

        TextArea battleLog = new TextArea();
        battleLog.setEditable(false);
        battleLog.setWrapText(true);
        battleLog.setStyle("-fx-font-size: 11px; -fx-font-family: 'System'; -fx-control-inner-background: #0a0e27; " +
                "-fx-text-fill: #c0a080; -fx-padding: 8; -fx-border-color: #8b7355; -fx-border-width: 1;");
        battleLog.setText("Battaglia iniziata!\n");

        VBox.setVgrow(battleLog, Priority.ALWAYS);
        battleLogContainer.getChildren().addAll(logTitle, battleLog);

        // SEZIONE AZIONI
        VBox actionBoxContainer = new VBox(8);
        actionBoxContainer.setStyle("-fx-border-color: #d4af37; -fx-border-width: 2; -fx-padding: 10; " +
                "-fx-background-color: #1a1a3e; -fx-border-radius: 8;");

        HBox actionBox = new HBox(8);
        actionBox.setStyle("-fx-alignment: center; -fx-spacing: 8;");

        for (BattleAction action : BattleAction.values()) {
            Button actionBtn = new Button(action.getDisplayName());
            actionBtn.setStyle("-fx-padding: 10px 18px; -fx-font-size: 11px; -fx-font-weight: bold; " +
                    "-fx-background-color: #d4af37; -fx-text-fill: #1a0f3e; -fx-border-radius: 5;");
            actionBtn.setPrefWidth(110);
            actionBtn.setWrapText(true);
            actionBtn.setOnAction(e -> {
                try {
                    String result = serviceFactory.getBattleService().executeAction(currentBattle, action);
                    battleLog.appendText(result + "\n");

                    charName.setText(currentCharacter.getName().toUpperCase());
                    charStats.setText(String.format("❤ %d/%d",
                            currentCharacter.getCurrentHealth(), currentCharacter.getMaxHealth()));

                    enemyName.setText(currentBattle.getEnemy().getName().toUpperCase());
                    enemyStats.setText(String.format("❤ %d/%d",
                            currentBattle.getEnemy().getCurrentHealth(), currentBattle.getEnemy().getMaxHealth()));

                    if (currentBattle.getStatus() != BattleStatus.IN_PROGRESS) {
                        for (javafx.scene.Node node : actionBox.getChildren()) {
                            if (node instanceof Button) {
                                ((Button) node).setDisable(true);
                            }
                        }
                        battleLog.appendText("\n=== BATTAGLIA TERMINATA ===\n");

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
                            continueBtn.setOnAction(e2 -> {
                                if (onBattleContinue != null) {
                                    onBattleContinue.run();
                                }
                            });

                            Button backBtn = new Button("Menu");
                            backBtn.setStyle("-fx-padding: 8px 20px; -fx-font-size: 11px; -fx-background-color: #d4af37; " +
                                    "-fx-text-fill: #1a0f3e; -fx-font-weight: bold; -fx-border-radius: 5;");
                            backBtn.setOnAction(e2 -> {
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
                            backBtn.setOnAction(e2 -> {
                                if (onBattleEnd != null) {
                                    onBattleEnd.run();
                                }
                            });

                            endBattleBox.getChildren().addAll(defeatMsg, backBtn);
                        }

                        actionBox.getChildren().add(endBattleBox);
                    }
                } catch (Exception ex) {
                    showAlert("Errore", ex.getMessage());
                }
            });
            actionBox.getChildren().add(actionBtn);
        }

        actionBoxContainer.getChildren().add(actionBox);

        center.getChildren().addAll(imageContainer, statsBox, battleLogContainer, actionBoxContainer);
        VBox.setVgrow(battleLogContainer, Priority.ALWAYS);

        ScrollPane centerScroll = new ScrollPane(center);
        centerScroll.setFitToWidth(true);
        centerScroll.setStyle("-fx-padding: 0; -fx-control-inner-background: #0a0e27;");
        VBox.setVgrow(centerScroll, Priority.ALWAYS);

        root.getChildren().addAll(headerBox, centerScroll);

        Scene scene = new Scene(root, 900, 800);
        stage.setScene(scene);
        stage.setTitle("Dungeon Legend - Battaglia");
    }

    private ImageView loadEnemyImage(it.unicam.cs.mpgc.rpg118302.models.entities.Enemy enemy) {
        String imagePath = "/images/" + enemy.getType().name().toLowerCase() + ".png";
        System.out.println("Caricando immagine: " + imagePath);
        try {
            java.net.URL resourceUrl = getClass().getResource(imagePath);
            if (resourceUrl == null) {
                System.err.println("ERRORE: Immagine non trovata: " + imagePath);
                throw new Exception("Resource not found: " + imagePath);
            }
            Image image = new Image(resourceUrl.toExternalForm());
            System.out.println("Immagine caricata con successo!");
            return new ImageView(image);
        } catch (Exception e) {
            System.err.println("Errore caricamento immagine: " + e.getMessage());
            e.printStackTrace();
            ImageView placeholder = new ImageView();
            placeholder.setStyle("-fx-border-color: black; -fx-border-width: 2;");
            return placeholder;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}