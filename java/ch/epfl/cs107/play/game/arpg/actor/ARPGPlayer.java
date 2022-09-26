package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.Character.Assistant;
import ch.epfl.cs107.play.game.arpg.actor.Character.King;
import ch.epfl.cs107.play.game.arpg.actor.Character.ShopAssistant;
import ch.epfl.cs107.play.game.arpg.actor.CollectableItem.*;
import ch.epfl.cs107.play.game.arpg.actor.ProjectilesActor.Arrow;
import ch.epfl.cs107.play.game.arpg.actor.ProjectilesActor.MagicWaterProjectile;
import ch.epfl.cs107.play.game.arpg.actor.ProjectilesActor.SuperArrow;
import ch.epfl.cs107.play.game.arpg.actor.SignalItems.Lever;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.Inventory.Inventory;
import ch.epfl.cs107.play.game.rpg.Inventory.InventoryItem;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.Monstre;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;


import java.util.Collections;
import java.util.List;

public class ARPGPlayer extends Player implements Inventory.Holder {
    private final ARPGPlayerHandler handler = new ARPGPlayerHandler();
    private final Keyboard keyboard = getOwnerArea().getKeyboard();


    private static int animationDuration = 4;

    private final static int ANIMATION_NORMAL = 4;
    private final static int ANIMATION_SPRINT = 2;
    private final static int ANIMATION_NOFOOD = 10;

    protected boolean dead;

    private float hp = 5;
    private final float MAXHP = 5;
    private float hunger = 5;
    private int moneyAmountStart;
    private final float MAXHUNGER = 5;
    private boolean rewardEarned = false;


    private Animation[] animationsDeplacement;
    private Animation[] animationsSword;
    private Animation[] animationsBow;
    private Animation[] animationsBaton;
    private Animation[] animationsPioche;
    private Animation currentAnim;
    private Animation[] currentAnimArray;
    private String[] quests = {"", "",""};

    private ARPGItem[] itemPossible = {ARPGItem.BOMB, ARPGItem.ARROW, ARPGItem.BOW, ARPGItem.CASTLEKEY, ARPGItem.PIOCHE, ARPGItem.POKEBALL_BLUE, ARPGItem.POKEBALL_GREEN,
    ARPGItem.POKEBALL_ORANGE,ARPGItem.POKEBALL_RED, ARPGItem.STAFF, ARPGItem.STEAK, ARPGItem.SUPER_BOW, ARPGItem.SWORD};

    private ARPGInventory inventory;
    private InventoryItem currentItem;
    private int currentIndex;

    private Area spawnArea;

    private Etat etat;

    private ARPGPlayerStatusGUI API;

    /**
     * Default ARPGPlayer constructor
     *
     * @param area        (Area): Owner Area, not null
     * @param orientation (Orientation): Initial player orientation, not null
     * @param coordinates (Coordinates): Initial position, not null
     */
    public ARPGPlayer(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates);
        spawnArea = area;
        createAnimation();
        currentAnimArray = animationsDeplacement;
        setCurrentAnim();

        etat = Etat.IDLE;

        moneyAmountStart = 100;

        inventory = new ARPGInventory(100,moneyAmountStart);

        addStartingItems();

        if(!inventory.isEmpty()) {
            currentIndex = itemSelector(0);
        }

        API = new ARPGPlayerStatusGUI(hp, MAXHP, hunger, MAXHUNGER);

