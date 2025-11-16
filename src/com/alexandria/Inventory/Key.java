package com.alexandria.Inventory;

import com.alexandria.Traversal.Exit;
import com.alexandria.Traversal.Room;
import java.util.ArrayList;
import java.util.List;

public class Key extends Item {
    private List<Exit> unlocks; //list of exits this key can unlock

    public Key(String name, String description, Room Location, int id, boolean isVisible) {
        super(name, description, Location, id, isVisible);
        unlocks = new ArrayList<>();
    }

    public void addUnlockableExit(Exit exit) {
        unlocks.add(exit);
    }
    
    public List<Exit> getUnlocks() {
        return unlocks;
    }
}
