package com.alexandria.model.Traversal;

import com.alexandria.model.NPC.Assistants;
import com.alexandria.model.NPC.Guardians;
import com.alexandria.model.NPC.NPC;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;
import java.util.List;

public class Room implements Serializable {
	private String name; //added this to make it easier to access seperate room objects
    private String description;
    private ArrayList<Exit> exits; // array list of exit objects for each room
    transient ArrayList<NPC> npcs; //array list of NPCs in the room - must be transient to avoid serialization issues
    private static final List<Room> allRooms = new ArrayList<>();

    public Room(String name, String description) {
        this.name = name;
    	this.description = description;
        this.exits = new ArrayList<>();
        this.npcs = new ArrayList<>();
        allRooms.add(this);
    }

    public String getName() {
    	return name;
    }
    
    public String getDescription() {
        return description;
    }

    public void addExit(Exit exit) {
        exits.add(exit); //adds new exit to the room's exits arraylist
    } 

    public ArrayList<Exit> getExits() {
        return exits;
    }

    public void addNPC(NPC npc) {
        npcs.add(npc); //adds NPC to the room's npc arraylist
    }

    public ArrayList<NPC> getNPCs() {
        return npcs;
    }

    public String getExitString() {
        StringBuilder sb = new StringBuilder();
        Set<Direction> added = new HashSet<>();

        for (Exit exit : exits) { 
            Direction direction = exit.getDirectionFrom(this); 
            if (direction != null && !added.contains(direction)) {    
                sb.append(direction.name().toLowerCase()).append(" ");
                added.add(direction); 
                }
            }
        return sb.toString().trim();
    }

    public String getNPCString() {
        if (npcs.isEmpty()) {
            return "None";
        }
        StringBuilder sb = new StringBuilder();
        for (NPC npc : npcs) {
            sb.append(npc.getName()).append(", ");
        }
        // remove trailing comma and space
        return sb.substring(0, sb.length() - 2);
    }

    public String getLongDescription() {
        return "You are " + description + "\nNPC: " + getNPCString() + ".\nExits: " + getExitString();
    }

    public static Room findRoom(String name) {
        for (Room room : allRooms) {
            if (room.getName().contains(name)) {
                return room;
            }
        }
        return null; // not found
    }

}
