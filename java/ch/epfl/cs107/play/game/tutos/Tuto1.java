package ch.epfl.cs107.play.game.tutos;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.tutos.actor.SimpleGhost;
import ch.epfl.cs107.play.game.tutos.area.tuto1.Ferme;
import ch.epfl.cs107.play.game.tutos.area.tuto1.Village;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class Tuto1 extends AreaGame {
    private SimpleGhost player;

    private final String[] areas = {"zelda/Ferme", "zelda/Village"};
    private int areaIndex;

    private void createAreas(){
        addArea (new Ferme());
        addArea (new Village());
    }


    public void end(){}

    public void update(float deltaTime){


        if(player.isWeak()){
            switchArea();
        }

        Keyboard keyboard = getWindow().getKeyboard();
        Button key = keyboard.get(Keyboard.UP);
        Button key1 = keyboard.get(Keyboard.DOWN);
        Button key2 = keyboard.get(Keyboard.RIGHT);
        Button key3 = keyboard.get(Keyboard.LEFT);

        if(key.isDown())
        {
            player.moveUp();
        }
        if(key1.isDown()){
            player.moveDown();
        }
        if(key2.isDown()){
            player.moveRight();
        }
        if(key3.isDown()){
            player.moveLeft();
        }


        super.update(deltaTime);


    }

    public String getTitle(){
        return "Tuto 1";
    }

    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window,fileSystem)) {
            createAreas();
            areaIndex = 0;
            Area area = setCurrentArea(areas[areaIndex], true);
            player = new SimpleGhost(new Vector(18, 7), "ghost.1");
            area.registerActor(player);
            area.setViewCandidate(player);
            return true;
        }
        else return false;
    }

    public void switchArea(){

        Area currentArea = getCurrentArea();
        currentArea.unregisterActor(player);

        areaIndex = (areaIndex == 0)? 1 : 0;

        currentArea = setCurrentArea(areas[areaIndex], true);

        player.strenghten();
        currentArea.registerActor(player);
        currentArea.setViewCandidate(player);
    }




}
