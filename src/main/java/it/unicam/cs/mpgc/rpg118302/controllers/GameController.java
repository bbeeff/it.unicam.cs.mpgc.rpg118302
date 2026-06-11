package it.unicam.cs.mpgc.rpg118302.controllers;

import it.unicam.cs.mpgc.rpg118302.models.entities.Battle;
import it.unicam.cs.mpgc.rpg118302.models.entities.Character;
import it.unicam.cs.mpgc.rpg118302.models.enums.BattleAction;
import it.unicam.cs.mpgc.rpg118302.models.enums.BattleStatus;
import it.unicam.cs.mpgc.rpg118302.models.enums.Gender;
import it.unicam.cs.mpgc.rpg118302.services.factory.ServiceFactory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller principale per l'interfaccia grafica del Dungeon RPG.
 * Gestisce i tre schermi principali: creazione personaggio, menu principale e battaglia.
 */
public class GameController {
    private Stage stage;
    private Character currentCharacter;
    private Battle currentBattle;
    private ServiceFactory serviceFactory;

    /**
     * Costruttore che inizializza il controller con la factory dei servizi.
     */
    public GameController() {
        this.serviceFactory = ServiceFactory.getInstance();
    }

    /**
     * Avvia il controller mostrando lo schermo di creazione personaggio.
     * @param stage lo stage JavaFX principale
     */
    public void show(Stage stage) {
        this.stage = stage;
        showCharacterCreationScreen();
    }

    /**
     * Visualizza lo schermo di creazione del personaggio.
     * Permette al giocatore di inserire nome e scegliere il genere,
     * poi crea il personaggio e salva nel database.
     */
    private void showCharacterCreationScreen() {
        BorderPane root = new BorderPane();
        VBox center = new VBox(20);
        center.setPadding(new Insets(30));

        Label title = new Label("DUNGEON RPG");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Nome personaggio:");
        TextField nameField = new TextField();
        nameField.setPromptText("Inserisci il nome");

        Label genderLabel = new Label("Genere:");
        ComboBox<Gender> genderBox = new ComboBox<>();
        genderBox.getItems().addAll(Gender.values());
        genderBox.setStyle("-fx-font-size: 12px;");

        Button createBtn = new Button("Crea");
        createBtn.setStyle("-fx-padding: 10px 30px; -fx-font-size: 14px;");

        createBtn.setOnAction(e -> {
            String name = nameField.getText();
            Gender gender = genderBox.getValue();
            if (name.isEmpty() || gender == null) {
                showAlert("Errore", "Inserisci nome e genere");
                return;
            }
            try {
                currentCharacter = serviceFactory.getCharacterService().createCharacter(name, gender);
                showGameScreen();
            } catch (Exception ex) {
                showAlert("Errore", ex.getMessage());
            }
        });

        center.getChildren().addAll(title, nameLabel, nameField, genderLabel, genderBox, createBtn);
        root.setCenter(center);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Dungeon RPG - Creazione Personaggio");
        stage.show();
    }

    /**
     * Visualizza il menu principale del gioco.
     * Mostra le statistiche attuali del personaggio e i pulsanti per
     * iniziare una battaglia o uscire dal gioco.
     */
    private void showGameScreen() {
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
        battleBtn.setOnAction(e -> startBattle());

        Button exitBtn = new Button("Esci");
        exitBtn.setStyle("-fx-padding: 10px 30px; -fx-font-size: 14px;");
        exitBtn.setOnAction(e -> stage.close());

        center.getChildren().addAll(gameTitle, charInfo, battleBtn, exitBtn);
        root.setCenter(center);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Dungeon RPG - Menu Principale");
    }

    /**
     * Inizia una nuova battaglia creando un nemico e visualizzando
     * lo schermo di battaglia.
     */
    private void startBattle() {
        try {
            currentBattle = serviceFactory.getBattleService().initiateBattle(currentCharacter);
            showBattleScreen();
        } catch (Exception ex) {
            showAlert("Errore", ex.getMessage());
        }
    }

