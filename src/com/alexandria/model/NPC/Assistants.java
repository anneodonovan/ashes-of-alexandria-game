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

    public String interact(Player currPlayer) {
        return this.name + " is ready to talk.";
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
