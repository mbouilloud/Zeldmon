package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Monstre;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

/**
 * Fire Spell is an Entity that want interaction
 */
public class FireSpell extends AreaEntity implements Interactor {
    private Animation animationFire;
    private FireSpellHandler handler = new FireSpellHandler();
    private int MIN_LIFE_TIME = 120;
    private int MAX_LIFE_TIME = 240;

    private int PROPAGATION_TIME_FIRE = 20;

    private int propagationCompteur = 0;

    private int lifeTime;

    private int force;
    private RPGSprite[] sprites;

    /**
     * Default FireSpell constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     */
    public FireSpell(Area area, Orientation orientation, DiscreteCoordinates coordinates, int force) {
        super(area, orientation, coordinates);

        this.force = force;

        lifeTime = randomInt(MAX_LIFE_TIME - MIN_LIFE_TIME) + MIN_LIFE_TIME;

        sprites = new RPGSprite[7];


        for (int i = 0; i < 7; i++) {
            sprites[i] = new RPGSprite("zelda/fire", 1, 1, this, new RegionOfInterest(i * 16, 0, 16, 16));
        }

        animationFire = new Animation(7, sprites, true);
    }

    /**
     * Picks random int between 0 and max
     */
    public int randomInt(int max) {
        return RandomGenerator.getInstance().nextInt(max);
    }

    @Override
    public void update(float deltaTime) {

        if (propagationCompteur == PROPAGATION_TIME_FIRE) {
            if (force > 0) {
                Area area = getOwnerArea();
                FireSpell fire = new FireSpell(area, getOrientation(), getFieldOfViewCells().get(0), force - 1);
                if (area.canEnterAreaCells(fire, getFieldOfViewCells())) {
                    area.registerActor(fire);
                }
            }
            propagationCompteur = 0;
        }

        lifeTime -= 1;
        propagationCompteur += 1;
        animationFire.update(deltaTime);
        if (lifeTime == 0) {
            getOwnerArea().unregisterActor(this);
        }
        super.update(deltaTime);
    }

    /**
     * Stop fire --- unregister itself
     */
    public void stopFire() {
        getOwnerArea().unregisterActor(this);
    }

    @Override
    public void draw(Canvas canvas) {
        ;
        animationFire.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
        return false;
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
        ((ARPGInteractionVisitor) v).interactWith(this);
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    private class FireSpellHandler implements ARPGInteractionVisitor {

        @Override
        public void interactWith(ARPGPlayer player) {
            player.looseHp(1);
        }

        @Override
        public void interactWith(Monstre monstre) {
            if (monstre.isVulnerableTo("feu")) {
                monstre.loosehp(0.5f);
            }
        }

        @Override
        public void interactWith(Grass grass) {
            grass.cutGrass();
        }

        @Override
        public void interactWith(Bomb bomb) {
            bomb.explodeBomb();
        }
    }
}
