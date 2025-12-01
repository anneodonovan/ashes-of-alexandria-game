package com.alexandria.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameFrame extends Application {

    @Override
    public void start(Stage primaryStage) {
        AshesOfAlexandriaGame gameModel = new AshesOfAlexandriaGame();
        Player player = gameModel.getPlayer();

        LeftGamePanel left = new LeftGamePanel();
        CenterGamePanel center = new CenterGamePanel();
        RightGamePanel right = new RightGamePanel();

        GameController controller = new GameController(left, center, right, gameModel, player);

        BorderPane root = new BorderPane();
        root.setLeft(left);
        root.setCenter(center);
        root.setRight(right);

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
