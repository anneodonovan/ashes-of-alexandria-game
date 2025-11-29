package com.alexandria.model.NPC;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class DialogueLoader {
    /**
     * Loads a dialogue JSON file and returns a DialogueTree.
     * @param jsonFilePath The path to the JSON file (e.g. "resources/dialogue/guardian_library.json")
     * @return DialogueTree object, or null if something went wrong
     */
    public static DialogueTree loadDialogue(String jsonFilePath) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(jsonFilePath);
            
            // Read the whole JSON file as a JsonObject
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            // The "nodes" array contains all dialogue nodes, so deserialize it
            List<DialogueNode> nodes = gson.fromJson(
                json.get("nodes"),
                new TypeToken<List<DialogueNode>>(){}.getType()
            );

            reader.close();

            // Build and return a DialogueTree
            return new DialogueTree(nodes);
        } catch (IOException e) {
            System.err.println("Error loading dialogue file: " + jsonFilePath);
            e.printStackTrace();
            return null;
        }
    }
}

