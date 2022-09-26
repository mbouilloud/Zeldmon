package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.rpg.Inventory.InventoryItem;

/**
 * Enum representing InventoryItem for an ARPG player
 */
public enum ARPGItem implements InventoryItem {
    //// All types of item
    ARROW("Arrow", "zelda/arrow.icon",0, 0),
    SWORD("Sword","zelda/sword.icon", 0, 0),
    STAFF("Staff","zelda/staff_water.icon", 0, 0),
    BOW("Bow","zelda/bow.icon", 0, 0),
    BOMB("Bomb","zelda/bomb", 0, 0),
    CASTLEKEY("Castle key","zelda/key", 0 , 0),
    POKEBALL_RED("Pokeball red","Inball", 0 , 0),
    POKEBALL_BLUE("Pokeball blue","Masterball", 0 , 0),
    POKEBALL_ORANGE("Pokeball orange","Satball", 0 , 0),
    POKEBALL_GREEN("Pokeball gree","Mecaball", 0 , 0),
    SUPER_BOW("Super Bow", "zelda/arbalete",0,0),
    STEAK("Steak", "zelda/steak",0,0),
    PIOCHE("Pioche", "Pioche",0,0),
    ;

    final float weigth;
    final int price;
    final String name;
    final String spriteName;

    /**
     * Default ARPGItem constructor
     * @param name (String): name of item
     * @param weigth (float): weight of item
     * @param price (Cint): price of item
     */
    ARPGItem(String name,String spriteName, float weigth , int price){
        this.name= name;
        this.spriteName =spriteName;
        this.weigth = weigth;
        this.price = price;
    }

    @Override
    public float getWeigth() {
        return weigth;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getSpriteName() {
        return spriteName;
    }
}
