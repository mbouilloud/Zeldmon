package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.PoliceMan;
import ch.epfl.cs107.play.game.arpg.actor.TempleDoor;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class RouteTemple extends ARPGArea {
    public void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        //// Add 2 doors to Area
        registerActor(new Door("zelda/Route", new DiscreteCoordinates(18, 10), Logic.TRUE, this, Orientation.DOWN, new DiscreteCoordinates(0, 5), new DiscreteCoordinates(0, 4)));
        TempleDoor door = new TempleDoor("zelda/Temple", new DiscreteCoordinates(4, 1), this, Orientation.DOWN, new DiscreteCoordinates(5, 6));
        registerActor(door);
        registerActor(new PoliceMan(this, Orientation.DOWN, new DiscreteCoordinates(7,5), door));



    }

    public String getTitle() {
        return "zelda/RouteTemple";
    }
}
