package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.Character.Assistant;
import ch.epfl.cs107.play.game.arpg.actor.SignalItems.Lever;
import ch.epfl.cs107.play.game.arpg.actor.SignalItems.Waterfall;
import ch.epfl.cs107.play.game.arpg.actor.TempleDoor;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Village extends ARPGArea {

    public void createArea(){
        registerActor (new Background(this));
        registerActor(new Foreground(this));

        //// Add 3 doors to Area
        registerActor(new Door("zelda/Ferme", new DiscreteCoordinates(4,1), Logic.TRUE, this , Orientation.UP, new DiscreteCoordinates(4,19), new DiscreteCoordinates(5,19)));
        registerActor(new Door("zelda/Ferme", new DiscreteCoordinates(14,1), Logic.TRUE, this , Orientation.UP, new DiscreteCoordinates(13,19), new DiscreteCoordinates(14,19),  new DiscreteCoordinates(15,19)));
        registerActor(new Door("zelda/Route", new DiscreteCoordinates(9,1), Logic.TRUE, this , Orientation.UP, new DiscreteCoordinates(29,19), new DiscreteCoordinates(30,19)));
        Lever lever = new Lever(this, Orientation.DOWN, new DiscreteCoordinates(25,11));
        registerActor(lever);
        registerActor(new Waterfall(this, Orientation.DOWN, new DiscreteCoordinates(10,16), lever));
        registerActor(new Assistant(this, Orientation.DOWN, new DiscreteCoordinates(26,11)));
        registerActor(new TempleDoor("Grotte", new DiscreteCoordinates(16,1),this ,Orientation.DOWN, new DiscreteCoordinates(25,18)));
    }

    public String getTitle() {
        return "zelda/Village";
    }

}
