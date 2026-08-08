package com.alexandria.NPC;

import java.util.List;

public class DialogueNode {
    private String id;
    private String npcLine;
    private List<DialogueOption> options;

    // Required no-args constructor for Gson
    public DialogueNode() {}

    public String getId() {
        return id;
    }

    public String getNpcLine() {
        return npcLine;
    }

    public List<DialogueOption> getOptions() {
        return options;
    }
}
