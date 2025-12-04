package com.alexandria.view;

import com.alexandria.model.Gameplay.AshesOfAlexandriaGame;
import com.alexandria.model.Traversal.Room;
import com.alexandria.model.Player.Player;
import com.alexandria.controller.GameController;
import com.alexandria.model.Player.GameTimer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameFrame extends Application {

    private Player player;
    private LeftGamePanel left;
    private CenterGamePanel centerPanel;
    private RightGamePanel right;

    @Override
    public void start(Stage primaryStage) {
        left = new LeftGamePanel();
        centerPanel = new CenterGamePanel();
        right = new RightGamePanel();

        // Create the game model
        AshesOfAlexandriaGame gameModel = new AshesOfAlexandriaGame();

        // get the player from the game model
        player = gameModel.getPlayer();

        // Pass both into the controller
        GameController controller = new GameController(left, centerPanel, right, gameModel, player);
        controller.initUI();

        centerPanel.getOutputArea().appendText(gameModel.printWelcome());

        BorderPane root = new BorderPane();
        root.setLeft(left);
        root.setCenter(centerPanel);
        root.setRight(right);

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

        GameTimer timer = new GameTimer();
        timer.start(
            () -> endGame(), // what happens when time runs out
            countdown -> right.getTimerLabel().setText(countdown) // update the label each tick
        );
    }

    private void checkGameOver() {
        if (player.getHealth() <= 0) {
            centerPanel.getOutputArea().appendText("You have perished in the library. Game over.\n");
            endGame();
        } else if (player.hasItem("scroll of Eratosthenes")) {
            centerPanel.getOutputArea().appendText("Congratulations! You escaped with the master scroll!\n");
            endGame();
        }
    }

    private void endGame() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
