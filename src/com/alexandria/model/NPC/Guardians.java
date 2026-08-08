package com.alexandria.model.NPC;

import java.util.List;
import java.util.Scanner;

import com.alexandria.model.Inventory.Item;
import com.alexandria.model.Player.Player;
import com.alexandria.model.Traversal.Room;

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

    public String interact(Player currPlayer) {
        return this.name + " is ready to talk.";
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

    public String takeDamage(int damage) {
        StringBuilder out = new StringBuilder();
        this.health -= damage;
        if (this.health <= 0) {
            this.alive = false;
            out.append(this.name + " has been defeated!\n");
        } else {
            out.append(this.name + " has " + this.health + " health remaining.\n");
        }
        return out.toString();
    }
}

