package com.alexandria.model.NPC;
import java.util.Scanner;

public interface NPC {
    public String getName();
    public String takeDamage(int damage);
    public String interact(com.alexandria.model.Player.Player currPlayer);
    public void giveItem(com.alexandria.model.Inventory.Item item, com.alexandria.model.Player.Player player);
    public java.util.List<com.alexandria.model.Inventory.Item> getItems();
}
