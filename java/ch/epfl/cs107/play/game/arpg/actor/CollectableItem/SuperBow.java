package ch.epfl.cs107.play.game.arpg.actor.CollectableItem;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;
/**
 * Class representing super bow, close to Bow but with different representation and collectable entity
 */
public class SuperBow extends AreaEntity implements CollectableAreaEntity {
    private RPGSprite sprite;

    /**
     * Default Bomb constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     */
    public SuperBow(Area area, Orientation orientation, DiscreteCoordinates coordinates){
        super(area, orientation, coordinates);

        sprite = new RPGSprite("zelda/arbalete", 1f,1f,this, new RegionOfInterest(0,0,150,150));
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor)v).interactWith(this);
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public void pickup() {
        getOwnerArea().unregisterActor(this);
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }
}
