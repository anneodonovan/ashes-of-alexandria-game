package com.alexandria.model.NPC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogueTree {
    private Map<String, DialogueNode> nodeMap = new HashMap<>();

    // build the map from a list of nodes
    public DialogueTree(List<DialogueNode> nodes) {
        for (DialogueNode node : nodes) {
            nodeMap.put(node.getId(), node);
        }
    }

    // return the starting node
    public DialogueNode getStartNode() {
        return nodeMap.get("greeting"); 
    }

    public DialogueNode getNode(String id) {
        return nodeMap.get(id);
    }
}
