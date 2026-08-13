package com.alexandria.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class LeftGamePanel extends VBox {
    private Button saveButton;
    private Button reloadButton;
    private Button helpButton;
    private Button quitButton;

    public LeftGamePanel() {
        super(20); // spacing
        setPrefWidth(150);
        setPadding(new Insets(20));
        setAlignment(Pos.TOP_CENTER);
        getStyleClass().add("pixel-panel");

        Label titleLabel = new Label("Control Panel");
        titleLabel.getStyleClass().add("pixel-heading");

        saveButton = new Button("Save");
        reloadButton = new Button("Reload");
        helpButton = new Button("Help");
        quitButton = new Button("Quit");
        for (Button b : new Button[] { saveButton, reloadButton, helpButton, quitButton }) {
            b.getStyleClass().add("pixel-button");
        }
        quitButton.getStyleClass().add("pixel-button-danger");

        getChildren().addAll(titleLabel, saveButton, reloadButton, helpButton, quitButton);
    }

    public Button getSaveButton() { return saveButton; }
    public Button getReloadButton() { return reloadButton; }
    public Button getHelpButton() { return helpButton; }
    public Button getQuitButton() { return quitButton; }
}
