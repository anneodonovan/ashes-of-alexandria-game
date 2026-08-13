package com.alexandria.controller;

import com.alexandria.model.Gameplay.AshesOfAlexandriaGame; 
import com.alexandria.model.Player.Player;
import com.alexandria.view.GameFrame;
import com.alexandria.view.CenterGamePanel;
import com.alexandria.view.SidebarPanel;
import com.alexandria.model.Commands.Command;
import com.alexandria.model.Commands.Parser;
import com.alexandria.model.Traversal.Direction;
import com.alexandria.model.Traversal.Exit;
import com.alexandria.model.Traversal.Room;
import com.alexandria.model.NPC.NPC;
import com.alexandria.model.NPC.DialogueLoader;
import com.alexandria.model.NPC.DialogueManager;
import com.alexandria.model.NPC.DialogueTree;
import com.alexandria.model.NPC.DialogueNode;
import com.alexandria.model.NPC.DialogueOption;
import com.alexandria.view.AssetManager;
import com.alexandria.view.ConfettiEffect;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import java.util.List;
import java.io.IOException;
import javafx.scene.layout.Pane;

public class GameController {
    //handles input from the view and updates the model accordingly
    //listens to events from GamePanel and updates the AshesOfAlexandriaGame instance

    private SidebarPanel sidebar;
    private CenterGamePanel centerPanel;
    private final Pane confettiPane;
    private AshesOfAlexandriaGame gameModel;
    private Player player;
    private Parser parser;
    private DialogueManager dialogueManager;
    private DialogueTree currentDialogueTree;
    private DialogueNode currentDialogueNode;
    private NPC currentNpc;
    private boolean inConversation = false;

    public GameController(SidebarPanel sidebar, CenterGamePanel centerPanel, AshesOfAlexandriaGame gameModel, Player player, Pane confettiPane) {
        this.sidebar = sidebar;
        this.centerPanel = centerPanel;
        this.gameModel = gameModel;
        this.player = player;
        this.parser = new Parser();
        this.confettiPane = confettiPane;

        initializeListeners();
    }

    public void initUI() {
        updateUI();
    }

    private void initializeListeners() {
        // Add action listeners to buttons in sidebar
        sidebar.getSaveButton().setOnAction(e -> saveGame()); // e = event
        sidebar.getReloadButton().setOnAction(e -> reloadGame());
        sidebar.getHelpButton().setOnAction(e -> help());
        sidebar.getQuitButton().setOnAction(e -> endGame());

        centerPanel.getInputField().setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER -> { // -> = lambda operator
                String inputText = centerPanel.getInputField().getText().trim();
                if (!inputText.isEmpty()) {
                    runCommand(inputText); // pass the STRING, not a Command
                }
                centerPanel.getInputField().clear();
                e.consume(); // prevent adding a new line - consume marks e as handled so listeners can ignore it
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

                centerPanel.setNpcPortrait(AssetManager.getNpcPortrait(npc.getDialogueFileName()));
                centerPanel.getOutputArea().appendText(out.toString());
                showDialogueOptions(currentDialogueNode, npc); // show buttons
            } else {
                centerPanel.getOutputArea().appendText("There is no " + npcName + " here to talk to.\n");
            }
        }
        checkGameOver();
        updateUI();
    }

    private void updateUI() {
        sidebar.getScoreLabel().setText(String.valueOf(player.getScore()));
        sidebar.getHealthBar().setProgress(player.getHealthPercent());
        sidebar.getInventoryList().getItems().setAll(player.getInventoryItems());
        centerPanel.setRoomArt(AssetManager.getRoomArt(player.getCurrentRoom().getName()));
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
            Exit chosen = exits.stream().filter(e -> e.getLabel().equalsIgnoreCase(label)).findFirst().orElse(null);
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
                centerPanel.clearNpcPortrait();
            }
            updateUI();
        });
    }


    private void saveGame() {
        try {
            player.savePlayerState();
            runCommand("save"); // just for output text

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Saved!");
            alert.showAndWait();

            updateUI(); // refresh GUI if needed
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save Failed");
            alert.setContentText("Could not save player state: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void reloadGame() {
        try {
            Player loaded = Player.reloadPlayerState(player.getName());
            this.player = loaded;
            gameModel.setPlayer(loaded);

            runCommand("reload"); //just for output text
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Reloaded!");
            alert.showAndWait();

            updateUI();  // refreshes GUI with new player state
        } catch (IOException | ClassNotFoundException e) {
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

    public void triggerWin() {
        ConfettiEffect effect = new ConfettiEffect(confettiPane);
        effect.playConfetti();
    }

    public void checkGameOver() {
        if (player.getHealth() <= 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Failure");
            alert.setHeaderText("Game Over!");
            String message = "You have perished in the library. Game over.\n The game window will close when you click ok";
            alert.setContentText(message);
            alert.showAndWait();
            endGame();
        } else if (player.hasItem("scroll of Eratosthenes")) {
            triggerWin();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Game Completed!");
            String message = "Congratulations! You escaped with the master scroll!\n The game window will close when you click ok";
            alert.setContentText(message);
            alert.showAndWait();
            endGame();
        }
    }
}