package com.alexandria.controller;

import com.alexandria.model.Gameplay.AshesOfAlexandriaGame; 
import com.alexandria.model.Player.Player;
import com.alexandria.view.GameFrame;
import com.alexandria.view.LeftGamePanel;
import com.alexandria.view.CenterGamePanel;
import com.alexandria.view.RightGamePanel;



public class GameController {
    //handles input from the view and updates the model accordingly
    //listens to swing events from GamePanel and updates the AshesOfAlexandriaGame instance

    private LeftGamePanel leftPanel;
    private CenterGamePanel centerPanel;
    private RightGamePanel rightPanel;
    private AshesOfAlexandriaGame gameModel;
    private Player player;

    public GameController(LeftGamePanel leftPanel, CenterGamePanel centerPanel, RightGamePanel rightPanel, AshesOfAlexandriaGame gameModel, Player player) {
        this.leftPanel = leftPanel;
        this.centerPanel = centerPanel;
        this.rightPanel = rightPanel;
        this.gameModel = gameModel;
        this.player = player;
        
        initializeListeners();
        updateUI();
    }

    private void initializeListeners() {
        // Add action listeners to buttons in leftPanel
        leftPanel.getPlayButton().setOnAction(e -> startGame());
        leftPanel.getSaveButton().setOnAction(e -> saveGame());
        leftPanel.getReloadButton().setOnAction(e -> reloadGame());
        leftPanel.getHelpButton().setOnAction(e -> help());
        leftPanel.getQuitButton().setOnAction(e -> endGame());

        centerPanel.getInputField().setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER -> processCommand(centerPanel.getInputField().getText());
            }
        });
    }

    public void processCommand(String command) {
        String result = gameModel.handleCommand(command);
        centerPanel.getOutputArea().appendText("> " + command + "\n" + result + "\n");
        updateUI();
    }

    private void updateUI() {
        rightPanel.getScoreLabel().setText(String.valueOf(player.getScore()));
        rightPanel.getHealthBar().setProgress(player.getHealthPercent());
        rightPanel.getInventoryList().getItems().setAll(player.getInventoryItems());
    }

    private void startGame() {
        // Logic to start the game
    }

    private void saveGame() {
        centerPanel.getOutputArea().appendText("Game saved!\n");
    }
    
    private void reloadGame() {
        centerPanel.getOutputArea().appendText("Game reloaded!\n");
    }

    private void help() {
        // Logic to show help
    }

    private void endGame() {
        System.exit(0);
    }
}