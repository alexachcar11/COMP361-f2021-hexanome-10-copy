import org.minueto.image.MinuetoImage;

public class TransportationCounter extends Token {

    // field
    CardType aType;
    
    /**
     * CONSTRUCTOR : Creates a Hitbox object.
     *
     * @param minX  left-most border of the image
     * @param maxX  right-most border of the image
     * @param minY  bottom-most border of the image
     * @param maxY  top-most border of the image
     * @param image MinuetoImage to display
     */

    public TransportationCounter(int minX, int maxX, int minY, int maxY, MinuetoImage image, CardType pType) {
        super(minX, maxX, minY, maxY, image);
        aType = pType;
    }
    public CardType getType(){
        return aType;
    }
}
