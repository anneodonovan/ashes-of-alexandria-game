package com.alexandria.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class LeftGamePanel extends VBox {

    public LeftGamePanel() {
        super(20); // spacing
        setPrefWidth(150);
        setPadding(new Insets(20));
        setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("Control Panel");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button playButton = new Button("Play");
        Button saveButton = new Button("Save");
        Button reloadButton = new Button("Reload");
        Button helpButton = new Button("Help");
        Button quitButton = new Button("Quit");
        quitButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        getChildren().addAll(titleLabel, playButton, saveButton, reloadButton, helpButton, quitButton);
    }
}
