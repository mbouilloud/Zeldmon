package ch.epfl.cs107.play.game.rpg.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class representing Inventory of player
 */

abstract public class Inventory {
    private float maxWeigth;
    public Map<InventoryItem,Integer> items;
    private float inventoryWeigth;


    /**
     * Default ARPGItem constructor
     * @param maxWeigth (float): maxWeigth of inventory
     */
    public Inventory(float maxWeigth){
        this.maxWeigth = maxWeigth;
        items = new HashMap<>();
        inventoryWeigth = 0;
    }

    /**
     * Adds item(s) to inventory if weigth is accepted. Returns true or false depending if addition was completed
     *
     * @param newItem (InventoryItem)
     *
     * @param quantity (int)
     *
     * @return boolean
     */
    protected boolean addItem(InventoryItem newItem, int quantity) {
        if(quantity * newItem.getWeigth() + inventoryWeigth <= maxWeigth) {

            if (!checkItem(newItem)) { //// if item not already in inventory
                items.put(newItem, quantity);
            }
            else {
                int alreadyPresentQuantity = items.get(newItem);
                items.remove(newItem, alreadyPresentQuantity); /// remove old quantity
                items.put(newItem, quantity + alreadyPresentQuantity); /// put new
            }
            inventoryWeigth += quantity * newItem.getWeigth();
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Removes item(s) to inventory if it is present. Returns true or false depending if removal was completed
     *
     * @param item (InventoryItem)
     *
     * @param quantity (int)
     *
     * @return boolean
     */
    protected boolean removeItem(InventoryItem item, int quantity){
        if (checkItem(item)) {
            int alreadyPresentQuantity = items.get(item);
            if(alreadyPresentQuantity - quantity > 0) { /// if quantity to remove normal
                items.remove(item, alreadyPresentQuantity);
                items.put(item, alreadyPresentQuantity-quantity);
                inventoryWeigth -= quantity * item.getWeigth();
                return true;
            }
            else if (alreadyPresentQuantity - quantity == 0){ /// if quantity is equal to quantity already present, remove everything
                items.remove(item);
                inventoryWeigth -= item.getWeigth()* quantity;
                return true;
            }
            else{
                return false;
            }
        }
        else{
            System.out.print("Item not found"); /// item not in inventory
            return false;
        }
    }

    /**
     * Check if item is contained in inventory
     *
     * @param item (InventoryItem)
     *
     * @return boolean (true for yes/false for no)
     */
    public boolean checkItem(InventoryItem item){
        String itemName = item.getName();
        for(InventoryItem key : items.keySet()){
            if(key.getName() == itemName){
                return true;
            }
        }
        return false;
    }


    /**
     * Returns size of inventory
     *
     * @return size (int)
     */
    protected int getSize(){
        return items.size();
    }

    /**
     * Returns value of inventory ---> price of all cumulate items
     *
     *
     * @return totalPrice (int)
     */
    protected int valueOfItems(){
        int totalPrice = 0;
        for(Entry<InventoryItem, Integer> pair : items.entrySet()){
            totalPrice += (pair.getKey()).getPrice()*(pair.getValue()); //// Item price * quantity contained in inventory
        }
        return totalPrice;
    }

    /**
     * Reset the inventory
     */
    protected void clearInventory(){
        items.clear();
    }

    /**
     * Checks if inventory is empty
     */
    protected boolean isEmpty(){
        return items.isEmpty();
    }


    /**
     * Interface for players that possess an inventory
     */
    public interface Holder {

        /**
         * Check if player posses a certain item on his inventory ---- Used for basic item (ARPGItem like)
         *
         * @param item (InventoryItem)
         * @return boolean
         */
        boolean possess(InventoryItem item);


    }
}
