package com.alexandria.model.NPC;

import com.alexandria.model.NPC.DialogueLoader;
import com.alexandria.model.Player.Player;
import com.alexandria.model.Gameplay.AshesOfAlexandriaGame;
import com.alexandria.model.Inventory.Item;

import java.util.List;

public class DialogueManager {
    /**
     * Starts a conversation using the provided DialogueTree.
     * @param tree The DialogueTree loaded from JSON.
     */

    // Start a dialogue: return the first node and print its text
    public DialogueNode startDialogue(DialogueTree tree, Player player, NPC npc, StringBuilder out) {
        if (tree == null) {
            out.append("Dialogue could not be loaded or found.\n");
            return null;
        }
        DialogueNode node = tree.getStartNode();
        printNode(node, npc, out);
        return node; // controller keeps track of this node
    }

    // Helper to print a node's NPC line and options
    public void printNode(DialogueNode node, NPC npc, StringBuilder out) {
        out.append("\n")
           .append(npc.getName())
           .append(": ")
           .append(node.getNpcLine())
           .append("\n");

        List<DialogueOption> options = node.getOptions();
        if (options != null) {
            for (int i = 0; i < options.size(); i++) {
                out.append((i + 1))
                   .append(". ")
                   .append(options.get(i).getPlayerLine())
                   .append("\n");
            }
        }
    }

    // Advance dialogue when the player picks an option
    public DialogueNode chooseOption(DialogueTree tree, DialogueNode current, int choiceIndex, Player player, NPC npc, StringBuilder out) {
        
        List<DialogueOption> options = current.getOptions();
        if (options == null || options.isEmpty()) {
            out.append("No options available.\n");
            return null; // end conversation
        }

        if (choiceIndex < 1 || choiceIndex > options.size()) {
            out.append("Invalid choice.\n");
            return current; // stay on same node
        }

        String nextNodeId = options.get(choiceIndex - 1).getNextNode();
        DialogueNode next = tree.getNode(nextNodeId);

        if (next != null) {
            out.append(applyNodeEffects(next, player, npc));
            printNode(next, npc, out);

            // If next node has no options, treat as end of conversation
            if (next.getOptions() == null || next.getOptions().isEmpty()) {
                return null; // signal end
            }
            return next;
        } else {
            out.append("Interaction ended\n");
            return null;
        }
    }


    private String applyNodeEffects(DialogueNode node, Player player, NPC npc) {
        StringBuilder output = new StringBuilder();
        String id = node.getId();

        //this switch statement applies special effects based on the dialogue node id for all npcs (not specific ones)
        switch (id) {
            case "fail1":
                player.adjustHealth(-10); // or setHealth(getHealth() - 10)
                output.append("Incorrect (-10 HP)");
                break;
            case "fail2":
                player.adjustHealth(-15);
                output.append("Incorrect again (-15 HP)");
                break;
            case "fail3":
                player.adjustHealth(-20);
                output.append("Incorrect, yet again (-20 HP)");
                break;
            case "attack":
                player.adjustHealth(-30);
                output.append(npc.getName() + "'s wrath rains upon you. (-30 HP)");
                break;
            case "give_item":
            case "give_item_scholar":
            case "give_item_archive":
                List<Item> items = npc.getItems();
                if (items.isEmpty()) {
                    output.append(npc.getName() + " has no items to give.");
                    break;
                } else {
                    String itemName = items.get(0).getName(); //this line needs to be there before we give the item, because otherwise it crashes the game (as it'd be looking for an item that no longer exists)
                    npc.giveItem(items.get(0), player); // assuming the NPC has at least one item (dealt with in the giveItem method if there's no items)
                    output.append("You succesfully recieved " + itemName + "! It's been added to your inventory.");
                }
                player.setScore(player.getScore() + 20); //reward player with points for getting an item
                break;
            default:
                // no special effect
                break;
            }
        return output.toString();
    }
}
