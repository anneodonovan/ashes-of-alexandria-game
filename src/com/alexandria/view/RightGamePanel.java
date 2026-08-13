package com.alexandria.view;

import com.alexandria.model.Inventory.Item;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RightGamePanel extends VBox {

    private static final int ICON_SIZE = 24;

    private Label scoreLabel;
    private ProgressBar healthBar;
    private ListView<Item> inventoryList;

    public RightGamePanel() {
        super(15);
        setPrefWidth(200);
        setPadding(new Insets(10));
        getStyleClass().add("pixel-panel");

        // Score
        Label scoreTitle = new Label("Score:");
        scoreTitle.getStyleClass().add("pixel-heading");
        scoreLabel = new Label("0");
        VBox scoreBox = new VBox(5, scoreTitle, scoreLabel);

        // Health
        Label healthTitle = new Label("Health:");
        healthTitle.getStyleClass().add("pixel-heading");
        healthBar = new ProgressBar(0.75);
        VBox healthBox = new VBox(5, healthTitle, healthBar);

        // Inventory
        Label inventoryTitle = new Label("Inventory:");
        inventoryTitle.getStyleClass().add("pixel-heading");
        inventoryList = new ListView<>();
        inventoryList.setCellFactory(list -> new ItemCell());
        inventoryList.setPrefHeight(200);
        VBox inventoryBox = new VBox(5, inventoryTitle, inventoryList);

        getChildren().addAll(scoreBox, healthBox, inventoryBox);
    }

    // Accessors for dynamic updates
    public Label getScoreLabel() { return scoreLabel; }
    public ProgressBar getHealthBar() { return healthBar; }
    public ListView<Item> getInventoryList() { return inventoryList; }

    private static class ItemCell extends ListCell<Item> {
        private final ImageView iconView = new ImageView();
        private final Label nameLabel = new Label();
        private final HBox root = new HBox(8, iconView, nameLabel);

        ItemCell() {
            iconView.setFitWidth(ICON_SIZE);
            iconView.setFitHeight(ICON_SIZE);
            iconView.setPreserveRatio(true);
            iconView.setSmooth(false);
        }

        @Override
        protected void updateItem(Item item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
                return;
            }
            Image icon = AssetManager.getItemIcon(item);
            iconView.setImage(icon);
            nameLabel.setText(item.getName());
            setGraphic(root);
        }
    }
}
