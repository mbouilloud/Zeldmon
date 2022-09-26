package ch.epfl.cs107.play.game.arpg.actor.CollectableItem;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.Inventory.InventoryItem;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

/**
 * Castle key is a collectable entity that can be contained in an inventory
 */
public class CastleKey extends AreaEntity implements CollectableAreaEntity {
    private RPGSprite sprite;

    /**
     * Default CastleKey constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     */
    public CastleKey(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates);
        sprite = new RPGSprite("zelda/key", 1, 1, this, new RegionOfInterest(0, 0, 16, 16));
        //sprite = new Sprite("zelda/key", 1f,1f,this);
    }

    /**
     * Key pickup, make it disapear of current area
     */
    public void pickup() {
        getOwnerArea().unregisterActor(this);
    }

    /**
     * @return name (Castle key)
     */
    public String getName() {
        return "Castle key";
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor) v).interactWith(this);
    }
}


