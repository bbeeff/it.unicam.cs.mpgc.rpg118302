package it.unicam.cs.mpgc.rpg118302.controllers;

import it.unicam.cs.mpgc.rpg118302.models.entities.Character;
import it.unicam.cs.mpgc.rpg118302.models.enums.Gender;
import it.unicam.cs.mpgc.rpg118302.services.factory.ServiceFactory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller per lo schermo di creazione del personaggio.
 */
public class CharacterCreationController {
    private Stage stage;
    private ServiceFactory serviceFactory;
    private Runnable onCharacterCreated;
    private Character createdCharacter;

    public CharacterCreationController(Stage stage, ServiceFactory serviceFactory) {
        this.stage = stage;
        this.serviceFactory = serviceFactory;
    }

    public void setOnCharacterCreated(Runnable callback) {
        this.onCharacterCreated = callback;
    }

    public Character getCreatedCharacter() {
        return createdCharacter;
    }

    public void show() {
        VBox root = new VBox(0);
        root.setStyle("-fx-background-color: #0a0e27;");

        // HEADER SPETTACOLARE
        VBox headerBox = new VBox(20);
        headerBox.setPadding(new Insets(40, 30, 40, 30));
        headerBox.setStyle("-fx-alignment: center; " +
                "-fx-background: linear-gradient(to bottom, #1a1a3e 0%, #2d1b4e 50%, #1a0f3e 100%);");

        // Top con versione a destra
        HBox headerTop = new HBox(0);
        headerTop.setStyle("-fx-alignment: center-right;");

        Label versionLabel = new Label(" PRIMA RELEASE v1.0");
        versionLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #d4af37; " +
                "-fx-padding: 5 15 5 15; -fx-border-color: #d4af37; -fx-border-width: 1; -fx-border-radius: 5;");

        headerTop.getChildren().add(versionLabel);

        Label topDecoration = new Label("═══════════════════════════════════════════════════════");
        topDecoration.setStyle("-fx-font-size: 12px; -fx-text-fill: #d4af37; -fx-font-family: 'Courier New';");

        Label mainTitle = new Label("⚔ DUNGEON LEGEND ⚔");
        mainTitle.setStyle("-fx-font-size: 44px; -fx-font-weight: bold; " +
                "-fx-text-fill: #d4af37; " +
                "-fx-effect: dropshadow(gaussian, #000000, 8, 0, 0, 2);");

