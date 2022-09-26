package ch.epfl.cs107.play.game.arpg.actor.Character;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Dialog;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Collections;
import java.util.List;

public class Assistant extends AreaEntity {
    private final Keyboard keyboard = getOwnerArea().getKeyboard();
    private RPGSprite[] sprites = new RPGSprite[3];
    private RPGSprite sprite;
    private Dialog dialog;
    private boolean talking = false;
    private boolean firstChange = true;
    private boolean secondChange = true;

    /**
     * Default Assistant constructor
     * @param area (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     */
    public Assistant(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates);

        dialog = new Dialog("Hello !", "zelda/dialog", getOwnerArea());
        for (int i = 0; i < 3; i++) {
            sprites[i] = new RPGSprite("assistant.fixed", 1.2f, 1.5f, this, new RegionOfInterest(i * 16, 0, 16, 21));
        }

        switch (orientation) {
            case UP:
                sprite = sprites[2];
                break;

            case DOWN:
                sprite = sprites[0];
                break;

            case LEFT:
                sprite = sprites[1];
                break;

            case RIGHT:
                sprite = sprites[3];
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
        if (keyboard.get(Keyboard.ESC).isPressed()) {
            talking = false;
        }
        if (keyboard.get(Keyboard.ENTER).isPressed()) {
            if (firstChange) {
                dialog.resetDialog("This lever will open what you are looking for.");
                firstChange = false;
            } else {
                if (secondChange) {
                    dialog.resetDialog("Come on, use it !");
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
        sprite.draw(canvas);
    }
}
