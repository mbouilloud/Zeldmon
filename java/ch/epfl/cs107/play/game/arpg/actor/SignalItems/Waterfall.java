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
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.List;

public class Waterfall extends AreaEntity {
    private Animation animation;
    private RPGSprite[] sprites = new RPGSprite[3];
    public HiddenDoor hiddenDoor;
    private Lever lever;

    /**
     * Default Bomb constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     * @param lever (Lever) signal used for hidden door
     */
    public Waterfall(Area area, Orientation orientation, DiscreteCoordinates coordinates, Lever lever) {
        super(area, orientation, coordinates);

        hiddenDoor = new HiddenDoor("GrotteMew", new DiscreteCoordinates(8,3), Logic.FALSE,getOwnerArea(),Orientation.DOWN, coordinates, new DiscreteCoordinates(coordinates.x +1, coordinates.y), new DiscreteCoordinates(coordinates.x+2, coordinates.y));
        this.lever = lever;
        getOwnerArea().registerActor(hiddenDoor);


        for (int i = 0; i < 3; i++) {
            sprites[i] = new RPGSprite("zelda/waterfall", 3f, 4f, this, new RegionOfInterest(i * 64 + 10 , 0,45, 64));

            animation = new Animation(8, sprites, true);
        }
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        ArrayList<DiscreteCoordinates> cells = new ArrayList<>();
        cells.add(getCurrentMainCellCoordinates());
        cells.add(new DiscreteCoordinates(getCurrentMainCellCoordinates().x + 1, getCurrentMainCellCoordinates().y)); ///// 3 cells
        cells.add(new DiscreteCoordinates(getCurrentMainCellCoordinates().x + 2, getCurrentMainCellCoordinates().y));
        return cells;
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
        return false;
    }

    @Override
    public boolean takeCellSpace() {
        if(!hiddenDoor.isOpen()){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void update(float deltaTime) {
        if(lever.isUP()){
           hiddenDoor.setSignal(Logic.TRUE);
        }
        if(!lever.isUP()){
            hiddenDoor.setSignal(Logic.FALSE);
        }

        animation.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas);
    }

}
