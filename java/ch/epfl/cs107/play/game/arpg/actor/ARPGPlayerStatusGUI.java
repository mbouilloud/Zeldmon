package ch.epfl.cs107.play.game.arpg.actor;


import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.rpg.Inventory.InventoryItem;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;


public class ARPGPlayerStatusGUI implements Graphics {

    private float hp;
    private float MAXHP;
    private float hunger;
    private float MAXHUNGER;


    /**
     * Default ARPGPlayerStatusGUI constructor
     *
     * @param hp (float): Player hp
     */
    public ARPGPlayerStatusGUI(float hp, float maxHp, float hunger, float maxHunger) {
        this.hp = hp;
        this.MAXHP = maxHp;
        this.hunger = hunger;
        this.MAXHUNGER = maxHunger;
    }


    private static final float DEPTH = 1001;

    /**
     * hp and hunger updater
     *
     * @param hp (float)
     * @param hunger (float)
     */
    public void update(float hp, float hunger) {
        this.hp = hp;
        this.hunger = hunger;
    }

    @Override
    public void draw(Canvas canvas) {
        ImageGraphics gearDisplay = gearDisplay(canvas); /// create Image of gear Display
        ImageGraphics[] heartDisplay = heartDisplay(canvas); // create Image array for heart
        ImageGraphics coinsDisplay = coinsDisplay(canvas); /// create Image of coin Display
        ImageGraphics[] hungerDisplay = hungerDisplay(canvas); /// create Image of hunger Display

        gearDisplay.draw(canvas); /// draw gearDisplay
        for (int i = 0; i < MAXHP; i++) {
            heartDisplay[i].draw(canvas); /// Add 5 heart in order (full, partial, empty heart)
        }
        for (int i = 0; i < MAXHUNGER; i++) {
            hungerDisplay[i].draw(canvas); /// Add 5 steak in order (full, partial, empty heart)
        }
        coinsDisplay.draw(canvas); /// draw coinsDisplay
    }

    /**
     * Same thing as draw() but with different parameters
     */
    public void draw(Canvas canvas, InventoryItem item) {
        ImageGraphics currentItem = currentItem(canvas, item); /// create Image for current Item
        currentItem.draw(canvas);
    }

    /**
     * Same thing as draw() but with different parameters
     */
    public void draw(Canvas canvas, int playermoney) {
        String moneyString = Integer.toString(playermoney);
        int length;
        if(moneyString.length() == 1){
            length = moneyString.length() + 2; /// number of digits of amount + 2 zeros we add

        }
        else{
            if(moneyString.length() == 2){
                length = moneyString.length() + 1; /// number of digits of amount + 1 zeros we add
            }
            else{
                length = moneyString.length(); /// number of digits of amount
            }
        }
        ImageGraphics[] money = money(canvas, playermoney);/// Create Image array for money amount, each index a digit (image)
        for (int i = 0; i < length; i++) {
            money[i].draw(canvas); /// print every digit of amount
        }
    }

