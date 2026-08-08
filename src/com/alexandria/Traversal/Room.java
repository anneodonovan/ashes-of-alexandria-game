package com.alexandria.Traversal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;

public class Room implements Serializable {
	private String name; //added this to make it easier to access seperate room objects
    private String description;
    private ArrayList<Exit> exits; // array list of exit objects for each room

    public Room(String name, String description) {
        this.name = name;
    	this.description = description;
        exits = new ArrayList<>();
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

    //add code to prevent printing multiples of the same direction + to provide a list of the options for each direction
    public String getExitString() {
        StringBuilder sb = new StringBuilder();
        Set<Direction> added = new HashSet<>();

        for (Exit exit : exits) { //iterate through list of exits and get direction names to display to player
            Direction direction = exit.getDirectionFrom(this); //get direction based on current room
            if (direction != null && !added.contains(direction)) {    
                sb.append(direction.name().toLowerCase()).append(" ");//make sure the exits described are based on the current room
                added.add(direction); 
                }
            }
        return sb.toString().trim();
    }

    public String getLongDescription() {
        return "You are " + description + ".\nExits: " + getExitString();
    }

}
