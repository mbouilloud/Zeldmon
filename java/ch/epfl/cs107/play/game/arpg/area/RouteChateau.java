package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.Bomb;
import ch.epfl.cs107.play.game.arpg.actor.CastleDoor;
import ch.epfl.cs107.play.game.arpg.actor.Grass;
import ch.epfl.cs107.play.game.arpg.actor.monsters.DarkLord;
import ch.epfl.cs107.play.game.arpg.actor.monsters.FlameSkull;
import ch.epfl.cs107.play.game.arpg.actor.monsters.LogMonster;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Keyboard;

public class RouteChateau extends ARPGArea {

    public void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        //// Add 2 doors to Area
        registerActor(new Door("zelda/Route", new DiscreteCoordinates(9, 18), Logic.TRUE, this, Orientation.DOWN, new DiscreteCoordinates(9, 0), new DiscreteCoordinates(10, 0)));
        registerActor(new CastleDoor("zelda/Chateau", new DiscreteCoordinates(7, 1), this, Orientation.UP, new DiscreteCoordinates(9, 13), new DiscreteCoordinates(10, 13)));

        registerActor(new Grass(this,Orientation.DOWN, new DiscreteCoordinates(8,5)));

        registerActor(new Grass(this,Orientation.DOWN, new DiscreteCoordinates(12,8)));

        registerActor(new Grass(this,Orientation.DOWN, new DiscreteCoordinates(12,12)));

        registerActor(new DarkLord(this, Orientation.DOWN, new DiscreteCoordinates(9, 9)));
    }

    public String getTitle() {
        return "zelda/RouteChateau";
    }

    @Override
    public void update(float deltaTime) {
        Keyboard keyboard = this.getKeyboard();
        if (keyboard.get(Keyboard.L).isPressed()) {
            registerActor(new LogMonster(this, Orientation.DOWN, new DiscreteCoordinates(9, 9)));
        }
        if (keyboard.get(Keyboard.S).isPressed()) {
            registerActor(new FlameSkull(this, Orientation.DOWN, new DiscreteCoordinates(8, 10)));
        }
        if (keyboard.get(Keyboard.B).isPressed()) {
            registerActor(new Bomb(this, Orientation.DOWN, new DiscreteCoordinates(5, 6), 100));
        }
        super.update(deltaTime);
    }
}
