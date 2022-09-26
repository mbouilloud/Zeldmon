package ch.epfl.cs107.play.game.arpg.actor.SignalItems;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
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

public class Target extends AreaEntity {
    private RPGSprite[] sprite;
    private Animation animation;
    private boolean hitted = false;

    /**
     * Default Arrow constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     */
    public Target(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates);

        sprite = new RPGSprite[6];
        for(int i = 0; i < 6; i++){
            sprite[i] = new RPGSprite("zelda/orb", 1f,1f, this, new RegionOfInterest(i*32,0,32,32));
        }

        animation = new Animation(4, sprite, true);
    }

    @Override
    public void update(float deltaTime) {
        animation.update(deltaTime);
        super.update(deltaTime);
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
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas);
    }

    /**
     * Hit target --> hitted is now true
     */
    public void hit(){
        hitted = true;
    }

    /**
     * @return true if target hit and false if not
     */
    public boolean hasBeenHit() {
        return hitted;
    }

    /**
     * reset target signal to off
     */
    public void resetHit() {
       hitted = false;
    }


    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

}
