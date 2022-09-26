package ch.epfl.cs107.play.game.arpg.actor.SignalItems;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Lever extends AreaEntity {
    private  RPGSprite leverUP;
    private  RPGSprite leverDown;

    private boolean isUP = false;

    /**
     * Default Arrow constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     */
    public Lever(Area area, Orientation orientation, DiscreteCoordinates coordinates){
        super(area, orientation, coordinates);

        leverUP = new RPGSprite("LeverUP", 1f, 1f, this, new RegionOfInterest(0,0,16,16));
        leverDown = new RPGSprite("LeverDown", 1f, 1f, this, new RegionOfInterest(0,0,16,16));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor)v).interactWith(this);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    /**
     * Lever is used, or go up : signal on, or down : signal of
     */
    public void useLever(){
        if(!isUP) {
            isUP = true;
        }
        else{
            isUP = false;
        }
    }

    /**
     * @return true if bridge up, and false if not
     */
    public boolean isUP() {
        return isUP;
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        if(!isUP){
            leverDown.draw(canvas);
        }
        else{
            leverUP.draw(canvas);
        }
    }
}
