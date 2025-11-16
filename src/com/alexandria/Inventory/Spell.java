package com.alexandria.Inventory;

import com.alexandria.Traversal.Room;

public class Spell<E> extends Item {
    private E effect; 

    public Spell(String name, String description, Room Location, int id, boolean isVisible, E effect) {
        super(name, description, Location, id, isVisible);
        this.effect = effect;
    }
    
    public void setEffect(E effect) {
        this.effect = effect;
    }

    public E getEffect() {
        return effect;
    }
}
