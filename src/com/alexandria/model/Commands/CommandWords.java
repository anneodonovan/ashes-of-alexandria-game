package com.alexandria.model.Commands;

import java.util.HashMap;
import java.util.Map;

public class CommandWords {
    private Map<String, String> validCommands;

    public CommandWords() {
        validCommands = new HashMap<>();
        validCommands.put("go", "Move to another room");
        validCommands.put("quit", "End the game");
        validCommands.put("help", "Show help");
        validCommands.put("look", "Look around");
        validCommands.put("eat", "Eat something");
        validCommands.put("take", "Take something and add to inventory");
        validCommands.put("drop", "Drop something from inventory");
        validCommands.put("show", "Show inventory");
        validCommands.put("save", "Save player data");
        validCommands.put("reload", "Reload player data");
        validCommands.put("light", "Light a lightsource item");
        validCommands.put("unlock", "Unlock a door with a key");
        validCommands.put("read", "Read the contents of a scroll");
        validCommands.put("talk", "Talk to an NPC");
        validCommands.put("cast", "Cast a spell from a spellbook");
    }

    public boolean isCommand(String commandWord) {
        return validCommands.containsKey(commandWord);
    }

}