    /**
     * Visualizza lo schermo di battaglia con immagine del nemico,
     * statistiche, log della battaglia e pulsanti per le azioni.
     * Gestisce l'esecuzione delle azioni, l'aggiornamento delle statistiche
     * e la conclusione della battaglia con scelta di continuare o tornare al menu.
     */
    private void showBattleScreen() {
        BorderPane root = new BorderPane();
        VBox center = new VBox(15);
        center.setPadding(new Insets(30));

        Label battleTitle = new Label("BATTAGLIA");
        battleTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ImageView enemyImage = loadEnemyImage(currentBattle.getEnemy());
        enemyImage.setFitWidth(250);
        enemyImage.setFitHeight(250);
        enemyImage.setPreserveRatio(true);
        enemyImage.setSmooth(true);

        javafx.scene.layout.HBox imageContainer = new javafx.scene.layout.HBox();
        imageContainer.setStyle("-fx-alignment: center; -fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 10;");
        imageContainer.setPrefHeight(300);
        imageContainer.getChildren().add(enemyImage);

        TextArea battleLog = new TextArea();
        battleLog.setEditable(false);
        battleLog.setWrapText(true);
        battleLog.setPrefHeight(200);
        battleLog.setText("Battaglia iniziata!\n");

        Label charStats = new Label(String.format("%s - Vita: %d/%d",
                currentCharacter.getName(), currentCharacter.getCurrentHealth(), currentCharacter.getMaxHealth()));
        charStats.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

        Label enemyStats = new Label(String.format("%s - Vita: %d/%d",
                currentBattle.getEnemy().getName(), currentBattle.getEnemy().getCurrentHealth(),
                currentBattle.getEnemy().getMaxHealth()));
        enemyStats.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: red;");

        VBox actionBox = new VBox(10);
        actionBox.setPadding(new Insets(10));
        actionBox.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1;");

        for (BattleAction action : BattleAction.values()) {
            Button actionBtn = new Button(action.getDisplayName());
            actionBtn.setStyle("-fx-padding: 8px 20px;");
            actionBtn.setOnAction(e -> {
                try {
                    String result = serviceFactory.getBattleService().executeAction(currentBattle, action);
                    battleLog.appendText(result + "\n");

                    charStats.setText(String.format("%s - Vita: %d/%d",
                            currentCharacter.getName(), currentCharacter.getCurrentHealth(),
                            currentCharacter.getMaxHealth()));
                    enemyStats.setText(String.format("%s - Vita: %d/%d",
                            currentBattle.getEnemy().getName(), currentBattle.getEnemy().getCurrentHealth(),
                            currentBattle.getEnemy().getMaxHealth()));

                    if (currentBattle.getStatus() != BattleStatus.IN_PROGRESS) {
                        for (javafx.scene.Node node : actionBox.getChildren()) {
                            if (node instanceof Button) {
                                ((Button) node).setDisable(true);
                            }
                        }
                        battleLog.appendText("\n--- BATTAGLIA TERMINATA ---\n");

                        VBox endBattleBox = new VBox(10);
                        endBattleBox.setPadding(new Insets(10));

                        if (currentBattle.getStatus() == BattleStatus.PLAYER_WON) {
                            Label victoryMsg = new Label("Vuoi continuare a combattere?");
                            victoryMsg.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                            Button continueBtn = new Button("Continua a Combattere");
                            continueBtn.setStyle("-fx-padding: 10px 20px;");
                            continueBtn.setOnAction(e2 -> startBattle());

                            Button backBtn = new Button("Torna al Menu");
                            backBtn.setStyle("-fx-padding: 10px 20px;");
                            backBtn.setOnAction(e2 -> {
                                try {
                                    currentCharacter = serviceFactory.getCharacterService().getCharacter(currentCharacter.getId()).orElse(currentCharacter);
                                    showGameScreen();
                                } catch (Exception ex) {
                                    showAlert("Errore", ex.getMessage());
                                }
                            });

                            endBattleBox.getChildren().addAll(victoryMsg, continueBtn, backBtn);
                        } else {
                            Label defeatMsg = new Label("Sei stato sconfitto!");
                            defeatMsg.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: red;");

                            Button backBtn = new Button("Torna al Menu");
                            backBtn.setStyle("-fx-padding: 10px 20px;");
                            backBtn.setOnAction(e2 -> {
                                try {
                                    currentCharacter = serviceFactory.getCharacterService().getCharacter(currentCharacter.getId()).orElse(currentCharacter);
                                    showGameScreen();
                                } catch (Exception ex) {
                                    showAlert("Errore", ex.getMessage());
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

        center.getChildren().addAll(battleTitle, imageContainer, charStats, enemyStats, battleLog, actionBox);
        root.setCenter(center);

        Scene scene = new Scene(root, 700, 800);
        stage.setScene(scene);
        stage.setTitle("Dungeon RPG - Battaglia");
    }

    /**
     * Carica l'immagine del nemico dal percorso resources.
     * Se l'immagine non viene trovata, mostra un placeholder vuoto.
     * @param enemy il nemico di cui caricare l'immagine
     */
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

    /**
     * Mostra un alert informativo con titolo e messaggio.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}