    /**
     * Create ImageGraphics for current item
     *
     * @param canvas (Canvas)
     * @param item   (InventoryItem) of player
     * @return image (ImageGraphics)
     */
    public ImageGraphics currentItem(Canvas canvas, InventoryItem item) {
        float width = canvas.getScaledWidth();
        float height = canvas.getScaledHeight();

        String spriteName = item.getSpriteName();

        int imageRegionWidth = 16;
        int imageRegionHeight = 16;

        Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width / 2, height / 2));

        if(item.getName() == "Super Bow") {
            imageRegionHeight = 150;
            imageRegionWidth = 150;
        }

        if(item.getName() == "Steak") {
            imageRegionHeight = 800;
            imageRegionWidth = 800;
        }

        if(item.getName() != "Pioche") {
            ImageGraphics itemImage = new
                    ImageGraphics(ResourcePath.getSprite(spriteName),
                    0.9f, 0.9f, new RegionOfInterest(0, 0, imageRegionWidth, imageRegionHeight),
                    anchor.add(new Vector(0.3f, height - 1.45f)), 1, DEPTH);

            return itemImage;
        }
        else{ /// Image of pickaxe not same format
            ImageGraphics itemImage = new
                    ImageGraphics(ResourcePath.getSprite(spriteName),
                    0.8f, 0.8f, new RegionOfInterest(187, 319, 800, 830),
                    anchor.add(new Vector(0.35f, height - 1.45f)), 1, DEPTH);

            return itemImage;
        }

    }

    /**
     * Create ImageGraphics array for amount of money (each index a digit)
        *
     * @param canvas (Canvas)
     * @param money  (int) of player
     * @return image (ImageGraphics)
     */
    public ImageGraphics[] money(Canvas canvas, int money) {
        String moneyStringTemporary = Integer.toString(money);

        String addZero = "00";
        String addZero1 = "0";

        if(moneyStringTemporary.length()  == 1){
            moneyStringTemporary = addZero + moneyStringTemporary;
        }
        else {
            if (moneyStringTemporary.length() == 2) {
                moneyStringTemporary = addZero1 + moneyStringTemporary;
            }
        }

        String moneyString = moneyStringTemporary;

        float spaceBetweenNumbers;

        switch (moneyString.length()){
            case 4:
                spaceBetweenNumbers = 0.56f;
                break;
            case 5:
                spaceBetweenNumbers = 0.49f;
                break;
            default:
                spaceBetweenNumbers = 0.75f;
                break;
        }

        ImageGraphics[] numeroImageTab = new ImageGraphics[moneyString.length()];  /// Array

        for (int i = 0; i < moneyString.length(); i++) {
            numeroImageTab[i] = numero(canvas, Integer.parseInt(moneyString.substring(i, i + 1)), 1.5f + i * spaceBetweenNumbers); /// for each digit calls a fonction that creates an ImageGraphics associated to digit(0-9)
            /// decalage is a shift between each number for display
        }

        return numeroImageTab;
    }


    /**
     * Create ImageGraphics for gear Display
     *
     * @param canvas (Canvas)
     * @return image (ImageGraphics)
     */
    public ImageGraphics gearDisplay(Canvas canvas) {
        float width = canvas.getScaledWidth();
        float height = canvas.getScaledHeight();
        Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width / 2, height / 2));
        ImageGraphics gearDisplay = new
                ImageGraphics(ResourcePath.getSprite("zelda/gearDisplay"),
                1.5f, 1.5f, new RegionOfInterest(0, 0, 32, 32),
                anchor.add(new Vector(0, height - 1.75f)), 1, DEPTH);

        return gearDisplay;
    }

    /**
     * Create ImageGraphics for coins Display
     *
     * @param canvas (Canvas)
     * @return image (ImageGraphics)
     */
    public ImageGraphics coinsDisplay(Canvas canvas) {
        float width = canvas.getScaledWidth();
        float height = canvas.getScaledHeight();
        Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width / 2, height / 2));
        ImageGraphics gearDisplay = new
                ImageGraphics(ResourcePath.getSprite("zelda/coinsDisplay"),
                4, 1.85f, new RegionOfInterest(0, 0, 64, 32),
                anchor.add(new Vector(0.05f, 0)), 1, DEPTH);

        return gearDisplay;
    }

    /**
     * Create ImageGraphics array  for coins Display
     *
     * @param canvas (Canvas)
     * @return image (ImageGraphics)
     */
    public ImageGraphics[] heartDisplay(Canvas canvas) {


        int coeurEntier = (int) hp; //// number of full heart

        int coeurPartiel = (int) ((hp - (int) hp) * 2); //// number of partial heart

        int coeurVide = (int)MAXHP - coeurEntier - coeurPartiel; //// number of empty heart


        float width = canvas.getScaledWidth();
        float height = canvas.getScaledHeight();

        Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width / 2, height / 2));
        ImageGraphics[] heartDisplay = new ImageGraphics[(int)MAXHP];
        for (int i = 0; i < coeurEntier; i++) { /// Creates Image for full heart and add it to array
            heartDisplay[i] = new ImageGraphics(ResourcePath.getSprite("zelda/heartDisplay"),
                    1f, 1f, new RegionOfInterest(32, 0, 16, 16),
                    anchor.add(new Vector(1.7f + i, height - 1.5f)), 1, DEPTH);
        }

        for (int i = coeurEntier; i < coeurEntier + coeurPartiel; i++) { /// Creates Image for partial heart and add it to array
            heartDisplay[i] = new ImageGraphics(ResourcePath.getSprite("zelda/heartDisplay"),
                    1f, 1f, new RegionOfInterest(16, 0, 16, 16),
                    anchor.add(new Vector(1.7f + i, height - 1.6f)), 1, DEPTH);
        }

        for (int i = coeurEntier + coeurPartiel; i < coeurEntier + coeurPartiel + coeurVide; i++) {/// Creates Image for empty heart and add it to array
            heartDisplay[i] = new ImageGraphics(ResourcePath.getSprite("zelda/heartDisplay"),
                    1f, 1f, new RegionOfInterest(0, 0, 16, 16),
                    anchor.add(new Vector(1.7f + i, height - 1.6f)), 1, DEPTH);
        }

        return heartDisplay;
    }

    /**
     * Create ImageGraphics  for a digit between 0 and 9
     *
     * @param canvas   (Canvas)
     * @param digit    (int) we want to get Image
     * @param decalage (float) space between numbers
     * @return image (ImageGraphics)
     */
    public ImageGraphics numero(Canvas canvas, int digit, float decalage) {
        float width = canvas.getScaledWidth();
        float height = canvas.getScaledHeight();
        ImageGraphics numeroGraphics = null;
        Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width / 2, height / 2));
        switch (digit) { ///switch to find which fonction we call depending on digit we want to get an Image
            case (1):
                numeroGraphics = decoupageNumero(canvas, 0, 0, 16, 16, decalage, anchor);
                break;
            case (2):
                numeroGraphics = decoupageNumero(canvas, 16, 0, 16, 16, decalage, anchor);
                break;

            case (3):
                numeroGraphics = decoupageNumero(canvas, 32, 0, 16, 16, decalage, anchor);
                break;

            case (4):
                numeroGraphics = decoupageNumero(canvas, 48, 0, 16, 16, decalage, anchor);
                break;

            case (5):
                numeroGraphics = decoupageNumero(canvas, 0, 16, 16, 16, decalage, anchor);
                break;

            case (6):
                numeroGraphics = decoupageNumero(canvas, 16, 16, 16, 16, decalage, anchor);
                break;

            case (7):
                numeroGraphics = decoupageNumero(canvas, 32, 16, 16, 16, decalage, anchor);
                break;

            case (8):
                numeroGraphics = decoupageNumero(canvas, 48, 16, 16, 16, decalage, anchor);
                break;

            case (9):
                numeroGraphics = decoupageNumero(canvas, 0, 32, 16, 16, decalage, anchor);
                break;

            case (0):
                numeroGraphics = decoupageNumero(canvas, 16, 32, 16, 16, decalage, anchor);
                break;
        }
        return numeroGraphics;
    }

    /**
     * Cut (choose a region) the png file of all digits ("zelda/digits") and create ImageGraphics of digit
     *
     * @param canvas     (Canvas)
     * @param x          , y (int) Coordinates where Image starts
     * @param widthImage , heigthImage (int) Coordinates where Image ends
     * @param decalage   (float) space we add in X coordinates away from 0 for anchor
     * @param anchor     (Vector) anchor associated to Image
     * @return image (ImageGraphics)
     */
    public ImageGraphics decoupageNumero(Canvas canvas, int x, int y, int widthImage, int heigthImage, float decalage, Vector anchor) {
        return new ImageGraphics(ResourcePath.getSprite("zelda/digits"),
                0.85f, 0.85f, new RegionOfInterest(x, y, widthImage, heigthImage),
                anchor.add(new Vector(0 + decalage, 0.5f)), 1, DEPTH);

    }

    /**
     * Create ImageGraphics array for hunger
     *
     * @param canvas   (Canvas)
     * @return image array (ImageGraphics[])
     */
    public ImageGraphics[] hungerDisplay(Canvas canvas) {

        int fullSteak = (int) hunger; //// number of full heart

        int partialSteak = (int) ((hunger - (int) hunger) * 2); //// number of partial heart

        int emptySteak = (int)MAXHUNGER - fullSteak - partialSteak; //// number of empty heart


        float width = canvas.getScaledWidth();
        float height = canvas.getScaledHeight();

        Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width / 2, height / 2));
        ImageGraphics[] hungerDisplay = new ImageGraphics[(int)MAXHUNGER];

        for (int i = 0; i < fullSteak; i++) { /// Creates Image for full heart and add it to array
            hungerDisplay[i] = new ImageGraphics(ResourcePath.getSprite("zelda/hunger"),
                    1f, 1f, new RegionOfInterest(0, 0, 143, 140),
                    anchor.add(new Vector(1.8f + i, height - 2.5f)), 1, DEPTH);
        }

        for (int i = fullSteak; i < fullSteak + partialSteak; i++) { /// Creates Image for partial heart and add it to array
            hungerDisplay[i] = new ImageGraphics(ResourcePath.getSprite("zelda/hunger"),
                    1f, 1f, new RegionOfInterest(143, 0, 143, 140),
                    anchor.add(new Vector(1.8f + i, height - 2.5f)), 1, DEPTH);
        }

        for (int i = fullSteak + partialSteak; i < fullSteak + partialSteak + emptySteak; i++) {/// Creates Image for empty heart and add it to array
            hungerDisplay[i] = new ImageGraphics(ResourcePath.getSprite("zelda/hunger"),
                    1f, 1f, new RegionOfInterest(286, 0, 143, 140),
                    anchor.add(new Vector(1.8f + i, height - 2.5f)), 1, DEPTH);
        }

        return hungerDisplay;
    }
}
