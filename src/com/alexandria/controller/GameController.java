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
import com.alexandria.model.Traversal.Direction;
import com.alexandria.model.Traversal.Exit;
import com.alexandria.model.Traversal.Room;

import java.util.stream.Collectors;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import java.util.List;

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
                case ENTER -> {
                String inputText = centerPanel.getInputField().getText().trim();
                if (!inputText.isEmpty()) {
                    runCommand(inputText); // pass the STRING, not a Command
                }
                centerPanel.getInputField().clear();
                e.consume(); // prevent adding a new line
                }
            }
        });
    }

    private void runCommand(String inputText) {
        Command command = parser.parse(inputText); // parser gets the string
        String output = gameModel.processCommand(command); // processCommand returns text
        boolean needsChoice = output.contains("[CHOOSE_EXIT]");
        output = output.replace("[CHOOSE_EXIT]\n", "").replace("[CHOOSE_EXIT]", ""); //get rid of the marker

        centerPanel.getOutputArea().appendText("> " + inputText + "\n");
        centerPanel.getOutputArea().appendText(output);
        //code to deal with multiple exits
        if (needsChoice && "go".equals(command.getCommandWord()) && command.hasSecondWord()) {
            handleExitChoice(command.getSecondWord());
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

    private void handleExitChoice(String dirWord) {
        String dirUpper = dirWord.toUpperCase();
        Direction direction;
        try {
            direction = Direction.valueOf(dirUpper);
        } catch (Exception e) {
            return;
        }

        Room current = gameModel.getPlayer().getCurrentRoom();
        List<Exit> exits = current.getExits().stream().filter(e -> e.getDirectionFrom(current) == direction).toList();

        if (exits.size() <= 1) return;

        List<String> labels = exits.stream().map(Exit::getLabel).toList();

        Optional<String> result = centerPanel.showExitChoiceDialog(dirWord, labels);
        result.ifPresent(label -> {
            Exit chosen = exits.stream()
            .filter(e -> e.getLabel().equalsIgnoreCase(label))
            .findFirst()
            .orElse(null);

            if (chosen != null) {
                String text = gameModel.moveThroughExit(chosen); // or inline the “one exit” logic
                centerPanel.getOutputArea().appendText(text);
                updateUI();
            }
        });
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