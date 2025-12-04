package com.alexandria.model.Commands;

import java.util.Scanner;

public class Parser {
    private final CommandWords commands;
    private Scanner reader;

    public Parser() {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    public Command getCommand() {
        String inputLine = getInput();
        if (inputLine != null) {
            inputLine = inputLine.trim();
        }
        return parse(inputLine);
    }

    public Command parse(String inputLine) {
        String word1 = null;
        String word2 = null;
        String word3 = null;

        if (inputLine == null) {
            return new Command(null, null, null);
        }

        Scanner tokenizer = new Scanner(inputLine);
        if (tokenizer.hasNext()) {
            word1 = tokenizer.next().toLowerCase();
            if (tokenizer.hasNext()) {
                word2 = tokenizer.next().toLowerCase();
                if (tokenizer.hasNext()) {
                    word3 = tokenizer.next().toLowerCase();
                }
            }
        }
        tokenizer.close();

        if (commands.isCommand(word1)) {
            return new Command(word1, word2, word3);
        } else{
            return new Command(null, word2, word3);
        }
    }

    public String showCommands() {
        String commands =
            "go - Move to another room\n" +
            "quit - End the game\n" +
            "help - Show help\n" +
            "look - Look around\n" +
            "eat - Eat something\n" +
            "take - Take something and add to inventory\n" +
            "drop - Drop something from inventory\n" +
            "show - Show inventory\n" +
            "save - Save player data\n" +
            "reload - Reload player data\n" +
            "light - Light a lightsource item\n" +
            "unlock - Unlock a door with a key\n" +
            "read - Read the contents of a scroll\n" +
            "talk - Talk to an NPC" +
            "cast - Cast a spell from a you inventory";
        return commands;
    }

    public String getInput() {
        System.out.print("> ");
        String inputLine = reader.nextLine();
        return inputLine;
    }
}
