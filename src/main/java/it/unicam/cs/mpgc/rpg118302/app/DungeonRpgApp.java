package it.unicam.cs.mpgc.rpg118302.app;

import it.unicam.cs.mpgc.rpg118302.config.JpaUtil;
import it.unicam.cs.mpgc.rpg118302.controllers.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Punto principale dell'applicazione per Dungeon RPG.
 */
public class DungeonRpgApp extends Application {
    private GameController gameController;

    @Override
    public void start(Stage primaryStage) {
        gameController = new GameController();
        gameController.show(primaryStage);

        primaryStage.setOnCloseRequest(e -> {
            JpaUtil.closeEntityManagerFactory();
            System.exit(0);
        });
    }

    @Override
    public void stop() {
        JpaUtil.closeEntityManagerFactory();
    }

    public static void main(String[] args) {
        launch(args);
    }
}