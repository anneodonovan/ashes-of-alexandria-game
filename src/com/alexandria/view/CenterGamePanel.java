package com.alexandria.view;

import com.alexandria.model.Traversal.Exit;
import com.alexandria.model.Traversal.Room;
import com.alexandria.model.Traversal.Direction;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ChoiceDialog;
import java.util.List;
import java.util.Optional;

public class CenterGamePanel extends BorderPane {

    private TextArea outputArea;
    private TextArea inputField;

    public CenterGamePanel() {
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);  

        inputField = new TextArea();
        inputField.setPrefHeight(100);
        inputField.setWrapText(true);

        setCenter(outputArea);
        setBottom(inputField);
    }

    public TextArea getOutputArea() {
        return outputArea;
    }

    public TextArea getInputField() {
        return inputField;
    }

    // method to show a choice dialog for exits
    public Optional<String> showExitChoiceDialog(String direction, List<String> labels) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(labels.get(0), labels);
        dialog.setTitle("Choose Exit");
        dialog.setHeaderText("Multiple exits going " + direction.toLowerCase());
        dialog.setContentText("Select which exit to take:");
        return dialog.showAndWait();
    }
}
