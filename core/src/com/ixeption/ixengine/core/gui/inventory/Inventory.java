package com.ixeption.ixengine.core.gui.inventory;

public interface Inventory {

    public void openInventory();

    public void closeInventory();

    public void addItem(Item item, int quantity);

    public boolean removeItem(Item item, int quantity);

}
