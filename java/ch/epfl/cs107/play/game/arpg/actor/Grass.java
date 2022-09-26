package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.CollectableItem.Coin;
import ch.epfl.cs107.play.game.arpg.actor.CollectableItem.Heart;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

/**
 * Grass is a entity
 */
public class Grass extends AreaEntity {
    private RPGSprite sprite;

    private double PROBABILITY_TO_DROP_ITEM = 0.5;
    private double PROBABILITY_TO_DROP_HEART = 0.5;

    private boolean cutted = false; //// if entity is traversable usefull later
    private Animation cutAnimation;


    /**
     * Default Grass constructor
     * @param area (Area): Owner Area, not null
     * @param orientation (Orientation): Initial player orientation, not null
     * @param coordinates (Coordinates): Initial position, not null
     */
    public Grass(Area area, Orientation orientation, DiscreteCoordinates coordinates){
        super(area, orientation,coordinates);
        sprite = new RPGSprite("zelda/grass", 1f, 1f, this, new RegionOfInterest(0,0,16,16));

        /// Create animation for explosion
        RPGSprite sprites[] = new RPGSprite[4];
        for(int i=0; i < 4;i++){
            sprites[i] = new RPGSprite ("zelda/grass.sliced", 1, 1, this , new RegionOfInterest (i*32 , 0, 32, 32));
        }
        cutAnimation = new Animation(4,sprites, false);
    }

    /**
     * Do action of cutting grass ---> unregister Actor and call fonction to maybe drop an item ()
     */
    public void cutGrass(){
        //// if Grass has been cut we update the fact that it does not take cell space anymore
        ///// This make us able to maybe drop an item
        if(!cutted) { //// just first time
            dropItemProbality();
        }
        cutted = true;
    }

    @Override
    public void update(float deltaTime) {
        if(cutted){
            if(!cutAnimation.isCompleted()) {
                cutAnimation.update(deltaTime);
            }
            else{
                getOwnerArea().unregisterActor(this);
            }
        }
        super.update(deltaTime);
    }

    /**
     * Pick a random double between 0 and 1
     */
    public double randomDouble(){
        return  RandomGenerator.getInstance().nextDouble();
    }

    /**
     * Random item droper, picks a double between 0 and 1,  and depending on it(comparing to constants) drops or not.
     */
    public void dropItemProbality(){
        if(randomDouble() < PROBABILITY_TO_DROP_ITEM){ //// checks possibilty to drop an item
            if(randomDouble() < PROBABILITY_TO_DROP_HEART){ //// checks possibilty to drop a heart
                getOwnerArea().registerActor(new Heart(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates()));
            }
            else{ //// if not heart -- coin
                getOwnerArea().registerActor(new Coin(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates()));
            }
        }
    }


    @Override
    public void draw(Canvas canvas) {
        if(!cutAnimation.isCompleted()) {
            if(cutted){
                cutAnimation.draw(canvas);
            }
            else{
                sprite.draw(canvas);
            }
        }

    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }


    @Override
    public boolean takeCellSpace() {
        return !cutted;
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
        ((ARPGInteractionVisitor)v).interactWith(this);
    }

}
