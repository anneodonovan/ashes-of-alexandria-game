package com.alexandria.model.Player;

import com.alexandria.model.Traversal.Direction;
import com.alexandria.model.Traversal.Exit;
import com.alexandria.model.Traversal.Room;
import com.alexandria.model.Inventory.Item;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable{
    private String name;
    private Room currentRoom;
    private int health;
    private List<Item> inventory;
    private int score;

    public Player (String name, Room startingRoom, int health, int score) {
        this.name = name;
        this.currentRoom = startingRoom;
        this.health = health;
        this.inventory = new ArrayList<>();
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public void move(String dir) {
        String directionStr = dir.toUpperCase();
        Direction direction; // Convert string to Direction enum

        try {
            direction = Direction.valueOf(directionStr); // assign direction based on user input and convert to enum
        } catch (Exception e) {
            System.out.println("That's not a valid direction!");
            return;
        }
        
        Room nextRoom = null;
        for (Exit exit : currentRoom.getExits()) {
            if (exit.getDirectionFrom(currentRoom) == direction) {
                nextRoom = exit.getOtherSide(currentRoom); //get the room on the other side of the exit
                break;
            }
        }
        if (nextRoom != null) {
            currentRoom = nextRoom;
            System.out.println("You moved to: " + currentRoom.getDescription());
        } else {
            System.out.println("You can't go that way!");
        }
    }
    
    public void addItem(Item item) {
        inventory.add(item);
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void adjustHealth(int amount) {
        this.health += amount;
        System.out.println("Your health is now: " + this.health);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public double getHealthPercent() {
        return Math.max(0.0, Math.min(1.0, this.health / 100.0));
    }

    public List<Item> getInventoryItems() {
        return this.inventory;
    }

    public boolean hasItem(String itemName) {
        if (itemName == null) return false;
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    //handles saving player state to a file
    public void savePlayerState() throws IOException {
        FileOutputStream fos = new FileOutputStream(name + ".txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
    }

    //handles reloading player state from a file
    public static Player reloadPlayerState(String playerName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(playerName + ".txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Player loaded = (Player)ois.readObject(); 
        ois.close();
        return loaded;
    }

}
