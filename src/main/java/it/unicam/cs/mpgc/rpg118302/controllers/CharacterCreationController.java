package it.unicam.cs.mpgc.rpg118302.controllers;

import it.unicam.cs.mpgc.rpg118302.models.entities.Character;
import it.unicam.cs.mpgc.rpg118302.models.enums.Gender;
import it.unicam.cs.mpgc.rpg118302.services.factory.ServiceFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller per lo schermo di creazione del personaggio.
 */
public class CharacterCreationController {
    private Stage stage;
    private ServiceFactory serviceFactory;
    private Runnable onCharacterCreated;
    private Character createdCharacter;

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<Gender> genderBox;

    // COSTRUTTORE NO-ARGS PER FXML
    public CharacterCreationController() {
    }

    // COSTRUTTORE CON PARAMETRI PER USAGE
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

    @FXML
    public void initialize() {
        genderBox.getItems().addAll(Gender.values());
    }

    @FXML
    private void onCreateButtonClicked() {
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
    }

    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CharacterCreation.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load(), 1000, 750);
            stage.setScene(scene);
            stage.setTitle("Dungeon Legend - L'Inizio della Leggenda");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("ERRORE", "Impossibile caricare l'interfaccia: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}