package com.alexandria.NPC;

import java.util.List;
import java.util.Scanner;

import com.alexandria.Inventory.Item;
import com.alexandria.Player.Player;
import com.alexandria.Traversal.Room;

public class Guardians extends AbstractNPC implements NPC {
    public int damage;

    public Guardians(String name, int health, Room location, boolean alive, List<Item> items, String dialogueFileName, int damage) {
        super(name, health, location, alive, items, dialogueFileName);
        this.damage = damage;
    }

    public static List<NPC> getNPCs(Room curRoom) {
    // This should pull from room.getAssistants() if Room has it!
        return curRoom.getNPCs(); 
    }

    public void addItem(Item item) {
        items.add(item);
        item.setLocation(null); // Remove item from room when given to NPC
        item.setVisible(false); // Make item invisible in the world
    }

    public List<Item> getItems() {
        return items;
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

    public void giveItem(Item item, Player player) {
        if (items.contains(item)) {
            items.remove(item);
            player.addItem(item);
            System.out.println(name + " gave you " + item.getName() + ".");
        } else {
            System.out.println(name + " doesn't have that item.");
        }
    }
}

