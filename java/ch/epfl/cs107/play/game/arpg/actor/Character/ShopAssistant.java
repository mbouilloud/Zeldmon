package ch.epfl.cs107.play.game.arpg.actor.Character;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Dialog;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Collections;
import java.util.List;

public class ShopAssistant extends AreaEntity {
    private final Keyboard keyboard = getOwnerArea().getKeyboard();
    private RPGSprite sprite;
    private Dialog dialog;
    private boolean talking = false;
    private boolean firstChange = true;
    private boolean secondChange = true;
    private boolean questsCompleted = false;

    /**
     * Default Assistant constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     */
    public ShopAssistant(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates);

        dialog = new Dialog("Hello welcome to the best pokemon shop of the region !", "zelda/dialog", getOwnerArea());

        sprite = new RPGSprite("shopAssistant", 1.5f, 2f, this, new RegionOfInterest(0, 0, 52, 64));

    }

    /**
     * Turn talking to true, used when king got interacted with
     **/
    public void Dialog(ARPGPlayer player) {
        if(player.questsCompleted()){
            questsCompleted = true;
        }
        talking = true;
    }


    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        ;
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor) v).interactWith(this);
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public void update(float deltaTime) {
        if (keyboard.get(Keyboard.ESC).isPressed()) {
            talking = false;
        }
        if (keyboard.get(Keyboard.ENTER).isPressed()) {
            if(questsCompleted) {
                if (firstChange) {
                    dialog.resetDialog("Congratulations you found all pokemons");
                    firstChange = false;
                } else {
                    if (secondChange) {
                        dialog.resetDialog("We offer you this new pokemon and 500 coin for reward");
                        secondChange = false;
                    } else {
                        talking = false;
                    }
                }
            }
            else{
                if (firstChange) {
                    dialog.resetDialog("I can't help you ");
                    firstChange = false;
                } else {
                    if (secondChange) {
                        dialog.resetDialog("You need to find all pokemon to earn a reward.");
                        secondChange = false;
                    } else {
                        dialog = new Dialog("Hello welcome to the best pokemon shop of the region !", "zelda/dialog", getOwnerArea());
                        secondChange = true;
                        firstChange =true;
                        talking = false;
                    }
                }
            }
        }
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        if (talking) {
            dialog.draw(canvas);
        }
        sprite.draw(canvas);
    }
}
