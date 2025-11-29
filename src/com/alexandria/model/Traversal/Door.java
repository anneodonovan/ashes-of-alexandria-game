package com.alexandria.model.Traversal;

import java.util.ArrayList;
import java.util.List;

public class Door extends Exit {
	private static List<Door> allDoors = new ArrayList<>(); //array list to keep track of all doors globally so they're accessible	   
    public boolean isLocked;
    public String requiredKeyID;
    public boolean canPass;

    public Door(Room roomA, Room roomB, Direction directionFromA, Direction directionFromB, String label, boolean isVisible, String requiredKeyID, boolean isLocked, boolean canPass) {
        super(roomA, roomB, directionFromA, directionFromB, label, isVisible);
        this.requiredKeyID = requiredKeyID;
        this.isLocked = isLocked;
        this.canPass = !isLocked;
        allDoors.add(this); //add to list
    }

    public void setRequiredKeyID(String requiredKeyID) {
        this.requiredKeyID = requiredKeyID;
    }

    public String getRequiredKeyID() {
        return requiredKeyID;
    }

    public static List<Door> getDoorsList() {
        return allDoors;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
        if (isLocked) {
            this.canPass = false; // if locked, it cannot be passed
        }
    }

    public boolean unlock(String keyID) {
        if (isLocked && keyID.equals(requiredKeyID)) {
            isLocked = false;
            canPass = true;
            return true; // Successfully unlocked
        }
        return false; // Failed to unlock
    }

    public boolean lock(String keyID) {
        if (!isLocked && keyID.equals(requiredKeyID)) {
            isLocked = true;
            canPass = false;
            return true; // Successfully locked
        }
        return false; // Failed to lock
    }
}
