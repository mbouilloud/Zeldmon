package ch.epfl.cs107.play.game.tutos.actor;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import java.awt.*;


public class SimpleGhost extends Entity {

    private Sprite sprite;
    private double energyLvl;
    private TextGraphics hpText;

    public SimpleGhost(Vector position , String spriteName){
        super(position);
        sprite = new Sprite (spriteName , 1, 1.f, this);
        energyLvl = 10;
        hpText = new TextGraphics (Integer.toString ((int) energyLvl), 0.4f, Color.BLUE);
        hpText.setParent ((Positionable) this);
        this.hpText.setAnchor(new Vector ( -0.3f, 0.1f));

    }
    
    public boolean isWeak() {
        if(energyLvl <= 0) {
            return true;
        }
        else{
            return false;
        }
    }

    public void strenghten() {
        energyLvl = 10;
    }

    public void draw(Canvas canvas){
        sprite.draw(canvas);
        hpText.draw(canvas);
    }

    public void update(float deltaTime){
        energyLvl -= deltaTime;

        if(isWeak()){
            energyLvl = 0;
        }

        hpText = new TextGraphics (Integer.toString ((int) energyLvl), 0.4f, Color.BLUE);
        hpText.setParent ((Positionable) this);
        this.hpText.setAnchor(new Vector ( -0.3f, 0.1f));
    }

    public void moveUp(){setCurrentPosition(getPosition().add(0.f,0.05f));}

    public void moveDown(){setCurrentPosition(getPosition().sub(0.f,0.05f));}

    public void moveLeft(){setCurrentPosition(getPosition().sub(0.05f,0.f));}

    public void moveRight(){setCurrentPosition(getPosition().add(0.05f,0.f));}



}
