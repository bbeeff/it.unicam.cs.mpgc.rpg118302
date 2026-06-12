package it.unicam.cs.mpgc.rpg118302.controllers;

import it.unicam.cs.mpgc.rpg118302.models.entities.Character;
import it.unicam.cs.mpgc.rpg118302.services.factory.ServiceFactory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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

    public void show() {
        VBox root = new VBox(0);
        root.setStyle("-fx-background-color: #0a0e27;");

        // HEADER
        VBox headerBox = new VBox(15);
        headerBox.setPadding(new Insets(40, 30, 40, 30));
        headerBox.setStyle("-fx-alignment: center; " +
                "-fx-background: linear-gradient(to bottom, #1a1a3e 0%, #2d1b4e 50%, #1a0f3e 100%);");

        // Top con versione a destra
        HBox topWithVersion = new HBox(0);
        topWithVersion.setStyle("-fx-alignment: center;");

        Label topDecoration = new Label("═══════════════════════════════════════════════════════");
        topDecoration.setStyle("-fx-font-size: 12px; -fx-text-fill: #d4af37; -fx-font-family: 'Courier New';");

        topWithVersion.getChildren().add(topDecoration);
        HBox.setHgrow(topWithVersion, Priority.ALWAYS);

        Label versionLabel = new Label(" PRIMA RELEASE v1.0");
        versionLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #d4af37; " +
                "-fx-padding: 5 15 5 15; -fx-border-color: #d4af37; -fx-border-width: 1; -fx-border-radius: 5;");

        HBox headerTop = new HBox(0);
        headerTop.setStyle("-fx-alignment: center-right;");
        headerTop.getChildren().add(versionLabel);

        Label mainTitle = new Label("⚔ DUNGEON LEGEND ⚔");
        mainTitle.setStyle("-fx-font-size: 44px; -fx-font-weight: bold; " +
                "-fx-text-fill: #d4af37; " +
                "-fx-effect: dropshadow(gaussian, #000000, 8, 0, 0, 2);");

        Label divider = new Label("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        divider.setStyle("-fx-font-size: 12px; -fx-text-fill: #8b7355; -fx-font-family: 'Courier New';");

        Label subtitle = new Label("«La battaglia ti attende...»");
        subtitle.setStyle("-fx-font-size: 16px; -fx-font-style: italic; -fx-text-fill: #c0a080;");

        Label bottomDecoration = new Label("═══════════════════════════════════════════════════════");
        bottomDecoration.setStyle("-fx-font-size: 12px; -fx-text-fill: #d4af37; -fx-font-family: 'Courier New';");

        headerBox.getChildren().addAll(headerTop, mainTitle, divider, subtitle, bottomDecoration);

        // CENTER - Main content con ScrollPane
        HBox mainContent = new HBox(40);
        mainContent.setPadding(new Insets(50, 50, 50, 50));
        mainContent.setStyle("-fx-background-color: #0a0e27; -fx-spacing: 40;");

        // PANNELLO SINISTRO - Statistiche del personaggio
        VBox statsPanel = new VBox(18);
        statsPanel.setStyle("-fx-padding: 25; " +
                "-fx-background-color: #1a1a3e; " +
                "-fx-border-color: #d4af37; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 10; " +
                "-fx-effect: dropshadow(gaussian, #d4af37, 12, 0.4, 0, 5);");
        statsPanel.setPrefWidth(250);

        Label statsTitle = new Label("👤 IL TUO EROE");
        statsTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #d4af37; -fx-letter-spacing: 2;");

        Label charName = new Label(currentCharacter.getName().toUpperCase());
        charName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #c0a080;");

        Label charGender = new Label("Genere: " + currentCharacter.getGender().getDisplayName());
        charGender.setStyle("-fx-font-size: 11px; -fx-text-fill: #a0a0a0;");

        Separator sep1 = new Separator();
        sep1.setStyle("-fx-border-color: #8b7355;");

        Label statLabel = new Label("STATISTICHE");
        statLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #d4af37; -fx-letter-spacing: 1;");

        String statsText = String.format(
                "⚔ Livello: %d\n" +
                        "❤ Vita: %d / %d\n" +
                        "🗡 Attacco: %d\n" +
                        "🛡 Difesa: %d\n" +
                        "🧪 Pozioni: %d",
                currentCharacter.getLevel(),
                currentCharacter.getCurrentHealth(),
                currentCharacter.getMaxHealth(),
                currentCharacter.getAttack(),
                currentCharacter.getDefense(),
                currentCharacter.getPotions()
        );

        Label stats = new Label(statsText);
        stats.setStyle("-fx-font-size: 11px; -fx-text-fill: #c0a080; " +
                "-fx-line-spacing: 8; -fx-font-family: 'Courier New';");

        Separator sep2 = new Separator();
        sep2.setStyle("-fx-border-color: #8b7355;");

        Label wisdom = new Label("«Ogni cicatrice racconta\nuna storia di sopravvivenza.»");
        wisdom.setStyle("-fx-font-size: 12px; -fx-text-fill: #a0a0a0; " +
                "-fx-font-style: italic; -fx-line-spacing: 5; -fx-wrap-text: true; -fx-text-alignment: center;");

        statsPanel.getChildren().addAll(statsTitle, charName, charGender, sep1, statLabel, stats, sep2, wisdom);

        // PANNELLO DESTRO - Azioni e narrative
        VBox actionPanel = new VBox(25);
        actionPanel.setStyle("-fx-spacing: 25;");

        // Card narrativa
        VBox narrativeCard = new VBox(12);
        narrativeCard.setStyle("-fx-padding: 20; " +
                "-fx-background-color: #1a0f3e; " +
                "-fx-border-color: #8b7355; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 8; " +
                "-fx-effect: dropshadow(gaussian, #000000, 8, 0, 0, 3);");

        Label narrativeTitle = new Label("📜 MISSIONE");
        narrativeTitle.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #d4af37;");

        Label narrative = new Label("Le profondità del dungeon ti richiamano.\n" +
                "Creature oscure abitano queste viscere.\n" +
                "Sei pronto a sfidare il destino?");
        narrative.setStyle("-fx-font-size: 14px; -fx-text-fill: #c0a080; " +
                "-fx-wrap-text: true; -fx-line-spacing: 8;");

        narrativeCard.getChildren().addAll(narrativeTitle, narrative);

        // Bottoni d'azione principali
        Button battleBtn = new Button("  ⚔ ENTRA IN BATTAGLIA ⚔  ");
        battleBtn.setStyle("-fx-padding: 16 40 16 40; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-color: linear-gradient(to right, #d4af37, #ffdb58); " +
                "-fx-text-fill: #1a0f3e; " +
                "-fx-border-radius: 8; " +
                "-fx-effect: dropshadow(gaussian, #d4af37, 10, 0.6, 0, 5); " +
                "-fx-cursor: hand;");
        battleBtn.setPrefWidth(250);
        battleBtn.setOnAction(e -> {
            if (onBattleStarted != null) {
                onBattleStarted.run();
            }
        });

        Button exitBtn = new Button("  ❌ ESCI DAL GIOCO  ");
        exitBtn.setStyle("-fx-padding: 14 40 14 40; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-color: #3a2a1a; " +
                "-fx-text-fill: #d4af37; " +
                "-fx-border-color: #8b7355; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 8; " +
                "-fx-cursor: hand;");
        exitBtn.setPrefWidth(250);
        exitBtn.setOnAction(e -> {
            if (onExit != null) {
                onExit.run();
            }
        });

        VBox mainButtonBox = new VBox(15);
        mainButtonBox.setStyle("-fx-alignment: center;");
        mainButtonBox.getChildren().addAll(battleBtn, exitBtn);

        // Bottoni per funzionalità future
        Label futureTitle = new Label("🔒 FUNZIONALITÀ PROSSIME VERSIONI");
        futureTitle.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #d4af37; " +
                "-fx-text-alignment: center;");

        HBox futureButtonsRow1 = new HBox(10);
        futureButtonsRow1.setStyle("-fx-alignment: center; -fx-spacing: 10;");

        Button equipBtn = createFutureButton("⚙ Equipaggiamento", "L'equipaggiamento sarà disponibile nella v1.1");
        Button shopBtn = createFutureButton("💰 Negozio", "Il negozio sarà disponibile nella v1.2");

        futureButtonsRow1.getChildren().addAll(equipBtn, shopBtn);

        HBox futureButtonsRow2 = new HBox(10);
        futureButtonsRow2.setStyle("-fx-alignment: center; -fx-spacing: 10;");

        Button abilityBtn = createFutureButton("✨ Abilità", "Le abilità saranno disponibili nella v1.3");
        Button mapBtn = createFutureButton("🗺 Mappa", "La mappa sarà disponibile nella v1.4");

        futureButtonsRow2.getChildren().addAll(abilityBtn, mapBtn);

        VBox futureBox = new VBox(15);
        futureBox.setStyle("-fx-padding: 15; -fx-alignment: center; " +
                "-fx-background-color: #1a0f3e; -fx-border-color: #8b7355; " +
                "-fx-border-width: 1; -fx-border-radius: 8; -fx-border-style: dashed;");
        futureBox.getChildren().addAll(futureTitle, futureButtonsRow1, futureButtonsRow2);

        actionPanel.getChildren().addAll(narrativeCard, mainButtonBox, futureBox);
        HBox.setHgrow(actionPanel, Priority.ALWAYS);

        mainContent.getChildren().addAll(statsPanel, actionPanel);

        // ScrollPane per responsività
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setStyle("-fx-padding: 0; -fx-control-inner-background: #0a0e27;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        root.getChildren().addAll(headerBox, scrollPane);

        Scene scene = new Scene(root, 1000, 750);
        stage.setScene(scene);
        stage.setTitle("Dungeon Legend - Menu Principale");
    }

    private Button createFutureButton(String text, String message) {
        Button btn = new Button(text);
        btn.setStyle("-fx-padding: 12 20 12 20; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-color: #3a2a1a; " +
                "-fx-text-fill: #c0a080; " +
                "-fx-border-color: #8b7355; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 5; " +
                "-fx-cursor: hand;");
        btn.setPrefWidth(140);
        btn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("⏳ Funzionalità Futura");
            alert.setHeaderText(text);
            alert.setContentText(message);
            alert.showAndWait();
        });
        return btn;
    }
}