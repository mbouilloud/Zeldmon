package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Dialog;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Collections;
import java.util.List;

public class PoliceMan extends AreaEntity {

    private final Keyboard keyboard = getOwnerArea().getKeyboard();
    private RPGSprite[] sprites = new RPGSprite[3];
    private RPGSprite sprite;
    private Dialog dialog;
    private ARPGPlayer playerTaking;
    private boolean paid = false;
    private boolean talking;
    private boolean firstChange = true;
    private boolean secondChange = true;
    private TempleDoor door;



    /**
     * Default PoliceMan constructor
     *
     * @param area        (Area): Owner Area
     * @param orientation (Orientation): Initial orientation
     * @param coordinates (Coordinates): Initial position
     */
    public PoliceMan(Area area, Orientation orientation, DiscreteCoordinates coordinates, TempleDoor door) {
        super(area, orientation, coordinates);

        this.door = door;

        dialog = new Dialog("Hello the temple is closed, but I can open it for you, depends on your generosity...", "zelda/dialog", getOwnerArea());

        for (int i = 0; i < 3; i++) {
            sprites[i] = new RPGSprite("policeman", 1f, 1.5f, this, new RegionOfInterest(i*16, 0, 16, 21));
        }

        switch (orientation) {
            case DOWN:
                sprite = sprites[0];
                break;

            case UP:
                sprite= sprites[2];
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
    public void Dialog(ARPGPlayer player) {
        this.playerTaking = player;
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
        if(talking == true) {
            if (keyboard.get(Keyboard.ENTER).isPressed()) { /// if enter is pressed change to next dialog
                if(firstChange) {
                    if (playerTaking.getMoney() >= 50) {
                        dialog.resetDialog("Oh I see, you are indeed pretty generous, the door is open, good luck");
                        playerTaking.removeMoney(50);
                        door.setSignal(Logic.TRUE);
                    } else {
                        dialog.resetDialog("Do you think i accept this ? Go away !");
                    }
                    firstChange = false;
                }
                else{
                    talking = false;
                    firstChange = true;
                    dialog.resetDialog("Hello the temple is closed, but I can open it for you, depends on your generosity...");
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

