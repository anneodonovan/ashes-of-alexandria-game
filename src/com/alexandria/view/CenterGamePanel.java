package com.alexandria.view;

import com.alexandria.model.Traversal.Exit;
import com.alexandria.model.Traversal.Room;
import com.alexandria.model.Traversal.Direction;
import com.alexandria.model.NPC.DialogueOption;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ChoiceDialog;
import java.util.List;
import java.util.Optional;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import java.util.function.Consumer;

public class CenterGamePanel extends BorderPane {

    private static final int ROOM_ART_WIDTH = 640;
    private static final int ROOM_ART_HEIGHT = 360;
    private static final int NPC_PORTRAIT_SIZE = 96;

    private TextArea outputArea;
    private TextArea inputField;
    private VBox dialogueOptionsBox;
    private ImageView roomArtView;
    private ImageView npcPortraitView;

    public CenterGamePanel() {
        roomArtView = new ImageView();
        roomArtView.setFitWidth(ROOM_ART_WIDTH);
        roomArtView.setFitHeight(ROOM_ART_HEIGHT);
        roomArtView.setPreserveRatio(true);
        roomArtView.setSmooth(false);
        roomArtView.getStyleClass().add("pixel-panel");
        HBox roomArtBox = new HBox(roomArtView);
        roomArtBox.setAlignment(Pos.CENTER);
        roomArtBox.setPadding(new Insets(10, 0, 10, 0));

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.getStyleClass().add("pixel-output");
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        npcPortraitView = new ImageView();
        npcPortraitView.setFitWidth(NPC_PORTRAIT_SIZE);
        npcPortraitView.setFitHeight(NPC_PORTRAIT_SIZE);
        npcPortraitView.setPreserveRatio(true);
        npcPortraitView.setSmooth(false);
        npcPortraitView.setVisible(false);

        dialogueOptionsBox = new VBox();
        dialogueOptionsBox.setSpacing(10);
        dialogueOptionsBox.setVisible(false);

        HBox dialogueBox = new HBox(10, npcPortraitView, dialogueOptionsBox);
        dialogueBox.setAlignment(Pos.CENTER_LEFT);

        VBox centerBox = new VBox(10, outputArea, dialogueBox);

        inputField = new TextArea();
        inputField.setPrefHeight(80);
        inputField.setWrapText(true);
        inputField.getStyleClass().add("pixel-input");

        setTop(roomArtBox);
        setCenter(centerBox);
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

    public void setRoomArt(Image image) {
        roomArtView.setImage(image);
    }

    public void setNpcPortrait(Image image) {
        npcPortraitView.setImage(image);
        npcPortraitView.setVisible(true);
    }

    public void clearNpcPortrait() {
        npcPortraitView.setImage(null);
        npcPortraitView.setVisible(false);
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
