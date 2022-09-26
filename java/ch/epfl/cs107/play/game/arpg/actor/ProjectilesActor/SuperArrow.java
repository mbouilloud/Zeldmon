package ch.epfl.cs107.play.game.arpg.actor.ProjectilesActor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.Bomb;
import ch.epfl.cs107.play.game.arpg.actor.FireSpell;
import ch.epfl.cs107.play.game.arpg.actor.Grass;
import ch.epfl.cs107.play.game.rpg.actor.Monstre;
import ch.epfl.cs107.play.game.arpg.actor.SignalItems.Target;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class SuperArrow extends Arrow {
    private SuperArrowHandler superArrowHandler = new SuperArrowHandler();

    /**
     * Default Arrow constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     * @param vitesse (int) Speed
     * @param maxDistance (int) max distance
     */
    public SuperArrow(Area area, Orientation orientation, DiscreteCoordinates coordinates, int vitesse, int maxDistance) {
        super(area, orientation, coordinates, vitesse, maxDistance);
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(superArrowHandler);
    }

    /**
     * Super Arrrow interaction handler, same as Arrow but not arrow never stopped.
     */
    private class SuperArrowHandler implements ARPGInteractionVisitor {

        @Override
        public void interactWith(Monstre monstre) {
            if(monstre.isVulnerableTo("physique")){
                monstre.loosehp(10f);
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

        @Override
        public void interactWith(FireSpell fire) {
            fire.stopFire();
        }

        @Override
        public void interactWith(Target target) {
            target.hit();
        }

        }
}
