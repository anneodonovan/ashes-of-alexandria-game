package com.alexandria.Inventory;

import java.util.ArrayList;
import java.util.List;

public class Item {
	private static List<Item> allItems = new ArrayList<>(); //array list to keep track of all items globally	
    private String description;
    private String name;
    private String location;
    private int id;
    private boolean isVisible;

    public Item(String name, String description, String Location, int id, boolean isVisible) {
        this.name = name;
        this.description = description;
        this.location = Location;
        this.id = id;
        this.isVisible = true;
        allItems.add(this); //add to global list
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
    
    public static List<Item> getItems(String location) {
        List<Item> found = new ArrayList<>(); //new array list for items that have been found
    	for (Item item : allItems) {
    		if (item.isVisible() && item.location != null && item.location.equalsIgnoreCase(location)) {
    			found.add(item);
    		}
    	}
        return found;
    }

    public static List<Item> dropItems(String itemName, String location) {
        List<Item> dropped = new ArrayList<>(); //new array list for items that have been dropped
    	for (Item item : allItems) {
    		if (item.isVisible() && item.location != null && item.location.equalsIgnoreCase(location)
                    && item.name.equalsIgnoreCase(itemName)) {
    			dropped.add(item);
    		}
    	}
        return dropped;
    }       
}
