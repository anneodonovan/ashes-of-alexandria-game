package com.alexandria.NPC;

interface NPC {
    public void interact();
    public void attack();
    public void move();
    public void giveItem(com.alexandria.Inventory.Item item, com.alexandria.Player.Player player);
}
