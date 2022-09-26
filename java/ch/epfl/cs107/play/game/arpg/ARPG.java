package ch.epfl.cs107.play.game.arpg;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.area.*;
import ch.epfl.cs107.play.game.rpg.RPG;

import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;

public class ARPG extends RPG {
    ArrayList<String> areas = new ArrayList<>();
    private void createAreas(){
        addArea(new Ferme());
        areas.add("zelda/Ferme");

        addArea(new Village());
        areas.add("zelda/Village");

        addArea(new Route());
        areas.add("zelda/Route");

        addArea(new Shop());
        areas.add("PetalburgShop");

        addArea(new RouteChateau());
        areas.add("zelda/RouteChateau");

        addArea(new Chateau());
        areas.add("zelda/Chateau");

        addArea(new GrotteMew());
        areas.add("GrotteMew");

        addArea(new RouteTemple());
        areas.add("zelda/RouteTemple");

        addArea(new Temple());
        areas.add("zelda/Temple");

        addArea(new EndGame());
        areas.add("Endgame");

        addArea(new Grotte());
        areas.add("Grotte");

        addArea(new Arena());
        areas.add("PetalburgArena1");

        addArea(new GameOver());
        areas.add("GameOver");
    }


    public void end(){}

    public void update(float deltaTime){
        Player player = getPlayer();
        if(((ARPGPlayer)player).isDead()){ /// if player died
            player.leaveArea();
            for(int i =0; i< areas.size(); i++){
                setCurrentArea(areas.get(i), true);
            }
            Area area = setCurrentArea("GameOver", false);
            player.enterArea(area, new DiscreteCoordinates(3,3));
            ((ARPGPlayer)player).resetPlayer();

        }
        super.update(deltaTime);
    }

    public String getTitle(){
        return "Zel/Mon";
    }

    public boolean begin(Window window, FileSystem fileSystem) {

        if (super.begin(window,fileSystem)) {
            createAreas();
            Area area = setCurrentArea("zelda/Ferme", true);
            initPlayer(new ARPGPlayer(area, Orientation.DOWN, new DiscreteCoordinates(6,10)));
            area.setViewCandidate(getPlayer());
            return true;
        }
        else return false;
    }



}
