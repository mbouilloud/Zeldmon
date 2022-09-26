
package ch.epfl.cs107.play.game.arpg.actor.monsters;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.actor.CollectableItem.CastleKey;
import ch.epfl.cs107.play.game.arpg.actor.FireSpell;
import ch.epfl.cs107.play.game.rpg.actor.Monstre;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Dark Lord is a monster
 */
public class DarkLord extends Monstre {

    private Animation[] animationRepos;
    private Animation[] animationInvoke;
    private Animation[] currentAnimArray;
    private Animation currentAnim;
    private double PROBABILITY_ATTACK = 0.5;


    private final int MIN_SPELL_WAIT_DURATION = 150;
    private final int MAX_SPELL_WAIT_DURATION = 200;

    private int spellWait;
    private int waitCompteur;

    private final double PROBA_INACTION_LORD = 0.5;
    private final int MAXINACTION = 20;

    private final int TELEPORTATION_RADIUS = 15;

    private final int MAX_TENTATIVES = 5;

    private int inaction = 0;

    private DarkLordHandler handler = new DarkLordHandler();

    private Etat etat;

    /**
     * Default DarkLord constructor
     *
     * @param area        (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     */
    public DarkLord(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates, new String[]{"magie"});

        spellWait = randomInt(MAX_SPELL_WAIT_DURATION - MIN_SPELL_WAIT_DURATION) + MIN_SPELL_WAIT_DURATION;

        etat = Etat.IDLE; /// start state is idle

        MAXHP = 5;
        hp = MAXHP;

        ANIMATION_DURATION = 8;
        PROBABILITY_TO_CHANGE_DIRECTION = 0.6;

        Sprite[][] spriteDeplacement = RPGSprite.extractSprites("zelda/darkLord",
                3, 2f, 2.5f,
                this, 32, 32, new Orientation[]{Orientation.UP, Orientation.LEFT, Orientation.DOWN, Orientation.RIGHT});
        // crée un tableau de 4 animation
        animationRepos = RPGSprite.createAnimations(ANIMATION_DURATION / 2, spriteDeplacement); //// normal state animation

        Sprite[][] spriteInvoke = RPGSprite.extractSprites("zelda/darkLord.spell",
                3, 2f, 2.5f,
                this, 32, 32, new Orientation[]{Orientation.UP, Orientation.LEFT, Orientation.DOWN, Orientation.RIGHT});
        // crée un tableau de 4 animation
        animationInvoke = RPGSprite.createAnimations(ANIMATION_DURATION / 2, spriteInvoke, false); //// invoking tp animation

