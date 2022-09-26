package ch.epfl.cs107.play.game.arpg.actor.ProjectilesActor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.*;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Monstre;
import ch.epfl.cs107.play.game.rpg.actor.Projectiles;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

/**
 * Arrow is a projectile
 */
public class Arrow extends Projectiles {
    private ArrowHandler handler = new ArrowHandler();

    private RPGSprite spriteArrow;

    /**
     * Default Arrow constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     */
    public Arrow(Area area, Orientation orientation, DiscreteCoordinates coordinates, int vitesse, int maxDistance) {
        super(area, orientation, coordinates, vitesse, maxDistance);

        RPGSprite[] sprites = new RPGSprite[4];
        for(int i=0; i <= 3 ;i++){
            sprites[i] = new RPGSprite ("zelda/arrow", 1.3f, 1.3f, this , new RegionOfInterest(i*32 , 0, 32, 32));
        }

        //// Choose sprite depending on orientation
        switch (orientation){
            case DOWN:
                spriteArrow = sprites[2];
                break;

            case UP:
                spriteArrow = sprites[0];
                break;

            case LEFT:
                spriteArrow = sprites[3];
                break;

            case RIGHT:
                spriteArrow = sprites[1];
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);
    }

    @Override
    public void draw(Canvas canvas) {
        spriteArrow.draw(canvas);
    }


    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    private class ArrowHandler implements ARPGInteractionVisitor{

        @Override
        public void interactWith(Monstre monstre) {
            if(monstre.isVulnerableTo("physique")){
                monstre.loosehp(0.5f);
                stopped();
            }
        }

        @Override
        public void interactWith(Grass grass) {
            grass.cutGrass();
            stopped();
        }

        @Override
        public void interactWith(Bomb bomb) {
            bomb.explodeBomb();
            stopped();
        }

        @Override
        public void interactWith(FireSpell fire) {
            fire.stopFire();
            stopped();
        }

    }

}
