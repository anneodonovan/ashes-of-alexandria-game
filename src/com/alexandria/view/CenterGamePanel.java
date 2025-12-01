package com.alexandria.view;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class CenterGamePanel extends BorderPane {

    private TextArea outputArea;
    private TextArea inputField;

    public CenterGamePanel() {
        outputArea = new TextArea();
        outputArea.setEditable(false);

        inputField = new TextArea("> ");
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
}
