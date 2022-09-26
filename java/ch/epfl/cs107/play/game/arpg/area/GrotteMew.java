package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.CollectableItem.Pokeball;
import ch.epfl.cs107.play.game.arpg.actor.Rock;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class GrotteMew extends ARPGArea {

    public void createArea() {

        registerActor(new Background(this));

        registerActor(new Door("zelda/Village", new DiscreteCoordinates(11, 15), Logic.TRUE, this, Orientation.UP, new DiscreteCoordinates(8, 2)));

        registerActor(new Pokeball(this, Orientation.DOWN, new DiscreteCoordinates(8, 7), "red"));

        registerActor(new Rock(this, Orientation.DOWN, new DiscreteCoordinates(9,7)));
        registerActor(new Rock(this, Orientation.DOWN, new DiscreteCoordinates(7,7)));
        registerActor(new Rock(this, Orientation.DOWN, new DiscreteCoordinates(8,8)));
        registerActor(new Rock(this, Orientation.DOWN, new DiscreteCoordinates(9,8)));
        registerActor(new Rock(this, Orientation.DOWN, new DiscreteCoordinates(7,8)));
        registerActor(new Rock(this, Orientation.DOWN, new DiscreteCoordinates(7,6)));
        registerActor(new Rock(this, Orientation.DOWN, new DiscreteCoordinates(9,6)));

    }

    public String getTitle() {
        return "GrotteMew";
    }
}
