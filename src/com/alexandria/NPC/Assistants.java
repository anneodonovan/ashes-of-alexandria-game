package com.alexandria.NPC;

import com.alexandria.Traversal.Room;

import java.util.List;
import java.util.Scanner;

import com.alexandria.Inventory.Item;
import com.alexandria.Player.Player;

public class Assistants extends AbstractNPC implements NPC {
    
    public Assistants (String name, int health, Room location, boolean alive, List<Item> items, String dialogueFileName) {
        super(name, health, location, alive, items, dialogueFileName);
    }

    public static List<NPC> getNPCs(Room room) {
    // This should pull from room.getAssistants() if Room has it!
        return room.getNPCs(); 
    }

    public void giveItem(Item item, Player player) {
        if (items.contains(item)) {
            items.remove(item);
            player.addItem(item);
            System.out.println(name + " gave you " + item.getName() + ".");
        } else {
            System.out.println(name + " doesn't have that item.");
        }
    }

    public void interact(Scanner sc) {
        DialogueTree tree = DialogueLoader.loadDialogue(this.dialogueFileName);

        if (tree == null) {
            System.out.println("Dialogue could not be loaded for " + this.name);
            return;
        }

        DialogueManager dialogueManager = new DialogueManager();
        dialogueManager.startDialogue(tree, sc);
    }

    public void attack() {
        //logic
    }

    public void move() {
        //logic
    }
}
