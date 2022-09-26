package ch.epfl.cs107.play.game.tutos.actor;


import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.tutos.Tuto2Behavior;
//import ch.epfl.cs107.play.game.tutosSolution.area.Tuto2Area;
import ch.epfl.cs107.play.game.tutos.area.Tuto2Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.awt.*;
import java.util.Collections;
import java.util.List;


public class GhostPlayer extends MovableAreaEntity {
    private float hp;
    private TextGraphics message;
    private Sprite sprite;
    private boolean isPassingDoor = false;
    /// Animation duration in frame number
    private final static int ANIMATION_DURATION = 8;

    public GhostPlayer(Area area , Orientation orientation , DiscreteCoordinates coordinates, String spriteName){
        super(area, orientation, coordinates);
        this.hp = 10;
        message = new TextGraphics(Integer.toString((int)hp), 0.4f, Color.BLUE);
        message.setParent(this);
        message.setAnchor(new Vector(-0.3f, 0.1f));
        sprite = new Sprite(spriteName, 1.f, 1.f,this);

        resetMotion();
    }

    public void setIsPassingDoor() {
        this.isPassingDoor = true;
    }

    public boolean isPassingDoor() {
        return isPassingDoor;
    }

    public void resetDoorState() {
        isPassingDoor = false;
    }

    public boolean takeCellSpace(){
        return true;
    }

    public boolean isCellInteractable(){
        return true;
    }

    public boolean isViewInteractable(){
        return true;
    }

    public void acceptInteraction(AreaInteractionVisitor v) {
    }

    public void enterArea(Area area,DiscreteCoordinates position){
        area.registerActor(this);
        area.setViewCandidate(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
        resetMotion();
    }

    public void leaveArea(){
        getOwnerArea().unregisterActor(this);
    }

    public void draw(Canvas canvas){
        sprite.draw(canvas);
        message.draw(canvas);
    }

    public boolean isWeak() {
        return (hp <= 0.f);
    }

    public void strengthen() {
        hp = 10;
    }

    public List<DiscreteCoordinates> getCurrentCells () {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    private void moveUp(){
        if(getOrientation() == Orientation.UP){
            move(ANIMATION_DURATION);
        }
        else{
            orientate(Orientation.UP);
        }
    }
    private void moveDown(){
        if(getOrientation() == Orientation.DOWN){
            move(ANIMATION_DURATION);
        }
        else{
            orientate(Orientation.DOWN);
        }
    }
    private void moveLeft(){
        if(getOrientation() == Orientation.LEFT){
            move(ANIMATION_DURATION);
        }
        else{
            orientate(Orientation.LEFT);
        }
    }
    private void moveRight(){
        if(getOrientation() == Orientation.RIGHT){
            move(ANIMATION_DURATION);
        }
        else{
            orientate(Orientation.RIGHT);
        }
    }



    public void update(float deltaTime){
        if (hp > 0) {
            hp -=deltaTime;
            message.setText(Integer.toString((int)hp));
        }

        if (hp < 0) hp = 0.f;


        Keyboard keyboard = getOwnerArea().getKeyboard();
        Button key = keyboard.get(Keyboard.UP);
        Button key1 = keyboard.get(Keyboard.DOWN);
        Button key2 = keyboard.get(Keyboard.RIGHT);
        Button key3 = keyboard.get(Keyboard.LEFT);

        if(key.isDown()) {
            moveUp();
        }
        if(key1.isDown()){
            moveDown();
        }
        if(key2.isDown()){
            moveRight();
        }
        if(key3.isDown()){
            moveLeft();
        }

        super.update(deltaTime);

        List<DiscreteCoordinates> coords = getCurrentCells();
        if (coords != null) {
            for (DiscreteCoordinates c : coords) {
                if (((Tuto2Area)getOwnerArea()).isDoor(c)) setIsPassingDoor();
            }
        }


    }






}
