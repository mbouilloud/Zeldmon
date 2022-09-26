package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Monstre;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;


import java.util.Collections;
import java.util.List;

/**
 * Bomb is an AreaEntity that wants interaction
 */
public class Bomb extends AreaEntity implements Interactor {
    private int retardateur;
    private RPGSprite sprite;
    private boolean etatExplosion = false;
    private BombHandler handler = new BombHandler();
    private Animation animation;

    /**
     * Default Bomb constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     */
    public Bomb(Area area, Orientation orientation, DiscreteCoordinates coordinates, int retardateur){
        super(area, orientation, coordinates);
        sprite = new RPGSprite("zelda/bomb", +1f, 1f, this, new RegionOfInterest(0,0,16,16));
        this.retardateur = retardateur;

        /// Create animation for explosion
        RPGSprite spriteExplo[] = new RPGSprite[7];
        for(int i=0; i < 7;i++){
            spriteExplo[i] = new RPGSprite ("zelda/explosion", 1, 1, this , new RegionOfInterest (i*32 , 0, 32, 32));
        }
        animation = new Animation(1,spriteExplo, false);
    }

    @Override
    public void update(float deltaTime){
        if (retardateur != 0) {
            retardateur -=1;
        }
        else{
            etatExplosion = true;
        }

        if(etatExplosion) {
            if(animation.isCompleted()) { //// if explosion animation is over, unregister actor
                getOwnerArea().unregisterActor(this);
            }
            else {
                animation.update(deltaTime);
            }
        }

        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        if (!animation.isCompleted()) {
            if (etatExplosion) {
                //RPGSprite spriteExplo = new RPGSprite("zelda/explosion", 1f, 1f, this, new RegionOfInterest(0,0,32,32));
                animation.draw(canvas);
            } else {
                sprite.draw(canvas);
            }
        }
    }

    @Override
    public boolean wantsCellInteraction() {
        return etatExplosion;
    }

    @Override
    public boolean wantsViewInteraction() {
        return etatExplosion;
    }


    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }


    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor)v).interactWith(this);
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return getCurrentMainCellCoordinates().getNeighbours();
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    public void explodeBomb(){
        etatExplosion = true;
    }

    private class BombHandler implements ARPGInteractionVisitor {

        /**
         * Interaction between Bomb and Grass (unregister grass actor)
         * @param grass (Grass): actor (grass), bomb is going to interact with it
         */
        public void interactWith(Grass grass) {
           grass.cutGrass();
        }

        /**
         * Interaction between Bomb and ARPGPlayer (decrease hp by 2)
         * @param player (ARPGPlayer): actor (ARPGPlayer), bomb is going to interact with it
         */
        public void interactWith(ARPGPlayer player){
            player.looseHp(2); //// problem when the bomb is explodi
        }

        /**
         * Interaction between Bomb and ARPGPlayer (decrease hp by 2)
         * @param monstre (Monstre): monster (Monstre), bomb is going to interact with it
         */
        public void interactWith(Monstre monstre){
            if (monstre.isVulnerableTo("physique")){
                monstre.loosehp(5);
            }
        }

        @Override
        public void interactWith(TempleDoor templeDoor) {
            templeDoor.setSignal(Logic.TRUE);
        }
    }
}
