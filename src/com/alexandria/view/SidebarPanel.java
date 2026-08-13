package com.alexandria.view;

import com.alexandria.model.Inventory.Item;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

// Single side panel combining game controls (save/reload/help/quit) and player
// stats/inventory, replacing the previous separate LeftGamePanel/RightGamePanel.
public class SidebarPanel extends VBox {

    private static final int ICON_SIZE = 24;

    private Button saveButton;
    private Button reloadButton;
    private Button helpButton;
    private Button quitButton;
    private Label scoreLabel;
    private ProgressBar healthBar;
    private ListView<Item> inventoryList;

    public SidebarPanel() {
        super(15);
        setPrefWidth(200);
        setPadding(new Insets(10));
        getStyleClass().add("pixel-panel");

        Label titleLabel = new Label("Control Panel");
        titleLabel.getStyleClass().add("pixel-heading");

        saveButton = new Button("Save");
        reloadButton = new Button("Reload");
        helpButton = new Button("Help");
        quitButton = new Button("Quit");
        for (Button b : new Button[] { saveButton, reloadButton, helpButton, quitButton }) {
            b.getStyleClass().add("pixel-button");
            b.setMaxWidth(Double.MAX_VALUE);
        }
        quitButton.getStyleClass().add("pixel-button-danger");
        VBox controlsBox = new VBox(8, saveButton, reloadButton, helpButton, quitButton);

        Label scoreTitle = new Label("Score:");
        scoreTitle.getStyleClass().add("pixel-heading");
        scoreLabel = new Label("0");
        VBox scoreBox = new VBox(5, scoreTitle, scoreLabel);

        Label healthTitle = new Label("Health:");
        healthTitle.getStyleClass().add("pixel-heading");
        healthBar = new ProgressBar(0.75);
        VBox healthBox = new VBox(5, healthTitle, healthBar);

        Label inventoryTitle = new Label("Inventory:");
        inventoryTitle.getStyleClass().add("pixel-heading");
        inventoryList = new ListView<>();
        inventoryList.setCellFactory(list -> new ItemCell());
        inventoryList.setPrefHeight(200);
        VBox inventoryBox = new VBox(5, inventoryTitle, inventoryList);

        getChildren().addAll(titleLabel, controlsBox, scoreBox, healthBox, inventoryBox);
    }

    public Button getSaveButton() { return saveButton; }
    public Button getReloadButton() { return reloadButton; }
    public Button getHelpButton() { return helpButton; }
    public Button getQuitButton() { return quitButton; }
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
