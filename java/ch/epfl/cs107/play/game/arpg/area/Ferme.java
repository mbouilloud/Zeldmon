package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.CastleDoor;
import ch.epfl.cs107.play.game.arpg.actor.CollectableItem.CastleKey;
import ch.epfl.cs107.play.game.arpg.actor.monsters.FlameSkull;
import ch.epfl.cs107.play.game.arpg.actor.monsters.LogMonster;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Ferme extends ARPGArea {

    public void createArea() {
        registerActor (new Background(this));
        registerActor(new Foreground(this));

        //// Add 3 doors to Area
        registerActor(new Door("zelda/Route", new DiscreteCoordinates(1,15), Logic.TRUE, this , Orientation.RIGHT, new DiscreteCoordinates(19,15), new DiscreteCoordinates(19,16)));
        registerActor(new Door("zelda/Village", new DiscreteCoordinates(4,18), Logic.TRUE, this , Orientation.DOWN, new DiscreteCoordinates(4,0), new DiscreteCoordinates(5,0)));
        registerActor(new Door("zelda/Village", new DiscreteCoordinates(14,18), Logic.TRUE, this , Orientation.DOWN, new DiscreteCoordinates(13,0), new DiscreteCoordinates(14,0)));
        registerActor(new Door("PetalburgShop", new DiscreteCoordinates(3,2), Logic.TRUE, this , Orientation.DOWN, new DiscreteCoordinates(6,11), new DiscreteCoordinates(6,11)));

        registerActor(new LogMonster(this, Orientation.DOWN, new DiscreteCoordinates(5,5)));
        registerActor(new LogMonster(this, Orientation.DOWN, new DiscreteCoordinates(10,5)));

        registerActor(new LogMonster(this, Orientation.DOWN, new DiscreteCoordinates(6,7)));

        registerActor(new LogMonster(this, Orientation.DOWN, new DiscreteCoordinates(5,7)));
    }





    public String getTitle() {
        return "zelda/Ferme";
    }


}
