package it.unicam.cs.mpgc.rpg118302.app;

import it.unicam.cs.mpgc.rpg118302.controllers.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Applicazione principale del Dungeon RPG.
 */
public class DungeonRpgApp extends Application {

    @Override
    public void start(Stage stage) {
        try {
            System.out.println("=== START APP ===");
            stage.setTitle("Dungeon RPG");
            stage.setWidth(800);
            stage.setHeight(600);
            stage.show();
            System.out.println("Stage creato e mostrato");

            GameController gameController = new GameController();
            System.out.println("GameController creato");

            gameController.show(stage);
            System.out.println("GameController.show() eseguito");
        } catch (Exception e) {
            System.err.println("ERRORE in start():");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== MAIN ===");
        launch(args);
    }
}