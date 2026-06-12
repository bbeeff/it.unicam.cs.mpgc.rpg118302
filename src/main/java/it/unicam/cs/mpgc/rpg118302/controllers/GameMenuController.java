package it.unicam.cs.mpgc.rpg118302.controllers;

import it.unicam.cs.mpgc.rpg118302.models.entities.Character;
import it.unicam.cs.mpgc.rpg118302.services.factory.ServiceFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller per il menu principale del gioco.
 */
public class GameMenuController {
    private Stage stage;
    private Character currentCharacter;
    private ServiceFactory serviceFactory;
    private Runnable onBattleStarted;
    private Runnable onExit;

    @FXML
    private Label charName;

    @FXML
    private Label charGender;

    @FXML
    private Label charStats;

    @FXML
    private Button battleBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button equipBtn;

    @FXML
    private Button shopBtn;

    @FXML
    private Button abilityBtn;

    @FXML
    private Button mapBtn;

    // COSTRUTTORE NO-ARGS PER FXML
    public GameMenuController() {
    }

    // COSTRUTTORE CON PARAMETRI PER USAGE
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

    @FXML
    public void initialize() {
        updateCharacterStats();
    }

    private void updateCharacterStats() {
        if (currentCharacter != null) {
            charName.setText(currentCharacter.getName().toUpperCase());
            charGender.setText("Genere: " + currentCharacter.getGender().getDisplayName());
            charStats.setText(String.format(
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
            ));
        }
    }

    @FXML
    private void onBattleButtonClicked() {
        if (onBattleStarted != null) {
            onBattleStarted.run();
        }
    }

    @FXML
    private void onExitButtonClicked() {
        if (onExit != null) {
            onExit.run();
        }
    }

    @FXML
    private void onEquipButtonClicked() {
        showAlert("⏳ Funzionalità Futura", "⚙ Equipaggiamento", "L'equipaggiamento sarà disponibile nella v1.1");
    }

    @FXML
    private void onShopButtonClicked() {
        showAlert("⏳ Funzionalità Futura", "💰 Negozio", "Il negozio sarà disponibile nella v1.2");
    }

    @FXML
    private void onAbilityButtonClicked() {
        showAlert("⏳ Funzionalità Futura", "✨ Abilità", "Le abilità saranno disponibili nella v1.3");
    }

    @FXML
    private void onMapButtonClicked() {
        showAlert("⏳ Funzionalità Futura", "🗺 Mappa", "La mappa sarà disponibile nella v1.4");
    }

    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameMenu.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load(), 1000, 750);
            stage.setScene(scene);
            stage.setTitle("Dungeon Legend - Menu Principale");
            updateCharacterStats();
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