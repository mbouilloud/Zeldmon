package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.CollectableItem.Pioche;
import ch.epfl.cs107.play.game.arpg.actor.Rock;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;


public class Grotte extends ARPGArea {

    public void createArea() {

        registerActor(new Background(this));

        registerActor(new Door("zelda/Village", new DiscreteCoordinates(25, 17), Logic.TRUE, this, Orientation.UP, new DiscreteCoordinates(16, 0)));

        registerActor(new Door("PetalburgArena1", new DiscreteCoordinates(5, 1), Logic.TRUE, this, Orientation.UP, new DiscreteCoordinates(6, 32)));

        registerActor(new Pioche(this, Orientation.DOWN,new DiscreteCoordinates(22,37)));
        /// Rocks :)

        registerActor(new Rock(this, Orientation.DOWN, new DiscreteCoordinates(7,9)));
        registerActor(new Rock(this, Orientation.DOWN, new DiscreteCoordinates(6,9)));

        registerActor(new Rock(this, Orientation.DOWN, new DiscreteCoordinates(7,17)));
        registerActor(new Rock(this, Orientation.DOWN, new DiscreteCoordinates(6,17)));

        registerActor(new Rock(this, Orientation.DOWN, new DiscreteCoordinates(6,27)));
    }

    public String getTitle() {
        return "Grotte";
    }
}
