package com.alexandria.NPC;

import com.alexandria.Traversal.Room;

import java.util.List;

import com.alexandria.Inventory.Item;
import com.alexandria.Player.Player;

public class Assistants extends AbstractNPC implements NPC {
    
    public Assistants (String name, int health, Room location, boolean alive, List<Item> items) {
        super(name, health, location, alive, items);
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

    public void interact() {
        //logic
    }

    public void attack() {
        //logic
    }

    public void move() {
        //logic
    }
}
