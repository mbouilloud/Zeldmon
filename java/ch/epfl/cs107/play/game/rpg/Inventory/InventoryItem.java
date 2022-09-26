package ch.epfl.cs107.play.game.rpg.Inventory;


/**
 * Interfrace for an item of an inventory
 */
public interface InventoryItem {

    /**
     * Returns name of item
     */
    String getName();

    /**
     * Returns price of item
     */
    int getPrice();

    /**
     * Returns weigth of item
     */
    float getWeigth();

    /**
     * Returns sprite name of item
     */
    String getSpriteName();
}



