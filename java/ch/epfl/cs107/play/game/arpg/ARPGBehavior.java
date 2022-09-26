package ch.epfl.cs107.play.game.arpg;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.actor.FlyableEntity;
import ch.epfl.cs107.play.window.Window;

public class ARPGBehavior extends AreaBehavior {

    public enum CellType{
        NULL(0, true, false),
        WALL(-16777216, false, false),
        IMPASSABLE(-8750470, false, true),
        INTERACT(-256, true, false),
        DOOR(-195580, true, false),
        WALKABLE(-1, true, true),;

        final int type;
        final boolean isWalkable;
        final boolean isFlyable;

        CellType(int type, boolean isWalkable, boolean isFlyable){
            this.type = type;
            this.isWalkable = isWalkable;
            this.isFlyable = isFlyable;
        }

        public static CellType toType(int type){
            for(CellType ict : CellType.values()){
                if(ict.type == type)
                    return ict;
            }
            // When you add a new color, you can print the int value here before assign it to a type
            return NULL;
        }
    }

    /**
     * Default Tuto2Behavior Constructor
     * @param window (Window), not null
     * @param name (String): Name of the Behavior, not null
     */
    public ARPGBehavior(Window window, String name){
        super(window, name);
        int height = getHeight();
        int width = getWidth();
        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width ; x++) {
                CellType color = CellType.toType(getRGB(height-1-y, x));
                setCell(x,y, new ARPGCell(x,y,color));
            }
        }
    }


    /**
     * Cell adapted to the Tuto2 game
     */
    public class ARPGCell extends AreaBehavior.Cell {
        /// Type of the cell following the enum
        private final CellType type;

        /**
         * Default Tuto2Cell Constructor
         * @param x (int): x coordinate of the cell
         * @param y (int): y coordinate of the cell
         * @param type (EnigmeCellType), not null
         */
        public  ARPGCell(int x, int y, CellType type){
            super(x, y);
            this.type = type;
        }
        public boolean isDoor() {
            return type == CellType.DOOR;
        }
        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        }

        @Override
        protected boolean canEnter(Interactable entity) {

            if(entity instanceof FlyableEntity){
                return ((type.isWalkable)||type.isFlyable);
            }

            if(!entity.takeCellSpace()){
                return type.isWalkable;
            }
            return (type.isWalkable && !hasNonTraversableContent()); //// if cell has an entity that is non traversable, nothing can enter this cell
        }


        @Override
        public boolean isCellInteractable() {
            return true;
        }

        @Override
        public boolean isViewInteractable() {
            return false;
        }

        @Override
        public void acceptInteraction(AreaInteractionVisitor v) {
        }

    }
}
