package com.alexandria.model.NPC;

public class DialogueOption {
    private String playerLine;
    private String nextNode; // The id of the next DialogueNode

    // Required no-args constructor for Gson
    public DialogueOption() {}

    public String getPlayerLine() {
        return playerLine;
    }

    public String getNextNode() {
        return nextNode;
    }
}
