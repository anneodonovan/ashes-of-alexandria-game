package com.alexandria.NPC;

import com.alexandria.NPC.DialogueLoader;
import com.alexandria.Player.Player;
import com.alexandria.Gameplay.AshesOfAlexandriaGame;
import com.alexandria.Inventory.Item;

import java.util.List;
import java.util.Scanner;

public class DialogueManager {
    /**
     * Starts a conversation using the provided DialogueTree.
     * @param tree The DialogueTree loaded from JSON.
     * @param input Scanner for user input.
     */
    public void startDialogue(DialogueTree tree, Scanner input, Player player, NPC npc) {
        if (tree == null) {
            System.out.println("Dialogue could not be loaded or found.");
            return;
        }

        DialogueNode node = tree.getStartNode();
        while (node != null) {
            // Print NPC's line
            System.out.println("\n" + npc.getName() + ": " + node.getNpcLine());

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

            if (node != null) {
                applyNodeEffects(node, player, npc);
            }            
        }
        System.out.println("Interaction ended\n");
    }

    private void applyNodeEffects(DialogueNode node, Player player, NPC npc) {
        String id = node.getId();

        switch (id) {
            case "fail1":
                player.adjustHealth(-10); // or setHealth(getHealth() - 10)
                System.out.println("You feel seared by the flame. (-10 HP)");
                break;
            case "fail2":
                player.adjustHealth(-15);
                System.out.println("The flame lashes out at you. (-15 HP)");
                break;
            case "fail3":
                player.adjustHealth(-20);
                System.out.println("Your spirit is scorched. (-20 HP)");
                break;
            case "attack":
                player.adjustHealth(-30);
                System.out.println("The Flamewatcher’s wrath burns you. (-30 HP)");
                break;
            case "success":
                List<Item> items = npc.getItems();
                if (items.isEmpty()) {
                    System.out.println(npc.getName() + " has no items to give.");
                    break;
                } else {
                    String itemName = items.get(0).getName(); //this line needs to be there before we give the item, because otherwise it crashes the game (as it'd be looking for an item that no longer exists)
                    npc.giveItem(items.get(0), player); // assuming the NPC has at least one item
                    System.out.println("You succesfully recieved " + itemName + "! It's been added to your inventory.");
                }
                break;
            default:
                // no special effect
                break;
            }
    }
}
