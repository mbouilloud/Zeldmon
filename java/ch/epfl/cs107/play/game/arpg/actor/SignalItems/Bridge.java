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

import java.util.ArrayList;
import java.util.List;

public class Bridge extends AreaEntity {
    private RPGSprite sprite;
    private boolean up = true;
    private Target target;
    private int compteur;

    /**
     * Default Arrow constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     * @param target (Targer) target (used for signal)
     */
    public Bridge(Area area, Orientation orientation, DiscreteCoordinates coordinates, Target target){
        super(area, orientation, coordinates);

        this.target = target;
        compteur = 0;

        sprite = new RPGSprite("zelda/bridge", 4f, 2f, this, new RegionOfInterest(8,9,50,28));
    }

    /**
     * Lift bridge
     */
    public void goUp() {
        up = true;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor)v).interactWith(this);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        ArrayList<DiscreteCoordinates> cells = new ArrayList<>();
        for(int i = 1; i < 3; i++){
            cells.add(new DiscreteCoordinates(getCurrentMainCellCoordinates().x + i, getCurrentMainCellCoordinates().y));
        }
        return cells;
    }

    @Override
    public boolean takeCellSpace() {
        if(!up){
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        if(up) {
            sprite.draw(canvas);
        }
    }

    @Override
    public void update(float deltaTime) {
        if(target.hasBeenHit()){
            up = true;
            compteur = 120;
            target.resetHit();
        }
        if(compteur == 0){
            up = false; //// oscillateur de signal, au bout d'un certain temps le pont redescend
        }
        else{
            compteur -= 1;
        }
    }
}
