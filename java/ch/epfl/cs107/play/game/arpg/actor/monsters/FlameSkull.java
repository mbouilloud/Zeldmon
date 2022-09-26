package ch.epfl.cs107.play.game.arpg.actor.monsters;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.actor.Bomb;
import ch.epfl.cs107.play.game.areagame.actor.FlyableEntity;
import ch.epfl.cs107.play.game.arpg.actor.Grass;
import ch.epfl.cs107.play.game.rpg.actor.Monstre;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

/**
 * Class representing Flame Skull, a Flying Monster
 */
public class FlameSkull extends Monstre implements FlyableEntity {

    private final int MIN_LIFE_TIME = 150;
    private final int MAX_LIFE_TIME = 300;
    private int life;
    private Animation[] animationSkull;
    private Animation currentAnim;
    private FlameSkullHandler handler = new FlameSkullHandler();

    /**
     * Default FlameSkull constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial  orientation
     * @param coordinates (Coordinates): Initial position
     */
    public FlameSkull(Area area, Orientation orientation, DiscreteCoordinates coordinates){
        super(area, orientation,coordinates, new String[]{"physique", "magie"}); //// with vulnabirities

        life = randomInt(MAX_LIFE_TIME - MIN_LIFE_TIME) + MIN_LIFE_TIME;

        PROBABILITY_TO_CHANGE_DIRECTION = 0.6;
        ANIMATION_DURATION = 8;
        MAXHP = 5;
        hp = MAXHP;

        Sprite[][] sprites = RPGSprite.extractSprites("zelda/flameSkull", 3, 2.5f, 3, this, 32,32, new Orientation[]{Orientation.UP, Orientation.LEFT, Orientation.DOWN, Orientation.RIGHT});

        animationSkull = RPGSprite.createAnimations(ANIMATION_DURATION/2, sprites );

    }


    @Override
    public void update(float deltaTime) {

        if(!getDied()) {

            if(!isDisplacementOccurs()) {
                aleatoryDeplacement();
                setCurrentAnim();
            }


            life -= 1;

            if (isDisplacementOccurs()) {
                currentAnim.update(deltaTime);
            } else {
                currentAnim.reset();
            }

            if (life == 0) {
                die();
            }
        }


        super.update(deltaTime);

    }

    /**
     * Set animation for player depending on orientation
     */
    public void setCurrentAnim(){
        currentAnim = animationSkull[getOrientation().ordinal()];
    }


        @Override
    public void draw(Canvas canvas) {
        if(!getDied()) {
            currentAnim.draw(canvas);
        }
        super.draw(canvas);
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor)v).interactWith(this);
    }


    private class FlameSkullHandler implements ARPGInteractionVisitor {

        @Override
        public void interactWith(ARPGPlayer player) {
            player.looseHp(1.f);
        }

        @Override
        public void interactWith(Grass grass) {
            grass.cutGrass();
        }

        @Override
        public void interactWith(Bomb bomb) {
            bomb.explodeBomb();
        }

        @Override
        public void interactWith(Monstre monstre) {
            if(monstre.isVulnerableTo("feu")){
                monstre.loosehp(1f);
            }
        }
    }
}
