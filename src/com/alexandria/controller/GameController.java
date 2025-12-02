package com.alexandria.controller;

import com.alexandria.model.Gameplay.AshesOfAlexandriaGame; 
import com.alexandria.model.Player.Player;
import com.alexandria.view.GameFrame;
import com.alexandria.view.LeftGamePanel;
import com.alexandria.view.CenterGamePanel;
import com.alexandria.view.RightGamePanel;
import com.alexandria.model.Commands.Command;
import com.alexandria.model.Commands.Parser;
import com.alexandria.model.Inventory.Item;

import java.util.stream.Collectors;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

public class GameController {
    //handles input from the view and updates the model accordingly
    //listens to swing events from GamePanel and updates the AshesOfAlexandriaGame instance

    private LeftGamePanel leftPanel;
    private CenterGamePanel centerPanel;
    private RightGamePanel rightPanel;
    private AshesOfAlexandriaGame gameModel;
    private Player player;
    private Parser parser;

    public GameController(LeftGamePanel leftPanel, CenterGamePanel centerPanel, RightGamePanel rightPanel, AshesOfAlexandriaGame gameModel, Player player) {
        this.leftPanel = leftPanel;
        this.centerPanel = centerPanel;
        this.rightPanel = rightPanel;
        this.gameModel = gameModel;
        this.player = player;
        this.parser = new Parser();
        
        initializeListeners();
    }

    public void initUI() {
        updateUI();
    }

    private void initializeListeners() {
        // Add action listeners to buttons in leftPanel
        leftPanel.getSaveButton().setOnAction(e -> saveGame());
        leftPanel.getReloadButton().setOnAction(e -> reloadGame());
        leftPanel.getHelpButton().setOnAction(e -> help());
        leftPanel.getQuitButton().setOnAction(e -> endGame());

        centerPanel.getInputField().setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER -> handleCommand(parser.parse(centerPanel.getInputField().getText()));
            }
        });
    }

    public void handleCommand(Command command) {
        boolean finished = gameModel.processCommand(command);
        centerPanel.getOutputArea().appendText("> " + command + "\n");
        if (finished) {
            centerPanel.getOutputArea().appendText("Game ended.\n");
        }
        updateUI();
    }

    private void runCommand(String inputText) {
        Command command = parser.parse(inputText);
        boolean finished = gameModel.processCommand(command);
        centerPanel.getOutputArea().appendText("> " + inputText + "\n");
        if (finished) {
            centerPanel.getOutputArea().appendText("Game ended.\n");
        }
        updateUI();
    }

    private void updateUI() {
        rightPanel.getScoreLabel().setText(String.valueOf(player.getScore()));
        rightPanel.getHealthBar().setProgress(player.getHealthPercent());
        rightPanel.getInventoryList().getItems().setAll(
            player.getInventoryItems().stream().map(Item::getName).collect(Collectors.toList()) //look at this line
        );
    }

    public static String askPlayerName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Player Name");
        dialog.setHeaderText("Welcome to the Adventure!");
        dialog.setContentText("Please enter your name:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse("Player"); // fallback if user cancels
    }

    private void saveGame() {
        runCommand("save");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Saved!");
        alert.showAndWait();
    }
    
    private void reloadGame() {
        runCommand("reload");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Reloaded!");
        alert.showAndWait();
    }

    private void help() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("How to play");
        String message = "You are lost. You are alone. You wander around the library and it's grounds, in search of the master scroll." + "\nYour command words are: " + parser.showCommands();
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void endGame() {
        System.exit(0);
    }
}