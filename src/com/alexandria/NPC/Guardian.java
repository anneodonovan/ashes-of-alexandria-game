package com.alexandria.NPC;
import java.util.List;

import com.alexandria.Inventory.Item;
import com.alexandria.Player.Player;
import com.alexandria.Traversal.Room;

public class Guardian extends AbstractNPC implements NPC {
    public int damage;

    public Guardian(String name, int health, Room location, boolean alive, List<Item> items, int damage) {
        super(name, health, location, alive, items);
        this.damage = damage;
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