        resetMotion();
    }

    /**
     * Add starting items to inventory
     */
    public void addStartingItems(){
        inventory.addItem(ARPGItem.BOMB, 2);
        inventory.addItem(ARPGItem.SWORD, 1);
        inventory.addItem(ARPGItem.BOW, 1);
        inventory.addItem(ARPGItem.STAFF, 1);
        inventory.addItem(ARPGItem.ARROW, 10);
        inventory.addItem(ARPGItem.STEAK, 1000);
    }

    /**
     * Create all animation array of player
     */
    public void createAnimation() {

        Sprite[][] sprites = RPGSprite.extractSprites("zelda/player",
                4, 1, 2f,
                this, 16, 32, new Orientation[]{Orientation.DOWN, Orientation.RIGHT, Orientation.UP, Orientation.LEFT});
        // crée un tableau de 4 animation
        animationsDeplacement = RPGSprite.createAnimations(animationDuration / 2, sprites);

        Sprite[][] sprites1 = RPGSprite.extractSprites("zelda/player.sword",
                4, 2f, 2f,
                this, 32, 32, new Orientation[]{Orientation.DOWN, Orientation.UP, Orientation.RIGHT, Orientation.LEFT});
        // crée un tableau de 4 animation
        animationsSword = RPGSprite.createAnimations(animationDuration / 2, sprites1, false);

        Sprite[][] sprites2 = RPGSprite.extractSprites("zelda/player.bow",
                4, 2f, 2f,
                this, 32, 32, new Orientation[]{Orientation.DOWN, Orientation.UP, Orientation.RIGHT, Orientation.LEFT});
        // crée un tableau de 4 animation
        animationsBow = RPGSprite.createAnimations(animationDuration / 2, sprites2, false);

        Sprite[][] sprites3 = RPGSprite.extractSprites("zelda/player.staff_water",
                4, 2f, 2f,
                this, 32, 32, new Orientation[]{Orientation.DOWN, Orientation.UP, Orientation.RIGHT, Orientation.LEFT});
        // crée un tableau de 4 animation
        animationsBaton = RPGSprite.createAnimations(animationDuration / 2, sprites3, false);

        Sprite[][] sprites4 = RPGSprite.extractSprites("zelda/player.sword.blue",
                4, 2f, 2f,
                this, 32, 32, new Orientation[]{Orientation.DOWN, Orientation.UP, Orientation.RIGHT, Orientation.LEFT});
        // crée un tableau de 4 animation
        animationsPioche = RPGSprite.createAnimations(animationDuration / 2, sprites4, false);
    }


    /**
     * Orientate or Move this player in the given orientation if the given button is down
     *
     * @param orientation (Orientation): given orientation, not null
     * @param b           (Button): button corresponding to the given orientation, not null
     */
    private void moveOrientate(Orientation orientation, Button b) {

        if (b.isDown()) {
            if (getOrientation() == orientation) move(animationDuration);
            else orientate(orientation);
        }
    }


    @Override
    public void update(float deltaTime) {

        if (etat == Etat.IDLE) { /// if attacking can't move
            moveOrientate(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
            moveOrientate(Orientation.UP, keyboard.get(Keyboard.UP));
            moveOrientate(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
            moveOrientate(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
        }

        setCurrentAnim();

        if (etat != Etat.IDLE) {
            if (etat != Etat.ATTAQUE_EPEE) {
                if (!currentAnim.isCompleted()) {
                    currentAnim.update(deltaTime);
                } else {
                    currentAnim.reset();
                    etat = Etat.IDLE;
                    setAnimArrayState(etat);
                    setCurrentAnim();
                }
            } else {
                if (keyboard.get(Keyboard.SPACE).isDown()) {
                    if (currentAnim.isCompleted()) {
                        currentAnim.reset();
                    }
                    currentAnim.update(deltaTime);
                } else {
                    currentAnim.reset();
                    etat = Etat.IDLE;
                    setAnimArrayState(etat);
                    setCurrentAnim();
                }
            }
        } else {
            if (isDisplacementOccurs()) {
                currentAnim.update(deltaTime);
            } else {
                currentAnim.reset();
            }
        }


        if (keyboard.get(Keyboard.TAB).isPressed()) { //// if TAB is pushed swaps item
            nextItem();
        }

        if (keyboard.get(Keyboard.SPACE).isPressed()) {//// if SPACE is pushed use current item
            useItem();
        }

        if (hp <= 0) { /// if hp negative block it at 0
            hp = 0;
            dead = true; ///// COMMENTER CETTE LIGNE POUR ENLEVER LE GAME OVER
        }

        if (isDisplacementOccurs()) {
            hunger -= 0.005f;
        }

        if (hp > MAXHP) {
            hp = MAXHP;
        }

        if (hunger > MAXHUNGER) { //// block hunger at max
            hunger = MAXHUNGER;
        }

        if (hunger <= 0) { //// different speed depending on hunger
            hunger = 0;
            animationDuration = ANIMATION_NOFOOD;
        } else {
            animationDuration = ANIMATION_NORMAL;
        }


        if(keyboard.get(Keyboard.Q).isDown()){
            animationDuration = ANIMATION_SPRINT;
        }
        API.update(hp, hunger); ///// update hp for heart representation

        if (keyboard.get(Keyboard.M).isPressed()) { /// CheatCode for extension
            quests[0] = "done";
            quests[1] = "done";
            quests[2] = "done";
        }


        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        currentAnim.draw(canvas);
        if (getOwnerArea().getTitle() != "Endgame" && getOwnerArea().getTitle() != "GameOver") { /// if game is not ended draw GUI
            API.draw(canvas);
            if (currentItem != null) {
                API.draw(canvas, currentItem);
            }
            API.draw(canvas, inventory.getMontantArgent());
        }
    }


    /**
     * Set the new animation choosing from current Animation array depending on action being done
     *
     * @return void : set a new current Animation depending on orientation
     */
    public void setCurrentAnim() {

        /*

        //////// ORIENTATION .ordinal() //////////////////

        DOWN ---- 2
        UP   ---- 0
        RIGHT --- 1
        LEFT ---- 3

        /////////////////////////////////////////////////

         */
        currentAnim = currentAnimArray[getOrientation().ordinal()];
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public DiscreteCoordinates getCurrentMainCellCoordinates() {
        return super.getCurrentMainCellCoordinates();
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        Button toucheE = keyboard.get(Keyboard.E);
        if (etat == Etat.ATTAQUE_EPEE || toucheE.isDown() || etat == Etat.MINING) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor) v).interactWith(this);
    }

    @Override //// From interface Inventory holder
    public boolean possess(InventoryItem item) {
        return inventory.checkItem(item);
    }


    /**
     * Swaps current Item to next of inventory
     * if item last of the inventory, goes back to first item
     */
    public void nextItem() {
        if(!inventory.isEmpty()) {
            if (currentIndex + 1 < itemPossible.length) {
                currentIndex += 1;
            } else {
                currentIndex = 0;
            }
            currentIndex = itemSelector(currentIndex);
        }
        else{
            currentItem = null;
        }
    }

    /**
     * Select item from already known list. If items not present, goes on until finding a present item
     *
     * @return index of current item
     */
    public int itemSelector(int index){
        while(!possess(itemPossible[index])){
            ++index;
            if(index == itemPossible.length){
                index = 0;
            }
        }
        currentItem = itemPossible[index];
        return index;
    }

    /**
     * Change Animation array associated to current action, if player is doing nothing : basic animation,
     * if not animation related to usage of item is given
     *
     * @param etat (Etat)
     */
    public void setAnimArrayState(Etat etat) {
        switch (etat) {
            case IDLE:
                currentAnimArray = animationsDeplacement;
                break;
            case ATTAQUE_ARC:
                currentAnimArray = animationsBow;
                break;
            case ATTAQUE_EPEE:
                currentAnimArray = animationsSword;
                break;
            case MINING:
                currentAnimArray = animationsPioche;
                break;
            case ATTAQUE_SUPER_ARC:
                currentAnimArray = animationsBow;
                break;
            case ATTAQUE_BATONMAGIQUE:
                currentAnimArray = animationsBaton;
                break;
            default:
                currentAnimArray = animationsDeplacement;
                break;
        }
    }

    /**
     * If player pushed SPACE call this fonction --- call fonction to use item, if item used was last disponible switch to other item in inventory
     * Change animation depending which item was used
     */
    public void useItem() {
        if (currentItem != null) {
            if (etat == Etat.IDLE) {
                etat = Etat.setEtatName(currentItem.getName());
                setAnimArrayState(etat);
                setCurrentAnim();
                use(currentItem);
            }
            if (!possess(currentItem)) {
                nextItem();
            }
        }
    }

    /**
     * Enum representing different states of ARPGPlayer
     */
    private enum Etat {
        IDLE(null),
        ATTAQUE_EPEE("Sword"),
        ATTAQUE_ARC("Bow"),
        ATTAQUE_SUPER_ARC("Super Bow"),
        ATTAQUE_BATONMAGIQUE("Staff"),
        MINING("Pioche"),
        ;

        final String name;

        /**
         * Default State constructor
         *
         * @param name (String): name of item
         */
        Etat(String name) {
            this.name = name;
        }

        /**
         * @param name (String) name of state we are searching.
         * @return State with name we were searching for if it exists, else return basic State : IDLE
         */
        private static Etat setEtatName(String name) {
            for (Etat state : Etat.values()) {
                if (state.name == name)
                    return state;
            }
            return IDLE;
        }

    }

    /**
     * Do action associated to the use of an item --- Choose what to do depending on item
     * (switch)
     * If no item contained in inventory do nothing
     *
     * @param item (InventoryItem)
     */
    public void use(InventoryItem item) {
        DiscreteCoordinates facingCells = getCurrentMainCellCoordinates().jump(getOrientation().toVector()); /// Cells in front of player
        Area area = getOwnerArea();
        if (item != null) {

            switch (item.getName()) {
                case ("Bomb"): //// if item to use is bomb
                    Bomb bomb = new Bomb(area, Orientation.DOWN, facingCells, 100);
                    if (area.canEnterAreaCells(bomb, getFieldOfViewCells())) { //// If bomb can be placed
                        area.registerActor(bomb); ///// add bomb to area in front of player
                        inventory.removeItem(currentItem, 1); //// remove 1 bomb from inventory
                    }
                    break;
                case ("Bow"):
                    if (possess(ARPGItem.ARROW)) { /// if player has arrow in inventory
                        Arrow fleches = new Arrow(area, getOrientation(), facingCells, 3, 10);
                        if (area.canEnterAreaCells(fleches, getFieldOfViewCells())) { //// If arrow can be placed
                            area.registerActor(fleches);
                            inventory.removeItem(ARPGItem.ARROW, 1);
                        }
                    }
                    break;
                case ("Staff"):
                    MagicWaterProjectile waterProjectile = new MagicWaterProjectile(area, getOrientation(), facingCells, 3, 10);
                    if (area.canEnterAreaCells(waterProjectile, getFieldOfViewCells())) { //// If water projectile can be placed
                        area.registerActor(waterProjectile);
                    }
                    break;
                case ("Super Bow"):
                    SuperArrow superArrow = new SuperArrow(area, getOrientation(), facingCells, 2, 10);
                    if (area.canEnterAreaCells(superArrow, getFieldOfViewCells())) { //// If arrow can be placed
                        area.registerActor(superArrow);
                    }
                    break;
                case ("Steak"):
                    hunger += 1;
                    inventory.removeItem(ARPGItem.STEAK, 1);
                    break;
                case ("Pokeball orange"):
                    inventory.removeItem(ARPGItem.POKEBALL_ORANGE, 1);
                    area.registerActor(new Teleporter("Endgame", new DiscreteCoordinates(5, 3), Logic.TRUE, getOwnerArea(), getOrientation().opposite(), getCurrentMainCellCoordinates()));
                    orientate(Orientation.DOWN);
                    break;
            }
        } else {
            System.out.print("Empty"); /// if inventory empty
        }
    }

    /**
     * Checks if quests are completed
     *
     * @return true if quests completed, false if not
     */
    public boolean questsCompleted() {
        if (quests[0] == "done" && quests[1] == "done" && quests[2] == "done") {
            return true;
        }
        return false;
    }

    @Override
    public void bip(Audio audio) {
    }

    /**
     * Getter for "dead"
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Setter for hp
     */
    public void resetHp() {
        hp = MAXHP;
    }

    /**
     * Setter for hunger
     */
    public void resetHunger() {
        hunger = MAXHUNGER;
    }

    /**
     * Reset the inventory of player
     */
    public void resetInventory() { /// Starting inventory
        inventory.clearInventory();
        addStartingItems();
    }


    /**
     * Reset player (hp and dead indicator)
     */
    public void resetPlayer(){
        resetDead();
        resetHp();
        resetHunger();
        resetInventory();
        orientate(Orientation.DOWN);
        addMoney(moneyAmountStart- getMoney());
        resetQuests();
    }

    /**
     * Setter for "dead"
     */
    public void resetDead() {
        dead = false;
    }

    /**
     * Reset all quests
     */
    public void resetQuests() {
        quests[0] = "";
        quests[1] = "";
        quests[2] = "";
    }

    /**
     * Decrease hp
     *
     * @param nB of hp to take away
     */
    public void looseHp(float nB) {
        hp -= nB;
    }

    /**
     * Increase hp
     *
     * @param nB of hp to give
     */
    public void addHP(float nB) {
        hp += nB;
    }

    /**
     * Increase money amount
     *
     * @param nB of money to give
     */
    private void addMoney(int nB) {
        inventory.addMoney(nB);
    }

    /**
     * Decrease money amount
     *
     * @param nB of money to take away
     */
    public void removeMoney(int nB) {
        inventory.removeMoney(nB);
    }

    /**
     * Returns money amount
     */
    public int getMoney() {
        return inventory.getMontantArgent();
    }

    /**
     * Handles all interaction for the type ARPGPlayer
     */
    private class ARPGPlayerHandler implements ARPGInteractionVisitor {


        @Override
        public void interactWith(Door door) {
            setIsPassingADoor(door);
        }

        @Override
        public void interactWith(CastleDoor castleDoor) {
            if (castleDoor.isOpen()) {
                setIsPassingADoor(castleDoor);
                castleDoor.setSignal(Logic.FALSE);
            } else {
                if (possess(ARPGItem.CASTLEKEY)) {
                    castleDoor.setSignal(Logic.TRUE);
                }

            }
        }

        @Override
        public void interactWith(TempleDoor templeDoor) {
            if (templeDoor.isOpen()) {
                setIsPassingADoor(templeDoor);
            }
        }

        @Override
        public void interactWith(Grass grass) {
            if (keyboard.get(Keyboard.SPACE).isDown()) {
                if(etat == Etat.ATTAQUE_EPEE) { /// this was added to separate case of sword attack and mining with pickaxe
                    grass.cutGrass();
                }
            }
        }

        @Override
        public void interactWith(Coin coin) {
            coin.pickup();
            addMoney(5);
        }

        @Override
        public void interactWith(Heart heart) {
            heart.pickup();
            addHP(1);
        }

        @Override
        public void interactWith(CastleKey key) {
            if (inventory.addItem(ARPGItem.CASTLEKEY, 1)) {
                key.pickup();
            }
        }

        @Override
        public void interactWith(Monstre monstre) {
            if (etat == Etat.ATTAQUE_EPEE)
                if (monstre.isVulnerableTo("physique")) {
                    monstre.loosehp(5);
                }
        }


        @Override
        public void interactWith(Bomb bomb) {
            bomb.explodeBomb();
        }

        @Override
        public void interactWith(King king) {
            king.Dialog();
        }

        @Override
        public void interactWith(Lever lever) {
            lever.useLever();
        }

        @Override
        public void interactWith(Pokeball pokeball) {
            pokeball.pickup();
            if (pokeball.getColor() == "red") {
                inventory.addItem(ARPGItem.POKEBALL_RED, 1);
                quests[0] = "done";
            }
            if (pokeball.getColor() == "blue") {
                inventory.addItem(ARPGItem.POKEBALL_BLUE, 1);
                quests[1] = "done";
            }
            if (pokeball.getColor() == "green") {
                inventory.addItem(ARPGItem.POKEBALL_GREEN, 1);
                quests[2] = "done";
            }
        }

        @Override
        public void interactWith(SuperBow arbalete) {
            if (inventory.addItem(ARPGItem.SUPER_BOW, 1)) {
                arbalete.pickup();
            }
        }

        @Override
        public void interactWith(Assistant assistant) {
            assistant.Dialog();
        }

        @Override
        public void interactWith(PoliceMan police) {
            police.Dialog(ARPGPlayer.this);
        }

        @Override
        public void interactWith(ShopAssistant shopAssistant) {
            shopAssistant.Dialog(ARPGPlayer.this);
            if (questsCompleted() && !rewardEarned) {
                inventory.clearInventory();
                inventory.addItem(ARPGItem.POKEBALL_ORANGE, 1);
                currentIndex = itemSelector(7); /// index of pokeball
                inventory.addMoney(500);
                rewardEarned = true;
            }
        }

        @Override
        public void interactWith(Rock rock) {
            if (etat == Etat.MINING) {
                rock.mine();
            }
        }

        @Override
        public void interactWith(Pioche pioche) {
            pioche.pickup();
            inventory.addItem(ARPGItem.PIOCHE, 1);
        }
    }


}
