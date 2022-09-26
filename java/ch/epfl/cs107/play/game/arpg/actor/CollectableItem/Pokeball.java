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

public class Pokeball extends AreaEntity implements CollectableAreaEntity {
    private RPGSprite sprite;
    private String color;

    /**
     * Default Bomb constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     */
    public Pokeball(Area area, Orientation orientation, DiscreteCoordinates coordinates, String color){
        super(area, orientation, coordinates);
        this.color = color;

        if(color == "red") {
            sprite = new RPGSprite("Inball", 1f, 1f, this, new RegionOfInterest(0, 0, 16, 16));
        }
        if(color == "blue"){
            sprite = new RPGSprite("Masterball", 1f, 1f, this, new RegionOfInterest(0, 0, 16, 16));
        }
        if(color == "green"){
            sprite = new RPGSprite("Mecaball", 1f, 1f, this, new RegionOfInterest(0, 0, 16, 16));
        }
    }

    /**
     * @return color of pokeball
     */
    public String getColor(){
        return color;
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
        return true;
    }

    @Override
    public boolean takeCellSpace() {
        return true;
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
        return false;
    }
}
