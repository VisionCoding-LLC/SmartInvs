package fr.minuskube.inv;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MultiStateItem implements ItemClicker{

    private int states; // Default should be one since its a button {0,1}
    private int currentState; // Stores current state
    private Map<Integer, ItemStack> stackMap; // Stores Item information for each state
    private Map<Integer, Consumer<InventoryClickEvent>> stateConsumers; // Stores Click Consumer for each corresonding state

    public MultiStateItem(int states, int currentState,
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
        if(getStateConsumers().get(getCurrentState()) != null){
            getStateConsumers().get(getCurrentState()).accept(e);

            if(getStateConsumers().containsKey(getCurrentState()+1)){
                switchState(getCurrentState()+1);
            }else{
                switchState(0);
            }
        }
    }

    @Override
    public boolean isButton() {
        return true;
    }

    // May be null
    @Override
    public ItemStack getItem(){
        return this.stackMap.get(getCurrentState());
    }

    public void addState(int state, ItemStack stack, Consumer<InventoryClickEvent> consumer){
        stateConsumers.remove(state);
        stateConsumers.put(state, consumer);

        stackMap.remove(state);
        stackMap.put(state, stack);
    }

    public static MultiStateItem empty() {
        return new MultiStateItem(1, 0,
                new HashMap<>(), new HashMap<>());
    }

    public int getCurrentState() {
        return currentState;
    }

    public int getStates() {
        return states;
    }

    public Map<Integer, ItemStack> getStackMap() {
        return stackMap;
    }

    public Map<Integer, Consumer<InventoryClickEvent>> getStateConsumers() {
        return stateConsumers;
    }
}
