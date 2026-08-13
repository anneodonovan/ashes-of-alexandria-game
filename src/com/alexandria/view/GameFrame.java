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

    private Player player;
    private SidebarPanel sidebar;
    private CenterGamePanel centerPanel;

    @Override
    public void start(Stage primaryStage) {
        sidebar = new SidebarPanel();
        centerPanel = new CenterGamePanel();

        AshesOfAlexandriaGame gameModel = new AshesOfAlexandriaGame();
        player = gameModel.getPlayer();

        BorderPane root = new BorderPane();
        root.setCenter(centerPanel);
        root.setRight(sidebar);

        // confetti overlay
        Pane confettiPane = new Pane();
        confettiPane.setPickOnBounds(false); // don’t block clicks
        StackPane layeredRoot = new StackPane(root, confettiPane);

        GameController controller = new GameController(sidebar, centerPanel, gameModel, player, confettiPane);
        controller.initUI();

        centerPanel.getOutputArea().appendText(gameModel.printWelcome());

        layeredRoot.getStyleClass().add("pixel-root");

        Scene scene = new Scene(layeredRoot, 1200, 800);
        scene.getStylesheets().add(GameFrame.class.getResource("/css/pixel-theme.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
