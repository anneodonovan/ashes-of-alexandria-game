package com.alexandria.Inventory;

import com.alexandria.Traversal.Room;

public class Scroll extends Item {
    private String contents;

    public Scroll(String name, String description, Room Location, int id, boolean isVisible, String contents) {
        super(name, description, Location, id, isVisible);
        this.contents = contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }
    
}
