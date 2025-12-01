package com.alexandria.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameFrame extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ashes of Alexandria");

        BorderPane root = new BorderPane();

        // Panels
        root.setLeft(new LeftGamePanel());
        root.setCenter(new CenterGamePanel());
        root.setRight(new RightGamePanel());

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
