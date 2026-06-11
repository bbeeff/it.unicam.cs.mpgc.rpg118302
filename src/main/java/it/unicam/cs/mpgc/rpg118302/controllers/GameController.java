package it.unicam.cs.mpgc.rpg118302.controllers;

import it.unicam.cs.mpgc.rpg118302.models.entities.Character;
import it.unicam.cs.mpgc.rpg118302.services.factory.ServiceFactory;
import javafx.stage.Stage;

/**
 * Controller principale che orchestra i tre controller specializzati.
 * Gestisce il flusso di navigazione tra creazione personaggio, menu e battaglia.
 */
public class GameController {
    private Stage stage;
    private Character currentCharacter;
    private ServiceFactory serviceFactory;

    private CharacterCreationController characterCreationController;
    private GameMenuController gameMenuController;
    private BattleController battleController;

    public GameController() {
        this.serviceFactory = ServiceFactory.getInstance();
    }

    /**
     * Avvia il controller mostrando lo schermo di creazione personaggio.
     */
    public void show(Stage stage) {
        this.stage = stage;
        initializeControllers();
        showCharacterCreation();
    }

    private void initializeControllers() {
        characterCreationController = new CharacterCreationController(stage, serviceFactory);
        gameMenuController = new GameMenuController(stage, serviceFactory);
        battleController = new BattleController(stage, serviceFactory);

        characterCreationController.setOnCharacterCreated(() -> {
            currentCharacter = characterCreationController.getCreatedCharacter();
            if (currentCharacter != null) {
                showGameMenu();
            }
        });

        gameMenuController.setOnBattleStarted(this::startBattle);
        gameMenuController.setOnExit(() -> stage.close());

        battleController.setOnBattleContinue(this::startBattle);
        battleController.setOnBattleEnd(() -> {
            try {
                currentCharacter = serviceFactory.getCharacterService()
                        .getCharacter(currentCharacter.getId()).orElse(currentCharacter);
                showGameMenu();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void showCharacterCreation() {
        characterCreationController.show();
    }

    private void showGameMenu() {
        gameMenuController.setCurrentCharacter(currentCharacter);
        gameMenuController.show();
    }

    private void startBattle() {
        try {
            var battle = serviceFactory.getBattleService().initiateBattle(currentCharacter);
            battleController.setCurrentCharacter(currentCharacter);
            battleController.setCurrentBattle(battle);
            battleController.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}