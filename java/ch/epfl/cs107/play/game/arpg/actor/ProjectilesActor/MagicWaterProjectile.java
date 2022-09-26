package ch.epfl.cs107.play.game.arpg.actor.ProjectilesActor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
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


public class MagicWaterProjectile extends Projectiles {
    private MagicWaterHandler handler = new MagicWaterHandler();

    private Animation waterAnimation;

    /**
     * Default Arrow constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     * @param vitesse (int) Speed
     * @param maxDistance (int) max distance
     */
    public MagicWaterProjectile(Area area, Orientation orientation, DiscreteCoordinates coordinates, int vitesse, int maxDistance) {
        super(area, orientation, coordinates, vitesse, maxDistance);

        RPGSprite[] sprites = new RPGSprite[4];
        for(int i=0; i <= 3 ;i++){
            sprites[i] = new RPGSprite ("zelda/magicWaterProjectile", 1.5f, 1.5f, this , new RegionOfInterest(i*32 , 0, 32, 32));
        }

        waterAnimation = new Animation(6, sprites, true);

    }

    @Override
    public void update(float deltaTime) {
        waterAnimation.update(deltaTime);
        super.update(deltaTime);
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);
    }

    @Override
    public void draw(Canvas canvas) {
        waterAnimation.draw(canvas);
    }


    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    /**
     * MagicWater interaction handler
     */
    private class MagicWaterHandler implements ARPGInteractionVisitor {

        @Override
        public void interactWith(Monstre monstre) {
            if(monstre.isVulnerableTo("magie")){
                monstre.loosehp(2f);
                stopped();
            }
        }

        @Override
        public void interactWith(FireSpell fire) {
            fire.stopFire();
        }

    }

}


