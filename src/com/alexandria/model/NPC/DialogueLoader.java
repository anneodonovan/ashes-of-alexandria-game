package com.alexandria.model.NPC;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class DialogueLoader {
    
    public static DialogueTree loadDialogue(String fileName) {
        Gson gson = new Gson();
        String resourcePath = "/dialogues/" + fileName + "_dialogue.json";

        try (InputStream in = DialogueLoader.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                System.err.println("Error loading dialogue file: " + resourcePath);
                return null;
            }

            Reader reader = new InputStreamReader(in);

            JsonObject json = gson.fromJson(reader, JsonObject.class);

            List<DialogueNode> nodes = gson.fromJson(
                json.get("nodes"), new TypeToken<List<DialogueNode>>(){}.getType()
            );

            return new DialogueTree(nodes);
        } catch (IOException e) {
            System.err.println("Error reading dialogue file: " + resourcePath);
            e.printStackTrace();
            return null;
        }
    }
}