        Label divider = new Label("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        divider.setStyle("-fx-font-size: 12px; -fx-text-fill: #8b7355; -fx-font-family: 'Courier New';");

        Label subtitle = new Label("«Il tuo destino ti chiama...»");
        subtitle.setStyle("-fx-font-size: 16px; -fx-font-style: italic; -fx-text-fill: #c0a080;");

        Label description = new Label("Un'antica profezia parla di un eroe che sconfiggerà le creature oscure.\n" +
                "Sarai tu? Crea il tuo personaggio e sfida l'abisso.");
        description.setStyle("-fx-font-size: 12px; -fx-text-fill: #a0a0a0; " +
                "-fx-wrap-text: true; -fx-text-alignment: center; -fx-line-spacing: 5;");

        Label bottomDecoration = new Label("═══════════════════════════════════════════════════════");
        bottomDecoration.setStyle("-fx-font-size: 12px; -fx-text-fill: #d4af37; -fx-font-family: 'Courier New';");

        headerBox.getChildren().addAll(headerTop, topDecoration, mainTitle, divider, subtitle, description, bottomDecoration);

        // CENTER - Con ScrollPane per responsività
        VBox mainContent = new VBox(30);
        mainContent.setPadding(new Insets(40, 50, 50, 50));
        mainContent.setStyle("-fx-background-color: #0a0e27;");

        // COLONNA SINISTRA - Info narrativa
        VBox leftPanel = new VBox(15);
        leftPanel.setStyle("-fx-padding: 20; " +
                "-fx-background-color: #1a1a3e; " +
                "-fx-border-color: #8b7355; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 10; " +
                "-fx-effect: dropshadow(gaussian, #000000, 15, 0, 0, 5);");

        Label statsTitle = new Label("📖 LA TUA LEGGENDA");
        statsTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #d4af37;");

        Label stat1 = new Label("• Vita: 100\n• Attacco: 10\n• Difesa: 5\n• Pozioni: 3");
        stat1.setStyle("-fx-font-size: 11px; -fx-text-fill: #c0a080; -fx-line-spacing: 8;");

        Separator sep = new Separator();
        sep.setStyle("-fx-border-color: #8b7355;");

        Label wisdom = new Label("«Un eroe saggio sa che\nla forza non è tutto.\nLa strategia vince le battaglie.»");
        wisdom.setStyle("-fx-font-size: 11px; -fx-text-fill: #a0a0a0; " +
                "-fx-font-style: italic; -fx-line-spacing: 5; -fx-wrap-text: true;");

        leftPanel.getChildren().addAll(statsTitle, stat1, sep, wisdom);

        // COLONNA DESTRA - Form di creazione
        VBox rightPanel = new VBox(20);
        rightPanel.setStyle("-fx-spacing: 20;");

        // Sezione Nome
        VBox nameSection = new VBox(10);
        nameSection.setStyle("-fx-padding: 20; " +
                "-fx-border-color: #d4af37; " +
                "-fx-border-width: 2; " +
                "-fx-background-color: #1a0f3e; " +
                "-fx-border-radius: 8; " +
                "-fx-effect: dropshadow(gaussian, #d4af37, 8, 0.3, 0, 3);");

        Label nameLabel = new Label("👤 NOME DELL'EROE");
        nameLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #d4af37; -fx-letter-spacing: 2;");

        TextField nameField = new TextField();
        nameField.setPromptText("Scrivi il nome del tuo eroe...");
        nameField.setStyle("-fx-padding: 12; " +
                "-fx-font-size: 12px; " +
                "-fx-control-inner-background: #0a0e27; " +
                "-fx-text-fill: #d4af37; " +
                "-fx-prompt-text-fill: #666666; " +
                "-fx-border-color: #8b7355; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 5; " +
                "-fx-focus-color: #d4af37; " +
                "-fx-faint-focus-color: #d4af3722;");

        nameSection.getChildren().addAll(nameLabel, nameField);

        // Sezione Genere
        VBox genderSection = new VBox(10);
        genderSection.setStyle("-fx-padding: 20; " +
                "-fx-border-color: #d4af37; " +
                "-fx-border-width: 2; " +
                "-fx-background-color: #1a0f3e; " +
                "-fx-border-radius: 8; " +
                "-fx-effect: dropshadow(gaussian, #d4af37, 8, 0.3, 0, 3);");

        Label genderLabel = new Label("⚡ GENERE");
        genderLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #d4af37; -fx-letter-spacing: 2;");

        ComboBox<Gender> genderBox = new ComboBox<>();
        genderBox.getItems().addAll(Gender.values());
        genderBox.setStyle("-fx-padding: 10; " +
                "-fx-font-size: 12px; " +
                "-fx-control-inner-background: #0a0e27; " +
                "-fx-text-fill: #d4af37; " +
                "-fx-border-color: #8b7355; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 5;");

        genderSection.getChildren().addAll(genderLabel, genderBox);

        rightPanel.getChildren().addAll(nameSection, genderSection);
        HBox.setHgrow(rightPanel, Priority.ALWAYS);

        HBox contentLayout = new HBox(30);
        contentLayout.getChildren().addAll(leftPanel, rightPanel);
        HBox.setHgrow(rightPanel, Priority.ALWAYS);

        // Bottone Crea
        Button createBtn = new Button("  🗡 ENTRA NELL'ABISSO 🗡  ");
        createBtn.setStyle("-fx-padding: 18 60 18 60; " +
                "-fx-font-size: 15px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-color: linear-gradient(to right, #d4af37, #ffdb58); " +
                "-fx-text-fill: #1a0f3e; " +
                "-fx-border-radius: 8; " +
                "-fx-effect: dropshadow(gaussian, #d4af37, 10, 0.6, 0, 5); " +
                "-fx-cursor: hand;");

        createBtn.setOnAction(e -> {
            String name = nameField.getText();
            Gender gender = genderBox.getValue();
            if (name.isEmpty() || gender == null) {
                showAlert("⚠ AVVISO", "Inserisci nome e genere per continuare!");
                return;
            }
            try {
                this.createdCharacter = serviceFactory.getCharacterService().createCharacter(name, gender);
                if (onCharacterCreated != null) {
                    onCharacterCreated.run();
                }
            } catch (Exception ex) {
                showAlert("❌ ERRORE", ex.getMessage());
            }
        });

        VBox buttonBox = new VBox(0);
        buttonBox.setStyle("-fx-alignment: center;");
        buttonBox.getChildren().add(createBtn);

        mainContent.getChildren().addAll(contentLayout, buttonBox);

        // ScrollPane per responsività
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setStyle("-fx-padding: 0; -fx-control-inner-background: #0a0e27; -fx-fit-to-width: true;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        root.getChildren().addAll(headerBox, scrollPane);

        Scene scene = new Scene(root, 1000, 750);
        stage.setScene(scene);
        stage.setTitle("Dungeon Legend - L'Inizio della Leggenda");
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}