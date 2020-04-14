package fr.minuskube.inv;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ClickableButton implements ItemClicker{

    private int states; // Default should be one since its a button {0,1}
    private int currentState; // Stores current state
    private Map<Integer, ItemStack> stackMap; // Stores Item information for each state
    private Map<Integer, Consumer<InventoryClickEvent>> stateConsumers; // Stores Click Consumer for each corresonding state

    public ClickableButton(int states, int currentState,
                           Map<Integer, Consumer<InventoryClickEvent>> stateConsumers,
                           Map<Integer, ItemStack> stackMap) {
        this.states = states;
        this.currentState = currentState;
        this.stateConsumers = stateConsumers;
        this.stackMap = stackMap;
    }

    public void addDefaultState(ItemStack stack, Consumer<InventoryClickEvent> consumer){
        stackMap.remove(0);
        stackMap.put(0, stack);

        stateConsumers.remove(0);
        stateConsumers.put(0, consumer);
    }

    public boolean switchState(int state){
        if(state > this.states){
            System.out.printf("Trying to access invalid state. Limit: %s , Found: %s",
                    this.states, state);
            return false;
        }
        if(!stackMap.containsKey(state)){
            System.out.printf("Could not find itemstack for state. Limit: %s , Index: %s",
                    stackMap.size(), state);
            return false;
        }

        this.currentState = state;
        return true;
    }

    @Override
    public void run(InventoryClickEvent e) {
        switch (getState()){
            case 0:
                this.stateConsumers.get(0).accept(e);
                switchState(1);
                break;
            case 1:
                this.stateConsumers.get(1).accept(e);
                switchState(0);
                break;

            default:
                // Error/Debug Message
                break;
        }
    }

    @Override
    public boolean isButton() {
        return true;
    }

    // May be null
    @Override
    public ItemStack getItem(){
        return this.stackMap.get(getState());
    }

    public void addState(int state, ItemStack stack, Consumer<InventoryClickEvent> consumer){
        stateConsumers.remove(state);
        stateConsumers.put(state, consumer);

        stackMap.remove(state);
        stackMap.put(state, stack);
    }

    public static ClickableButton empty() {
        return new ClickableButton(1, 0,
                new HashMap<>(), new HashMap<>());
    }

    public int getState() {
        return currentState;
    }
}
