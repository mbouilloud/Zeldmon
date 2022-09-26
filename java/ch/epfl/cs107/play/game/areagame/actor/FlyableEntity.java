package ch.epfl.cs107.play.game.areagame.actor;

/**
 * Entity able to fly on some specific cells
 */
public interface  FlyableEntity {

    /**
     * Default method of flyable entity, it can fly
     * @return true
     */
    default boolean canFly(){return true;}
}
