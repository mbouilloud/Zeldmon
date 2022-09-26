package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;

public class EndGame extends ARPGArea {


    public void createArea() {
        registerActor(new Background(this));
    }

    public String getTitle() {
        return "Endgame";
    }


}


