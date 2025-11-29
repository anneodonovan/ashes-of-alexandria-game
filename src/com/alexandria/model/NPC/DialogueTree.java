package com.alexandria.model.NPC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogueTree {
    private Map<String, DialogueNode> nodeMap = new HashMap<>();

    // Build the map from a list of nodes
    public DialogueTree(List<DialogueNode> nodes) {
        for (DialogueNode node : nodes) {
            nodeMap.put(node.getId(), node);
        }
    }

    // Return the starting node; usually "greeting"
    public DialogueNode getStartNode() {
        return nodeMap.get("greeting"); // or define another root id if needed
    }

    public DialogueNode getNode(String id) {
        return nodeMap.get(id);
    }
}
