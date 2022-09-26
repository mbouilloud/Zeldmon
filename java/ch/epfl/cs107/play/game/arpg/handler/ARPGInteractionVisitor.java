package ch.epfl.cs107.play.game.arpg.handler;

import ch.epfl.cs107.play.game.arpg.ARPGBehavior.ARPGCell;
import ch.epfl.cs107.play.game.arpg.actor.*;
import ch.epfl.cs107.play.game.arpg.actor.Character.Assistant;
import ch.epfl.cs107.play.game.arpg.actor.Character.King;
import ch.epfl.cs107.play.game.arpg.actor.Character.ShopAssistant;
import ch.epfl.cs107.play.game.arpg.actor.PoliceMan;
import ch.epfl.cs107.play.game.arpg.actor.CollectableItem.*;
import ch.epfl.cs107.play.game.arpg.actor.TempleDoor;
import ch.epfl.cs107.play.game.rpg.actor.Monstre;
import ch.epfl.cs107.play.game.arpg.actor.SignalItems.Lever;
import ch.epfl.cs107.play.game.arpg.actor.SignalItems.Target;
import ch.epfl.cs107.play.game.rpg.handler.RPGInteractionVisitor;

public interface ARPGInteractionVisitor extends RPGInteractionVisitor {


    /**
     * Simulate and interaction between ARPG Interactor and an ARPGCell
     * @param cell (ARPGCell), not null
     */
    default void interactWith(ARPGCell cell) {
    }

    /**
     * Simulate and interaction between ARPG Interactor and an ARPGPlayer
     * @param player (ARPGPlayer), not null
     */
    default void interactWith(ARPGPlayer player) {
    }

    /**
     * Simulate and interaction between ARPG Interactor and grass
     * @param grass (Grass), not null
     */
    default void interactWith(Grass grass) {
    }

    /**
     * Simulate and interaction between ARPG Interactor and coin
     * @param coin (Coin), not null
     */
    default void interactWith(Coin coin) {
    }

    /**
     * Simulate and interaction between ARPG Interactor and heart
     * @param heart (Heart), not null
     */
    default void interactWith(Heart heart) {
    }

    /**
     * Simulate and interaction between ARPG Interactor and castle key
     * @param key (Castle key), not null
     */
    default void interactWith(CastleKey key) {
    }

    /**
     * Simulate and interaction between ARPG Interactor and castle door
     * @param castleDoor (Castle door), not null
     */
    default void interactWith(CastleDoor castleDoor) {

    }

    /**
     * Simulate and interaction between ARPG Interactor and temple door
     * @param templeDoor (TempleDoor), not null
     */
    default void interactWith(TempleDoor templeDoor) {
    }

    /**
     * Simulate and interaction between ARPG Interactor and bomb
     * @param bomb (Bomb), not null
     */
    default void interactWith(Bomb bomb) {

    }

    /**
     * Simulate and interaction between ARPG Interactor and monster
     * @param monstre (Monstre), not null
     */
    default void interactWith(Monstre monstre) {

    }

    /**
     * Simulate and interaction between ARPG Interactor and fire spell
     * @param fire (FireSpell), not null
     */
    default void interactWith(FireSpell fire){

    }

    /**
     * Simulate and interaction between ARPG Interactor and king character
     * @param king (King), not null
     */
    default void interactWith(King king) {
    }

    /**
     * Simulate and interaction between ARPG Interactor and assistant character
     * @param assistant (Assistant), not null
     */
    default void interactWith(Assistant assistant) {
    }

    /**
     * Simulate and interaction between ARPG Interactor and lever
     * @param lever (Lever), not null
     */
    default void interactWith(Lever lever) {
    }

    /**
     * Simulate and interaction between ARPG Interactor and pokeball
     * @param pokeball (Pokeball), not null
     */
    default void interactWith(Pokeball pokeball){
    }

    /**
     * Simulate and interaction between ARPG Interactor and Special bow
     * @param arbalete (SuperBow), not null
     */
    default void interactWith(SuperBow arbalete){
    }

    /**
     * Simulate and interaction between ARPG Interactor and Target
     * @param target (Target), not null
     */
    default void interactWith(Target target) {
    }

    /**
     * Simulate and interaction between ARPG Interactor and PoliceMan
     * @param police (PoliceMan), not null
     */
    default void interactWith(PoliceMan police) {
    }

    /**
     * Simulate and interaction between ARPG Interactor and Shop Assistant
     * @param shopAssistant (ShopAssistant), not null
     */
    default void interactWith(ShopAssistant shopAssistant) {
    }

    /**
     * Simulate and interaction between ARPG Interactor and Rock
     * @param rock (Rock), not null
     */
    default void interactWith(Rock rock){}

    /**
     * Simulate and interaction between ARPG Interactor and Pioche
     * @param pioche (Pioche), not null
     */
    default void interactWith(Pioche pioche){}

}
