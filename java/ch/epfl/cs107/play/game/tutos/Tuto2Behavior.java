package ch.epfl.cs107.play.game.tutos;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.Cell;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Image;
import ch.epfl.cs107.play.window.Window;

import java.awt.*;

public class Tuto2Behavior extends AreaBehavior {

    public Tuto2Behavior(Window window, String name) {
        super(window, name);
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                Tuto2CellType cellType = Tuto2CellType.toType(getRGB(getHeight() - 1 - y, x));
                setCell(x, y, new Tuto2Cell(x, y, cellType));
            }
        }
    }

    public boolean isDoor(DiscreteCoordinates coord) {
        return (((Tuto2Cell)getCell(coord.x, coord.y)).isDoor());
    }


    public enum Tuto2CellType {
        NULL(0, false),
        WALL(-16777216, false), // #000000 RGB code of black
        IMPASSABLE(-8750470, false), // #7 A7A7A , RGB color of gray
        INTERACT(-256, true), // #FFFF00 , RGB color of yellow
        DOOR(-195580, true), // #FD0404 , RGB color of red
        WALKABLE(-1, true),
        ; // #FFFFFF , RGB color of white

        final int type;
        final boolean isWalkable;

        Tuto2CellType(int type, boolean isWalkable) {
            this.type = type;
            this.isWalkable = isWalkable;
        }


        static Tuto2CellType toType(int type) {
            Tuto2CellType solution = NULL;
            for (Tuto2CellType test : Tuto2CellType.values()) {
                if (type == test.type) {
                    solution = test;
                }
            }
            return solution;
        }
    }

    class Tuto2Cell extends Cell {
        protected Tuto2CellType type;

        private Tuto2Cell(int x, int y, Tuto2Behavior.Tuto2CellType type) {
            super(x, y);
            this.type = type;
        }

        protected boolean canLeave(Interactable entity) {
            return true;
        }

        protected boolean canEnter(Interactable entity) {
            return type.isWalkable;
        }

        public Tuto2Behavior.Tuto2CellType getType() {
            return type;
        }

        public boolean isCellInteractable() {
            return true;
        }

        public boolean isViewInteractable() {
            return false;
        }

        public void acceptInteraction(AreaInteractionVisitor v) {
        }

        public boolean isDoor() {
            return type == Tuto2CellType.DOOR;
        }
    }
}


