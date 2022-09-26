package ch.epfl.cs107.play.game.arpg.actor.SignalItems;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class HiddenDoor extends Door {

    /**
     * Default HiddenDoor constructor, same as door but with default  signal (OFF/FALSE), different from Castle Door because no sprite representation
     * @param destination        (String): Name of the destination area, not null
     * @param otherSideCoordinates (DiscreteCoordinate):Coordinates of the other side, not null
     * @param signal (Signal) True of False
     * @param area        (Area): Owner area, not null
     * @param orientation (Orientation): Initial orientation of the entity, not null
     * @param position    (DiscreteCoordinate): Initial position of the entity, not null
     */
    public HiddenDoor(String destination, DiscreteCoordinates otherSideCoordinates,Logic signal, Area area, Orientation orientation, DiscreteCoordinates position, DiscreteCoordinates... otherCells){
        super(destination, otherSideCoordinates, signal, area, orientation, position, otherCells);
    }

    @Override
    protected void setSignal(Logic signal) {
        super.setSignal(signal);
    }

}
