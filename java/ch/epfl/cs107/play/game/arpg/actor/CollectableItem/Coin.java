package ch.epfl.cs107.play.game.arpg.actor.CollectableItem;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
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
 * Coin is a collectable entity
 */
public class Coin extends AreaEntity implements CollectableAreaEntity{
    private RPGSprite[] sprite;
    private Animation animations;

    /**
     * Default Coin constructor
     *
     * @param area        (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     */
    public Coin(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates);
        sprite = new RPGSprite[4];

        for (int i = 0; i <= 3; i++) {
            sprite[i] = new RPGSprite("zelda/coin", 1, 1, this, new RegionOfInterest(i * 16, 0, 16, 16));
        }
        animations = new Animation(8, sprite, true);
    }

    /**
     * Key pickup, make it disapear of current area
     */
    public void pickup() {
        getOwnerArea().unregisterActor(this);
    }


    @Override
    public void draw(Canvas canvas) {
        animations.draw(canvas);
    }


    @Override
    public void update(float deltaTime) {
        animations.update(deltaTime);
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
