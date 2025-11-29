package com.alexandria.model.NPC;

import com.alexandria.model.Traversal.Room;
import com.alexandria.model.Inventory.Item;
import com.alexandria.model.Player.Player;

import java.util.List;
import java.util.Scanner;

public class Assistants extends AbstractNPC implements NPC {
    
    public Assistants (String name, int health, Room location, boolean alive, List<Item> items, String dialogueFileName) {
        super(name, health, location, alive, items, dialogueFileName);
    }

    public static List<NPC> getNPCs(Room room) {
    // This should pull from room.getAssistants() if Room has it!
        return room.getNPCs(); 
    }

    public void addItem(Item item) {
        items.add(item);
        item.setLocation(null); // Remove item from room when given to NPC
        item.setVisible(false); // Make item invisible in the world
    }

    public List<Item> getItems() {
        return items;
    }

    public void giveItem(Item item, Player player) {
        if (items.remove(item)) { //remove returns true if item was present and removed
            player.addItem(item);
            System.out.println(name + " gave you " + item.getName() + ".");
        } else {
            System.out.println(name + " doesn't have that item.");
        }
    }

    public void interact(Scanner sc, Player currPlayer) {
        DialogueTree tree = DialogueLoader.loadDialogue(this.dialogueFileName);

        if (tree == null) {
            System.out.println("Dialogue could not be loaded for " + this.name);
            return;
        }

        DialogueManager dialogueManager = new DialogueManager();
        dialogueManager.startDialogue(tree, sc, currPlayer, this);
    }

    public void attack() {
        //logic
    }

    public void move() {
        //logic
    }
}
