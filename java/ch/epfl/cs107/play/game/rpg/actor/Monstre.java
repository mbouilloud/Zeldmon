package ch.epfl.cs107.play.game.rpg.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

/**
 * Class representing monster of our game
 */

abstract public class Monstre extends MovableAreaEntity implements Interactor {
    protected float hp;
    protected float MAXHP;

    private boolean died = false;

    protected String[] vulnerabilites;
    private Animation animVanish;


    protected double PROBABILITY_TO_CHANGE_DIRECTION;
    protected int ANIMATION_DURATION;

    /**
     * Default Bomb constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     * @param vulnerabilites (Sring Array): monster vulnerabilities
     */
    public Monstre(Area area, Orientation orientation, DiscreteCoordinates coordinates, String[] vulnerabilites){
        super(area, orientation,coordinates);
        this.vulnerabilites = vulnerabilites;

        RPGSprite spriteVanish[] = new RPGSprite[7];
        for(int i=0; i < 7;i++){
            spriteVanish[i] = new RPGSprite ("zelda/vanish", 2, 2, this , new RegionOfInterest (i*32 , 0, 32, 32));
        }
        animVanish = new Animation(2,spriteVanish, false);
    }

    /**
     * Random orientaation changer for monster
     */
    public void aleatoryDeplacement(){
        if(randomDouble() < PROBABILITY_TO_CHANGE_DIRECTION){
            switch (randomInt(4)){
                case(0):
                    orientate(Orientation.DOWN);
                    break;
                case(1):
                    orientate(Orientation.UP);
                    break;
                case(2):
                    orientate(Orientation.LEFT);
                    break;
                case(3):
                    orientate(Orientation.RIGHT);
                    break;
            }
        }
        move(ANIMATION_DURATION);
    }

    /**
     * Picks random double between 0 and 1
     */
    public double randomDouble() {
        return RandomGenerator.getInstance().nextDouble();
    }

    /**
     * Picks random int between 0 and max
     */
    public int randomInt(int max) {
        return RandomGenerator.getInstance().nextInt(max);
    }

    /**
     * Checks list of monster vulnaribities
     * @param vul (String): vulnabirities checked
     */
    public boolean isVulnerableTo(String vul){
        for(int i =0; i < vulnerabilites.length; i++){
            if (vul == vulnerabilites[i]){
                return true;
            }
        }
        return false;
    }

    /**
     * Take away hp of monster
     * @param nB (float): hp to take away
     */
    public void loosehp(float nB){
        hp -= nB;
    }

    /**
     * Monster die, unregister him of current Area
     */
    public void die(){
        died = true;
    }

    /**
     * @return animVanish (Animation of dead monster)
     */
    public Animation getAnimVanish() {
        return animVanish;
    }

    /**
     * @return died, true if monster is dead and false if not
     */
    public boolean getDied(){
        return died;
    }

    @Override
    public void draw(Canvas canvas){
        if (died == true) {
            animVanish.draw(canvas);
        }
    }

    @Override
    public void update(float deltaTime) {
        if(died) {
            if(animVanish.isCompleted()) {
                getOwnerArea().unregisterActor(this);
            }
            else {
                animVanish.update(deltaTime);
            }
        }

        if(hp <= 0){
            die();
        }

        super.update(deltaTime);
    }

    @Override
    public boolean takeCellSpace() {
        if(!died){
            return true;
        }
        return false;
    }


    @Override
    public boolean wantsViewInteraction() {
        return true;
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }


}
