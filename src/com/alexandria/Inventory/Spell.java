package com.alexandria.Inventory;

public class Spell<E> extends Item {
    private E effect; 

    public Spell(String name, String description, String Location, int id, boolean isVisible, E effect) {
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
