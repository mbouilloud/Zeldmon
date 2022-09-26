package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.CastleDoor;
import ch.epfl.cs107.play.game.arpg.actor.Character.King;
import ch.epfl.cs107.play.game.arpg.actor.SignalItems.Lever;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Chateau extends ARPGArea {

    public void createArea(){
        registerActor (new Background(this));
        registerActor(new Foreground(this));

        //// Add 2 doors to Area
        registerActor(new Door("zelda/RouteChateau", new DiscreteCoordinates(9,12), Logic.TRUE, this , Orientation.DOWN, new DiscreteCoordinates(7,0), new DiscreteCoordinates(8,0)));
        registerActor(new King(this, Orientation.DOWN, new DiscreteCoordinates(7,12)));
    }

    public String getTitle () {
        return "zelda/Chateau";
    }

}