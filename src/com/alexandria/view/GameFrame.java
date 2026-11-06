package com.alexandria.view;

import com.alexandria.model.Gameplay.AshesOfAlexandriaGame;
import com.alexandria.model.Traversal.Room;
import com.alexandria.model.Player.Player;
import com.alexandria.controller.GameController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameFrame extends Application {

    private static final String DARK_STYLE =
        "-fx-base: #1c1c1c; -fx-background: #121212; -fx-control-inner-background: #0d0d0d;"
        + " -fx-text-base-color: #f2f2f2; -fx-text-inner-color: #f2f2f2;"
        + " -fx-text-background-color: #f2f2f2; -fx-accent: #4a90e2;";

    private Player player;
    private LeftGamePanel left;
    private CenterGamePanel centerPanel;
    private RightGamePanel right;

    @Override
    public void start(Stage primaryStage) {
        left = new LeftGamePanel();
        centerPanel = new CenterGamePanel();
        right = new RightGamePanel();

        AshesOfAlexandriaGame gameModel = new AshesOfAlexandriaGame();
        player = gameModel.getPlayer();

        BorderPane root = new BorderPane();
        root.setLeft(left);
        root.setCenter(centerPanel);
        root.setRight(right);

        // confetti overlay
        Pane confettiPane = new Pane();
        confettiPane.setPickOnBounds(false); // don’t block clicks
        StackPane layeredRoot = new StackPane(root, confettiPane);

        GameController controller = new GameController(left, centerPanel, right, gameModel, player, confettiPane);
        controller.initUI();

        centerPanel.getOutputArea().appendText(gameModel.printWelcome());

        boolean[] dark = {false};
        left.getThemeButton().setOnAction(e -> {
            dark[0] = !dark[0];
            layeredRoot.setStyle(dark[0] ? DARK_STYLE : "");
            left.setDarkMode(dark[0]);
        });

        Scene scene = new Scene(layeredRoot, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
