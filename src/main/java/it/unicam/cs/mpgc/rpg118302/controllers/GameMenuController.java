package it.unicam.cs.mpgc.rpg118302.controllers;

import it.unicam.cs.mpgc.rpg118302.models.entities.Character;
import it.unicam.cs.mpgc.rpg118302.services.factory.ServiceFactory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller per il menu principale del gioco.
 */
public class GameMenuController {
    private Stage stage;
    private Character currentCharacter;
    private ServiceFactory serviceFactory;
    private Runnable onBattleStarted;
    private Runnable onExit;

    public GameMenuController(Stage stage, ServiceFactory serviceFactory) {
        this.stage = stage;
        this.serviceFactory = serviceFactory;
    }

    public void setCurrentCharacter(Character character) {
        this.currentCharacter = character;
    }

    public void setOnBattleStarted(Runnable callback) {
        this.onBattleStarted = callback;
    }

    public void setOnExit(Runnable callback) {
        this.onExit = callback;
    }

    /**
     * Visualizza il menu principale mostrando le statistiche del personaggio
     * e i pulsanti per iniziare una battaglia o uscire.
     */
    public void show() {
        BorderPane root = new BorderPane();
        VBox center = new VBox(15);
        center.setPadding(new Insets(30));

        Label gameTitle = new Label("Benvenuto nel Dungeon!");
        gameTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label charInfo = new Label(String.format("Personaggio: %s (%s) | Livello: %d | Vita: %d/%d | Attacco: %d | Pozioni: %d",
                currentCharacter.getName(), currentCharacter.getGender().getDisplayName(),
                currentCharacter.getLevel(),
                currentCharacter.getCurrentHealth(), currentCharacter.getMaxHealth(),
                currentCharacter.getAttack(),
                currentCharacter.getPotions()));

        Button battleBtn = new Button("Inizia Battaglia");
        battleBtn.setStyle("-fx-padding: 10px 30px; -fx-font-size: 14px;");
        battleBtn.setOnAction(e -> {
            if (onBattleStarted != null) {
                onBattleStarted.run();
            }
        });

        Button exitBtn = new Button("Esci");
        exitBtn.setStyle("-fx-padding: 10px 30px; -fx-font-size: 14px;");
        exitBtn.setOnAction(e -> {
            if (onExit != null) {
                onExit.run();
            }
        });

        center.getChildren().addAll(gameTitle, charInfo, battleBtn, exitBtn);
        root.setCenter(center);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Dungeon RPG - Menu Principale");
    }
}