package com.alexandria.Commands;

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
    }

    public boolean isCommand(String commandWord) {
        return validCommands.containsKey(commandWord);
    }

    public void showAll() {
        //System.out.print("Valid commands are: ");
        for (String command : validCommands.keySet()) {
            System.out.print(command + " ");
        }
        System.out.println();
    }
}
