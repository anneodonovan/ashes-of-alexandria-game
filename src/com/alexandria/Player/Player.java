package com.alexandria.Player;

import com.alexandria.Traversal.Direction;
import com.alexandria.Traversal.Exit;
import com.alexandria.Traversal.Room;
import com.alexandria.Inventory.Item;

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
    private List<Item> inventory;

    public Player (String name, Room startingRoom) {
        this.name = name;
        this.currentRoom = startingRoom;
        this.inventory = new ArrayList<>();
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
                System.out.println("We're here"); //if direction mathches an exit from current room
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
