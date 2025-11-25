package com.alexandria.NPC;
import java.util.ArrayList;
import java.util.List;

import com.alexandria.Inventory.Item;
import com.alexandria.Traversal.Room;

public abstract class AbstractNPC {
    protected String name;
    protected int health;
    protected Room location;
    protected boolean alive;
    protected List<Item> items;
    protected String dialogueFileName;

    public AbstractNPC(String name, int health, Room location, boolean alive, List<Item> items) {
        this.name = name;
        this.health = health;
        this.location = location;
        this.alive = alive;
        this.items = new ArrayList<>();
    }

    public void setDialogueFileName(String dialogueFileName) {
        this.dialogueFileName = dialogueFileName;
    }

    public String getDialogueFileName() {
        return dialogueFileName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setLocation(Room location) {
        this.location = location;
    }

    public Room getLocation() {
        return location;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getHeldItems() {
        return items;
    }
}
