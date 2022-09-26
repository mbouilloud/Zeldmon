package ch.epfl.cs107.play.game.arpg.area;


import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.CollectableItem.Pokeball;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;


public class Arena extends ARPGArea {

    public void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        registerActor(new Pokeball(this, Orientation.DOWN, new DiscreteCoordinates(4, 5), "green"));

        registerActor(new Door("zelda/Ferme", new DiscreteCoordinates(6,8), Logic.TRUE, this, Orientation.DOWN, new DiscreteCoordinates(5,0), new DiscreteCoordinates(4,0)));

    }

    public String getTitle(){
        return "PetalburgArena1";
    }



}