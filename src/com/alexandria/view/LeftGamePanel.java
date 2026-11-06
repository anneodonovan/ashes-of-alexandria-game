package com.alexandria.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class LeftGamePanel extends VBox {
    private Button saveButton;
    private Button reloadButton;
    private Button helpButton;
    private Button quitButton;
    private Button themeButton;
    private Tooltip themeTooltip;
    private SVGPath moonIcon;
    private SVGPath sunIcon;

    public LeftGamePanel() {
        super(20); // spacing
        setPrefWidth(150);
        setPadding(new Insets(20));
        setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("Control Panel");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        saveButton = new Button("Save");
        reloadButton = new Button("Reload");
        helpButton = new Button("Help");
        quitButton = new Button("Quit");
        quitButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        themeButton = new Button();
        themeTooltip = new Tooltip();
        themeButton.setTooltip(themeTooltip);
        moonIcon = icon("M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z");
        sunIcon = icon("M17 12a5 5 0 1 1-10 0a5 5 0 0 1 10 0z M12 1v2 M12 21v2 M4.22 4.22l1.42 1.42"
            + " M18.36 18.36l1.42 1.42 M1 12h2 M21 12h2 M4.22 19.78l1.42-1.42 M18.36 5.64l1.42-1.42");
        Theme.dark.addListener((obs, was, is) -> updateThemeButton(is));
        updateThemeButton(Theme.dark.get());

        getChildren().addAll(titleLabel, saveButton, reloadButton, helpButton, quitButton, themeButton);
    }

    private SVGPath icon(String path) {
        SVGPath icon = new SVGPath();
        icon.setContent(path);
        icon.setFill(null);
        icon.setStrokeWidth(2);
        icon.strokeProperty().bind(themeButton.textFillProperty());
        return icon;
    }

    private void updateThemeButton(boolean dark) {
        themeButton.setGraphic(dark ? sunIcon : moonIcon);
        themeTooltip.setText(dark ? "Switch to light mode" : "Switch to dark mode");
        themeButton.setAccessibleText(themeTooltip.getText());
    }

    public Button getSaveButton() { return saveButton; }
    public Button getReloadButton() { return reloadButton; }
    public Button getHelpButton() { return helpButton; }
    public Button getQuitButton() { return quitButton; }
    public Button getThemeButton() { return themeButton; }
}
