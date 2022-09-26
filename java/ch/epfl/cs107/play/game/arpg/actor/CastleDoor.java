package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

/**
 * Castle door is a derived type of a Door
 */
public class CastleDoor extends Door {
    private RPGSprite openDoor;
    private RPGSprite closedDoor;

    /**
     * Default CastleDoor constructor, same as door but with default  signal (OFF/FALSE)
     * @param destination        (String): Name of the destination area, not null
     * @param otherSideCoordinates (DiscreteCoordinate):Coordinates of the other side, not null
     * @param area        (Area): Owner area, not null
     * @param orientation (Orientation): Initial orientation of the entity, not null
     * @param position    (DiscreteCoordinate): Initial position of the entity, not null
     */
    public CastleDoor(String destination, DiscreteCoordinates otherSideCoordinates, Area area, Orientation orientation, DiscreteCoordinates position, DiscreteCoordinates... otherCell) {
        super(destination, otherSideCoordinates, Logic.FALSE, area, orientation, position, otherCell);
        openDoor = new RPGSprite ("zelda/castleDoor.open", 2, 2, this , new RegionOfInterest(0 , 0, 32, 32));
        closedDoor = new RPGSprite ("zelda/castleDoor.close", 2, 2, this , new RegionOfInterest(0 , 0, 32, 32));
    }

    @Override
    public boolean isViewInteractable() {
        if(!isOpen()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return super.isCellInteractable();
    }

    @Override
    protected void setSignal(Logic signal) {
        super.setSignal(signal);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor)v).interactWith(this);
    }

    @Override
    public void draw(Canvas canvas) {
        if (isOpen()){
            openDoor.draw(canvas);
        }
        else{
            closedDoor.draw(canvas);
        }
    }

    @Override
    public boolean takeCellSpace() {
        if (isOpen()){
            return false;
        }
        else{
            return true;
        }
    }
}
