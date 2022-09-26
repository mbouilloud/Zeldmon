package ch.epfl.cs107.play.game.rpg.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Projectiles are movable, they want interaction and they can fly
 */
abstract public class Projectiles extends MovableAreaEntity implements Interactor, FlyableEntity {
    private int vitesse;
    private int maxDistance;
    private DiscreteCoordinates startingCoords;

    /**
     * Default Projectiles constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     * @param vitesse (int): speed
     * @param maxDistance (int)
     */
    public Projectiles(Area area, Orientation orientation, DiscreteCoordinates coordinates, int vitesse, int maxDistance){
        super(area, orientation,coordinates);
        this.vitesse = vitesse;
        this.maxDistance = maxDistance;
        this.startingCoords = coordinates;

        move(vitesse); /// give an initial speed
    }

    @Override
    public void update(float deltaTime) {
         int currentCellX = getCurrentMainCellCoordinates().x;
         int currentCelly =  getCurrentMainCellCoordinates().y;

         int max_X = startingCoords.jump(getOrientation().toVector().mul(maxDistance)).x;
        int max_Y = startingCoords.jump(getOrientation().toVector().mul(maxDistance)).y;


        if(((currentCellX != max_X) || (currentCelly != max_Y)) && (isDisplacementOccurs())){ /// if projectile has not reached max distance
            move(vitesse);
        }
        else{
            getOwnerArea().unregisterActor(this);
        }

        super.update(deltaTime);
    }

    /**
     * Projectile stopped disappear of area
     */
    public void stopped(){
        getOwnerArea().unregisterActor(this);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor)v).interactWith(this);
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
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
    public boolean takeCellSpace() {
        return false;
    }
}
