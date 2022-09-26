package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.Bomb;
import ch.epfl.cs107.play.game.arpg.actor.FireSpell;
import ch.epfl.cs107.play.game.arpg.actor.Grass;
import ch.epfl.cs107.play.game.arpg.actor.SignalItems.Bridge;
import ch.epfl.cs107.play.game.arpg.actor.SignalItems.Target;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Route extends ARPGArea{

    public void createArea(){
        registerActor (new Background(this));
        registerActor(new Foreground(this));

        //// Add 2 doors to Area
        registerActor(new Door("zelda/Ferme", new DiscreteCoordinates(18,15), Logic.TRUE, this , Orientation.UP, new DiscreteCoordinates(0,15), new DiscreteCoordinates(0,16)));
        registerActor(new Door("zelda/Village", new DiscreteCoordinates(29,18), Logic.TRUE, this , Orientation.DOWN, new DiscreteCoordinates(9,0), new DiscreteCoordinates(10,0)));
        registerActor(new Door("zelda/RouteChateau", new DiscreteCoordinates(9,1), Logic.TRUE, this , Orientation.DOWN, new DiscreteCoordinates(9,19), new DiscreteCoordinates(10,19)));
        registerActor(new Door("zelda/RouteTemple", new DiscreteCoordinates(1,5), Logic.TRUE, this , Orientation.LEFT, new DiscreteCoordinates(19,11), new DiscreteCoordinates(19,10)));

        Target target = (new Target(this, Orientation.LEFT, new DiscreteCoordinates(18,7)));
        registerActor(target);
        registerActor(new Bridge(this, Orientation.DOWN, new DiscreteCoordinates(15,10), target));


        //// Add grass
        for(int i = 5; i <= 7; i++){
            for(int j =6; j <= 11; j++){
                registerActor(new Grass(this, Orientation.UP, new DiscreteCoordinates(i,j)));
            }
        }

        //// Add Bomb

        registerActor(new Bomb(this, Orientation.UP, new DiscreteCoordinates(6,7), 100));
    }

    public String getTitle () {
        return "zelda/Route";
    }

}