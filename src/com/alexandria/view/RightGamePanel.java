package com.alexandria.view;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

public class RightGamePanel extends VBox {

    private Label scoreLabel;
    private ProgressBar healthBar;
    private ListView<String> inventoryList;

    public RightGamePanel() {
        super(15);
        setPrefWidth(200);
        setPadding(new Insets(10));

        // Score
        Label scoreTitle = new Label("Score:");
        scoreTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        scoreLabel = new Label("0");
        VBox scoreBox = new VBox(5, scoreTitle, scoreLabel);

        // Health
        Label healthTitle = new Label("Health:");
        healthTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        healthBar = new ProgressBar(0.75);
        VBox healthBox = new VBox(5, healthTitle, healthBar);

        // Inventory
        Label inventoryTitle = new Label("Inventory:");
        inventoryTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        inventoryList = new ListView<>();
        inventoryList.getItems().addAll();
        inventoryList.setPrefHeight(200);
        VBox inventoryBox = new VBox(5, inventoryTitle, inventoryList);

        getChildren().addAll(scoreBox, healthBox, inventoryBox);
    }

    // Accessors for dynamic updates
    public Label getScoreLabel() { return scoreLabel; }
    public ProgressBar getHealthBar() { return healthBar; }
    public ListView<String> getInventoryList() { return inventoryList; }
}
