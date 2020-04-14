package fr.minuskube.inv;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface ItemClicker {

    boolean isButton();
    ItemStack getItem();
    void run(InventoryClickEvent event);
}
