package ch.epfl.cs107.play.game.arpg.actor.monsters;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.actor.CollectableItem.Coin;
import ch.epfl.cs107.play.game.rpg.actor.Monstre;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogMonster extends Monstre {
    private final int MAXINACTION = 50;
    private int inaction;

    private final int ATTAQUE_SPEED = 3;
    private final int MIN_SLEEPING_DURATION = 50;
    private final int MAX_SLEEPING_DURATION = 80;

    private final int MIN_LIFE_TIME = 200;
    private final int MAX_LIFE_TIME = 400;

    private int life;

    private final double PROBA_INACTION = 0.5;

    private Etat etat;
    private LogMonsterHandler handler = new LogMonsterHandler();


    private Animation currentAnim;
    private Animation[] animationsDeplacement;
    private Animation animationSleeping;
    private Animation animationWakingUp;

    /**
     * Default LogMonster constructor
     *
     * @param area        (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     */
    public LogMonster(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates, new String[]{"physique", "feu"});

        life = randomInt(MAX_LIFE_TIME - MIN_LIFE_TIME) + MIN_LIFE_TIME;

        PROBABILITY_TO_CHANGE_DIRECTION = 0.6;
        ANIMATION_DURATION = 10;

        MAXHP = 5;
        hp = MAXHP;

        createAnimation();
        etat = Etat.IDLE;
        inaction = 0;
    }

    /**
     * Create all animation array of monster
     */
    public void createAnimation() {

        Sprite[][] spriteDeplacement = RPGSprite.extractSprites("zelda/logmonster",
                4, 2f, 2f,
                this, 32, 32, new Orientation[]{Orientation.DOWN, Orientation.UP, Orientation.RIGHT, Orientation.LEFT});
        // crée un tableau de 4 animation
        animationsDeplacement = RPGSprite.createAnimations(ANIMATION_DURATION / 2, spriteDeplacement); /// animation for movement

        Sprite[] spriteWakingUp = new Sprite[3];

        for (int i = 0; i < 3; i++) {
            spriteWakingUp[i] = new RPGSprite("zelda/logMonster.wakingUp", 2.f, 2.f, this, new RegionOfInterest(0, i * 32, 32, 32));
        }

        animationWakingUp = new Animation(8, spriteWakingUp, false); //// waking up animation

        Sprite[] spriteSleeping = new Sprite[4];

        for (int i = 0; i < 4; i++) {
            spriteSleeping[i] = new RPGSprite("zelda/logMonster.sleeping", 2f, 2f, this, new RegionOfInterest(0, i * 32, 32, 32));
        }

        animationSleeping = new Animation(9, spriteSleeping, false); //// sleeping animation

        setCurrentAnimDeplacement();
    }

    @Override
    public void update(float deltaTime) {

        if (!getDied()) {
            if (inaction == 0) {
                switch (etat) {
                    case ATTAQUE:
                        if (!isDisplacementOccurs() && !move(ATTAQUE_SPEED)){
                            etat = Etat.FALLINGSLEEP;
                        }
                        break;

                    case IDLE:
                        if (!isDisplacementOccurs()) {
                            aleatoryDeplacement();
                            setCurrentAnimDeplacement();
                            if (randomDouble() < PROBA_INACTION) {
                                inaction = randomInt(MAXINACTION);
                            }
                        }
                        break;

                    case SLEEPING:
                        currentAnim = animationWakingUp;
                        etat = Etat.WAKINGUP;
                        break;

                    case FALLINGSLEEP:
                        inaction = randomInt(MAX_SLEEPING_DURATION - MIN_SLEEPING_DURATION) + MIN_SLEEPING_DURATION;
                        currentAnim = animationSleeping;
                        etat = Etat.SLEEPING;
                        break;

                    case WAKINGUP:
                        if (currentAnim.isCompleted()) {
                            setCurrentAnimDeplacement();
                            etat = Etat.IDLE;
                        }
                        break;
                }
            } else {
                inaction -= 1;
            }


            if (!isDisplacementOccurs() && etat != Etat.WAKINGUP) {
                currentAnim.reset();
            } else {
                currentAnim.update(deltaTime);
            }


            if (life == 0) {
                die();
            }

            life -= 1;
        }

        if (getAnimVanish().isCompleted()) { /// drops coin when dead
            getOwnerArea().registerActor(new Coin(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates()));
        }

        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        currentAnim.draw(canvas);
        super.draw(canvas);
    }

    /**
     * Set the new animation
     *
     * @return void : set a new current Animation depending on orientation
     */
    public void setCurrentAnimDeplacement() {
        currentAnim = animationsDeplacement[getOrientation().ordinal()];
    }

    /**
     * Picks random int between 0 and max
     */
    public int randomInt(int max) {
        return RandomGenerator.getInstance().nextInt(max);
    }

    /**
     * Picks random double between 0 and 1
     */
    public double randomDouble() {
        return RandomGenerator.getInstance().nextDouble();
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);
    }

    @Override
    public boolean wantsCellInteraction() {
        return false;
    }

    @Override
    public boolean wantsViewInteraction() {
        if ((etat == Etat.IDLE) || (etat == Etat.ATTAQUE)) {
            return true;
        }
        return false;
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
        ((ARPGInteractionVisitor) v).interactWith(this);
    }

    @Override
    public boolean takeCellSpace() {
        return !getDied();
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        if (etat == Etat.ATTAQUE) { //// Just 1 cell in front
            return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
        } else {
            if (etat == Etat.IDLE) {
                ArrayList<DiscreteCoordinates> facingcells = new ArrayList<>();
                for (int i = 1; i <= 8; i++) {
                    facingcells.add(getCurrentMainCellCoordinates().jump(getOrientation().toVector().mul(i))); //// 8 front cells
                }
                return facingcells;
            } else {
                return null;
            }
        }
    }

    /**
     * Different states for Log Monster
     */
    private enum Etat {
        //// All types of item
        IDLE(),
        ATTAQUE(),
        FALLINGSLEEP(),
        SLEEPING(),
        WAKINGUP(),
        ;

        // public String getSpriteName(){return spriteName;}

    }

    /**
     * Interaction handler for log monster
     */
    private class LogMonsterHandler implements ARPGInteractionVisitor {

        @Override
        public void interactWith(ARPGPlayer player) {
            if (etat == Etat.ATTAQUE) {
                player.looseHp(2);
            } else {
                etat = Etat.ATTAQUE;

            }
        }
    }
}
