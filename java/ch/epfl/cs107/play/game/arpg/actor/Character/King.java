package ch.epfl.cs107.play.game.arpg.actor.Character;

import ch.epfl.cs107.play.game.areagame.Area;

import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.CollectableItem.SuperBow;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Dialog;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Collections;
import java.util.List;

public class King extends AreaEntity {

    private final Keyboard keyboard = getOwnerArea().getKeyboard();
    private RPGSprite[] sprites = new RPGSprite[3];
    private RPGSprite spriteKing;
    private Dialog dialog;
    private boolean talking = false;
    private boolean firstChange = true;
    private boolean secondChange = true;

    /**
     * Default BKing constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     */
    public King(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates);

        dialog = new Dialog("Thanks for saving our kingdom", "zelda/dialog", getOwnerArea());
        for (int i = 0; i < 3; i++) {
            sprites[i] = new RPGSprite("zelda/king", 1f, 2f, this, new RegionOfInterest(0, i * 32, 16, 32));
        }

        switch (orientation) {
            case DOWN:
                spriteKing = sprites[2];
                break;

            case UP:
                spriteKing = sprites[0];
                break;

            case LEFT:
                spriteKing = sprites[3];
                break;

            case RIGHT:
                spriteKing = sprites[1];
        }
    }
    /**
     * Turn talking to true, used when king got interacted with
     **/
    public void Dialog() {
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
        if(keyboard.get(Keyboard.ESC).isPressed()){
            talking = false;
        }
        if (keyboard.get(Keyboard.ENTER).isPressed()) { /// if enter is pressed change to next dialog
            if (firstChange) {
                dialog.resetDialog("To thank you we offer you this special Magic Bow");
                firstChange = false;
            } else {
                if (secondChange) {
                    dialog.resetDialog("Congratulations, you won a 'Magic Bow'");
                    getOwnerArea().registerActor(new SuperBow(getOwnerArea(), Orientation.DOWN, new DiscreteCoordinates(getCurrentMainCellCoordinates().x + 2, getCurrentMainCellCoordinates().y)));
                    secondChange = false;
                } else {
                    talking = false;
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
        spriteKing.draw(canvas);
    }

}

