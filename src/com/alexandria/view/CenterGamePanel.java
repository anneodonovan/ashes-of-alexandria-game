package com.alexandria.view;

import com.alexandria.model.Traversal.Exit;
import com.alexandria.model.Traversal.Room;
import com.alexandria.model.Traversal.Direction;
import com.alexandria.model.NPC.DialogueOption;
import com.alexandria.view.ascii.AsciiCanvasPanel;
import com.alexandria.view.ascii.AsciiDemoPatterns;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ChoiceDialog;
import java.util.List;
import java.util.Optional;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import java.util.function.Consumer;

public class CenterGamePanel extends BorderPane {

    private TextArea outputArea;
    private TextArea inputField;
    private VBox dialogueOptionsBox;
    private AsciiCanvasPanel asciiPanel;
    private Button toggleAsciiButton;

    public CenterGamePanel() {
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);

        dialogueOptionsBox = new VBox();
        dialogueOptionsBox.setSpacing(10);
        dialogueOptionsBox.setVisible(false);

        inputField = new TextArea();
        inputField.setPrefHeight(100);
        inputField.setWrapText(true);

        asciiPanel = new AsciiCanvasPanel(60, 30);
        asciiPanel.setVisible(false);
        asciiPanel.setManaged(false);
        asciiPanel.setOnResize(this::redrawAsciiDemo);

        toggleAsciiButton = new Button("Toggle ASCII Mode");
        toggleAsciiButton.setOnAction(e -> toggleAsciiMode());

        StackPane centerStack = new StackPane(outputArea, asciiPanel);

        VBox bottomBox = new VBox(dialogueOptionsBox, inputField);
        bottomBox.setSpacing(5);

        setTop(toggleAsciiButton);
        setCenter(centerStack);
        setBottom(bottomBox);
    }

    private void toggleAsciiMode() {
        boolean showingAscii = asciiPanel.isVisible();
        asciiPanel.setVisible(!showingAscii);
        asciiPanel.setManaged(!showingAscii);
        outputArea.setVisible(showingAscii);
        outputArea.setManaged(showingAscii);

        if (!showingAscii) {
            redrawAsciiDemo();
        }
    }

    private void redrawAsciiDemo() {
        if (!asciiPanel.isVisible()) {
            return;
        }
        int cols = asciiPanel.getCols();
        int rows = asciiPanel.getRows();
        double[][] demo = AsciiDemoPatterns.radialGradient(cols, rows);
        asciiPanel.draw(demo, AsciiDemoPatterns.waterTint(cols, rows, demo));
    }

    public TextArea getOutputArea() {
        return outputArea;
    }

    public TextArea getInputField() {
        return inputField;
    }

    public VBox getDialogueOptionsBox() {
        return dialogueOptionsBox;
    }

    // method to show a choice dialog for exits
    public Optional<String> showExitChoiceDialog(String direction, List<String> labels) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(labels.get(0), labels);
        dialog.setTitle("Choose Exit");
        dialog.setHeaderText("Multiple exits going " + direction.toLowerCase());
        dialog.setContentText("Select which exit to take:");
        return dialog.showAndWait();
    }

    public void renderDialogueOptions(List<DialogueOption> options, Consumer<Integer> onChoice) {
        dialogueOptionsBox.getChildren().clear();
        dialogueOptionsBox.setVisible(true);

        for (int i = 0; i < options.size(); i++) {
            DialogueOption opt = options.get(i);
            Button btn = new Button(opt.getPlayerLine());
            final int choiceIndex = i + 1;
            btn.setOnAction(e -> onChoice.accept(choiceIndex));
            dialogueOptionsBox.getChildren().add(btn);
        }
    }

}
