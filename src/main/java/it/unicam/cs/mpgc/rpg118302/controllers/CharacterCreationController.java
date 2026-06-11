package it.unicam.cs.mpgc.rpg118302.controllers;

import it.unicam.cs.mpgc.rpg118302.models.entities.Character;
import it.unicam.cs.mpgc.rpg118302.models.enums.Gender;
import it.unicam.cs.mpgc.rpg118302.services.factory.ServiceFactory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
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

    /**
     * Imposta il callback da eseguire quando il personaggio è creato.
     */
    public void setOnCharacterCreated(Runnable callback) {
        this.onCharacterCreated = callback;
    }

    /**
     * Ottiene il personaggio appena creato.
     */
    public Character getCreatedCharacter() {
        return createdCharacter;
    }

    /**
     * Visualizza lo schermo di creazione del personaggio.
     * Permette al giocatore di inserire nome e scegliere il genere.
     */
    public void show() {
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
                this.createdCharacter = serviceFactory.getCharacterService().createCharacter(name, gender);
                if (onCharacterCreated != null) {
                    onCharacterCreated.run();
                }
            } catch (Exception ex) {
                showAlert("Errore", ex.getMessage());
            }
        });

        center.getChildren().addAll(title, nameLabel, nameField, genderLabel, genderBox, createBtn);
        root.setCenter(center);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Dungeon RPG - Creazione Personaggio");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}