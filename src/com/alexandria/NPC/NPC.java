package com.alexandria.NPC;
import java.util.Scanner;

interface NPC {
    public void interact(Scanner sc);
    public void attack();
    public void move();
    public void giveItem(com.alexandria.Inventory.Item item, com.alexandria.Player.Player player);
}
