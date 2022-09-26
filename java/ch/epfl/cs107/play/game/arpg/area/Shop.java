package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.Character.ShopAssistant;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Shop extends ARPGArea {

    public void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        //// Add 2 doors to Area
        registerActor(new Door("zelda/Ferme", new DiscreteCoordinates(6, 10), Logic.TRUE, this, Orientation.UP, new DiscreteCoordinates(3, 1), new DiscreteCoordinates(4, 1)));
        registerActor(new ShopAssistant(this, Orientation.DOWN, new DiscreteCoordinates(3,6)));
    }

    public String getTitle(){
        return "PetalburgShop";
    }



}
