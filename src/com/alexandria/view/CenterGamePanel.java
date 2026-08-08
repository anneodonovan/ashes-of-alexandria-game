package com.alexandria.view;

import com.alexandria.model.Traversal.Exit;
import com.alexandria.model.Traversal.Room;
import com.alexandria.model.Traversal.Direction;
import com.alexandria.model.NPC.DialogueOption;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ChoiceDialog;
import java.util.List;
import java.util.Optional;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import java.util.function.Consumer;

public class CenterGamePanel extends BorderPane {

    private TextArea outputArea;
    private TextArea inputField;
    private VBox dialogueOptionsBox;

    public CenterGamePanel() {
        outputArea = new TextArea();
        outputArea.setPrefHeight(600);
        outputArea.setEditable(false);
        outputArea.setWrapText(true);

        dialogueOptionsBox = new VBox();
        dialogueOptionsBox.setSpacing(10);
        dialogueOptionsBox.setVisible(false);

        inputField = new TextArea();
        inputField.setPrefHeight(100);
        inputField.setWrapText(true);

        setTop(outputArea);
        setCenter(dialogueOptionsBox); // or setBottom(dialogueOptionsBox)
        setBottom(inputField);
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