        currentAnimArray = animationRepos;
        currentAnim = currentAnimArray[0];
    }


    /**
     * set current Animation depending on the current orientation
     */
    public void setCurrentAnim() {
        currentAnim = currentAnimArray[getOrientation().ordinal()];
    }


    @Override
    public void update(float deltaTime) {
        DiscreteCoordinates facingCell = getCurrentMainCellCoordinates().jump(getOrientation().toVector());
        //System.out.println(spellWait + " " + waitCompteur);
        if (waitCompteur == spellWait) {
            resetMotion();
            strategy();
            waitCompteur = 0;
        }


        switch (etat) {

            case IDLE:
                if (inaction == 0) {
                    if (!isDisplacementOccurs()) {
                        aleatoryDeplacement();
                        setCurrentAnim();
                    }
                    if (randomDouble() < PROBA_INACTION_LORD) {
                        inaction = randomInt(MAXINACTION);
                    }
                } else {
                    inaction -= 1;
                }
                break;

            case ATTAQUE:
                FireSpell flame = new FireSpell(getOwnerArea(), getOrientation(), facingCell, 5);
                getOwnerArea().registerActor(flame);
                etat = Etat.IDLE;
                break;

            case INVOKING_ENTITY:
                FlameSkull skull = new FlameSkull(getOwnerArea(), getOrientation(), facingCell);
                getOwnerArea().registerActor(skull);
                etat = Etat.IDLE;
                break;

            case INVOKING_TP:
                if (!isDisplacementOccurs()) {
                    currentAnimArray = animationInvoke;
                    setCurrentAnim();
                    if (currentAnim.isCompleted()) {
                        etat = Etat.TELEPORTING;
                    }
                }
                break;

            case TELEPORTING:
                int x = 0;
                int y = 0;
                ArrayList<DiscreteCoordinates> newPositions = new ArrayList<>();
                int nombresDeTentatives = 0;
                do { ///// try do find new position
                    newPositions.clear();
                    x = (randomInt(2 * TELEPORTATION_RADIUS) - TELEPORTATION_RADIUS);
                    y = (randomInt(2 * TELEPORTATION_RADIUS) - TELEPORTATION_RADIUS);
                    newPositions.add(getCurrentMainCellCoordinates().jump(x, y));
                    nombresDeTentatives += 1;
                } while ((!getOwnerArea().enterAreaCells(this, newPositions)) && (nombresDeTentatives <= MAX_TENTATIVES));
                if (nombresDeTentatives <= MAX_TENTATIVES) { /// if position was found
                    getOwnerArea().leaveAreaCells(this, getCurrentCells());
                    getOwnerArea().enterAreaCells(this, newPositions);
                    setCurrentPosition(newPositions.get(0).toVector());
                }
                etat = Etat.IDLE;
                currentAnimArray = animationRepos;
                setCurrentAnim();
                break;
        }

        if (isDisplacementOccurs() || etat == Etat.INVOKING_TP) {
            currentAnim.update(deltaTime);
        } else {
            currentAnim.reset();
        }


        if (getAnimVanish().isCompleted()) { /// when lord dies drop a castle key
            getOwnerArea().registerActor(new CastleKey(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates()));
        }

        waitCompteur += 1;
        super.update(deltaTime);
    }

    /**
     * DarkLord strategy to prepare an attack, do a probability test to choose new state
     */
    public void strategy() {
        if (randomDouble() > PROBABILITY_ATTACK) {
            etat = Etat.ATTAQUE;
        } else {
            etat = Etat.INVOKING_ENTITY;
        }
        OrientationProbality();
    }


    @Override
    public void draw(Canvas canvas) {
        if (!getDied()) {
            currentAnim.draw(canvas);
        }
        super.draw(canvas);
    }


    /**
     * Random orientation changer for monster, but new Orientation must be in front of an accessible cell for Fire spell
     */
    public void OrientationProbality() {

        Orientation[] orientationsTab = {Orientation.DOWN, Orientation.UP, Orientation.LEFT, Orientation.RIGHT};

        ArrayList<Orientation> orientationsPossible = new ArrayList<>();
        for (int i = 0; i < 4; i++) { //// try every orientation possible
            DiscreteCoordinates facingCell = getCurrentMainCellCoordinates().jump(orientationsTab[i].toVector());
            List<DiscreteCoordinates> facingCells = Collections.singletonList(getCurrentMainCellCoordinates().jump(orientationsTab[i].toVector()));
            FireSpell flame = new FireSpell(getOwnerArea(), orientationsTab[i], facingCell, 5);

            if (getOwnerArea().canEnterAreaCells(flame, facingCells)) {
                orientationsPossible.add(orientationsTab[i]);
            }
        }
        orientate(orientationsPossible.get(randomInt(orientationsPossible.size()))); /// select one orientation from possible orientation
    }

    @Override
    public boolean wantsViewInteraction() {
        return !getDied();
    }

    @Override
    public boolean wantsCellInteraction() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        ArrayList<DiscreteCoordinates> viewCells = new ArrayList<>();

        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
                if (i != 3 || j != 3) {
                    viewCells.add(new DiscreteCoordinates(getCurrentMainCellCoordinates().x - 3 + i, getCurrentMainCellCoordinates().y - 3 + j));
                }
            }

        }
        return viewCells;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor) v).interactWith(this);
    }

    /**
     * Different DarkLord states
     */
    private enum Etat {
        //// All types of item
        IDLE(),
        ATTAQUE(),
        INVOKING_ENTITY(),
        INVOKING_TP(),
        TELEPORTING(),
        ;

    }

    /**
     * Interaction handler for Dark lord
     */
    private class DarkLordHandler implements ARPGInteractionVisitor {

        @Override
        public void interactWith(ARPGPlayer player) {
            if (etat != Etat.TELEPORTING) {
                etat = Etat.INVOKING_TP;
            }
        }

    }


}


