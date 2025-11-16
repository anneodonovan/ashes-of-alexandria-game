package com.alexandria.Inventory;

public class Scroll extends Item {
    private String contents;

    public Scroll(String name, String description, int id, boolean isVisible, String contents) {
        super(name, description, id, isVisible);
        this.contents = contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }
    
}
