package com.alexandria.NPC;

import java.util.List;
import java.util.Scanner;

public class DialogueManager {
    /**
     * Starts a conversation using the provided DialogueTree.
     * @param tree The DialogueTree loaded from JSON.
     * @param input Scanner for user input.
     */
    public void startDialogue(DialogueTree tree, Scanner input) {
        if (tree == null) {
            System.out.println("Dialogue could not be loaded or found.");
            return;
        }

        DialogueNode node = tree.getStartNode();
        while (node != null) {
            // Print NPC's line
            System.out.println("\nNPC: " + node.getNpcLine());

            List<DialogueOption> options = node.getOptions();
            if (options == null || options.isEmpty()) {
                break; // End of conversation
            }

            // Print player options
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i).getPlayerLine());
            }

            int choice = -1;
            // Input loop for a valid choice
            while (choice < 1 || choice > options.size()) {
                System.out.print("> ");
                try {
                    choice = Integer.parseInt(input.nextLine());
                } catch (NumberFormatException ex) {
                    choice = -1;
                }
            }

            // Go to the next node by nextNode id
            String nextNodeId = options.get(choice - 1).getNextNode();
            node = tree.getNode(nextNodeId);
        }
        System.out.println("Conversation ended.\n");
    }
}
