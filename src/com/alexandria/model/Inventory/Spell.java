package com.alexandria.model.Inventory;

public class Spell<E> extends Item {
    private E effect; 

    public Spell(String name, String description, int id, boolean isVisible, E effect) {
        super(name, description, id, isVisible);
        this.effect = effect;
    }
    
    public void setEffect(E effect) {
        this.effect = effect;
    }

    public E getEffect() {
        return effect;
    }
}
