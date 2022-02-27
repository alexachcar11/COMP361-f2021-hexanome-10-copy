package clientsrc;
import org.minueto.image.MinuetoImage;

// import serversrc.Card;
// import serversrc.CardType;

public class GoldCard extends Card{

    //field
    CardType aType = CardType.GOLD;

    /**
     * CONSTRUCTOR : Creates a Hitbox object.
     *
     * @param minX  left-most border of the image
     * @param maxX  right-most border of the image
     * @param minY  bottom-most border of the image
     * @param maxY  top-most border of the image
     * @param image MinuetoImage to display
     */
    public GoldCard(int minX, int maxX, int minY, int maxY, MinuetoImage image) {
        super(minX, maxX, minY, maxY, image, "GoldCard");
    }

    // for each Gold Card, they give player 3 gold if they choose the gold card stack
    public int getGold(){
        return 3;
    }
}
