package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.rpg.Inventory.Inventory;
import ch.epfl.cs107.play.game.rpg.Inventory.InventoryItem;

public class ARPGInventory extends Inventory {
    private int montantArgent;

    /**
     * Default ARPGInventory constructor
     * @param maxWeigth (float): Inventory max weight
     * @param montantArgent (int): amount of momey in inventory
     */
    public ARPGInventory(float maxWeigth, int montantArgent){
        super(maxWeigth);
        this.montantArgent = montantArgent;
    }

    @Override
    protected boolean addItem(InventoryItem item, int quantity) {
        return super.addItem(item, quantity);
    }

    @Override
    protected boolean removeItem(InventoryItem item, int quantity) {
        return super.removeItem(item, quantity);
    }

    @Override
    protected void clearInventory() {
        super.clearInventory();
    }


    @Override
    protected int getSize() {
        return super.getSize();
    }

    /**
     * Calculate fortune of inventory (money + total price of items in inventory)
     *
     * @return fortune (int)
     */
    public int getFortune(){
        int fortune = montantArgent;

        fortune += valueOfItems();

        return fortune;
    }


    public int getMontantArgent() {
        return montantArgent;
    }

    /**
     * Add money to inventory money
     *
     * @param money(int) to add
     *
     */
    protected void addMoney(int money){
        montantArgent += money;
    }

    /**
     * Remove money of inventory money
     *
     * @param money(int) to remove
     *
     */
    protected void removeMoney(int money){
        montantArgent -= money;
    }


    @Override
    protected boolean isEmpty() {
        return super.isEmpty();
    }
}
