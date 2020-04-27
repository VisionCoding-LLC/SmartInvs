package fr.minuskube.inv;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.function.Consumer;

public class ClickableButton extends MultiStateItem {

    private ClickableButton(ItemStack onItemStack,Consumer<InventoryClickEvent> onState,
                            ItemStack offItemStack, Consumer<InventoryClickEvent> offState)
    {
        super(1, 0, new HashMap<>(), new HashMap<>());
        addDefaultState(onItemStack, onState);
        addState(1, offItemStack, offState);
    }

    public static ClickableButton of(ItemStack onItemStack,Consumer<InventoryClickEvent> onState,
                                     ItemStack offItemStack, Consumer<InventoryClickEvent> offState){
        return new ClickableButton(onItemStack, onState, offItemStack, offState);
    }

    /*@Override
    public void run(InventoryClickEvent e) {
        switch (getCurrentState()){
            case 0:
                this.getStateConsumers().get(0).accept(e);
                switchState(1);
                break;
            case 1:
                this.getStateConsumers().get(1).accept(e);
                switchState(0);
                break;

            default:
                // Error/Debug Message
                break;
        }
    }*/
}
