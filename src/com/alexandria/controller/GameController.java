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
import com.alexandria.model.NPC.NPC;
import com.alexandria.model.NPC.DialogueLoader;
import com.alexandria.model.NPC.DialogueManager;
import com.alexandria.model.NPC.DialogueTree;
import com.alexandria.model.NPC.DialogueNode;
import com.alexandria.model.NPC.DialogueOption;

import java.util.stream.Collectors;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import java.util.List;
import java.io.IOException;

public class GameController {
    //handles input from the view and updates the model accordingly
    //listens to swing events from GamePanel and updates the AshesOfAlexandriaGame instance

    private LeftGamePanel leftPanel;
    private CenterGamePanel centerPanel;
    private RightGamePanel rightPanel;
    private AshesOfAlexandriaGame gameModel;
    private Player player;
    private Parser parser;
    private DialogueManager dialogueManager;
    private DialogueTree currentDialogueTree;
    private DialogueNode currentDialogueNode;
    private NPC currentNpc;
    private boolean inConversation = false;

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

        //code to deal with NPC dialogue
        if ("talk".equals(command.getCommandWord()) && command.hasSecondWord()) {
            String npcName = command.getThirdWord();
            NPC npc = gameModel.talkToNPC(npcName);

            DialogueTree tree = DialogueLoader.loadDialogue(npcName);

            if (npc != null && tree != null) {
                dialogueManager = new DialogueManager();
                currentDialogueTree = tree;
                StringBuilder out = new StringBuilder();
                currentDialogueNode = dialogueManager.startDialogue(tree, player, npc, out);
                inConversation = true;

                centerPanel.getOutputArea().appendText(out.toString());
                showDialogueOptions(currentDialogueNode, npc); // show buttons
            } else {
                centerPanel.getOutputArea().appendText("There is no " + npcName + " here to talk to.\n");
            }
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

    private void showDialogueOptions(DialogueNode node, NPC npc) {
        List<DialogueOption> options = node.getOptions();
        if (options == null || options.isEmpty()) return;

        centerPanel.renderDialogueOptions(options, choiceIndex -> {
            StringBuilder out = new StringBuilder();
            currentDialogueNode = dialogueManager.chooseOption(currentDialogueTree, currentDialogueNode, choiceIndex, player, npc, out);
            centerPanel.getOutputArea().appendText(out.toString());

            if (currentDialogueNode != null) {
                showDialogueOptions(currentDialogueNode, npc);
            } else {
                inConversation = false;
                centerPanel.getDialogueOptionsBox().getChildren().clear();
                centerPanel.getDialogueOptionsBox().setVisible(false);
            }
            updateUI();
        });
    }


    private void saveGame() {
        runCommand("save");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Saved!");
        alert.showAndWait();
    }

    private void reloadGame() {
        try {
            Player loaded = Player.reloadPlayerState(player.getName());
            this.player = loaded;  

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Reloaded!");
            alert.showAndWait();

            updateUI();  // now uses the new player object
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Reload Failed");
            alert.setContentText("Could not reload player state.");
            alert.showAndWait();
        }
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