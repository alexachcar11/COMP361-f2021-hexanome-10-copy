package clientsrc;
import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

import org.minueto.image.MinuetoImage;

// 10x Unicorn, Troll Wagon, Elfcycle, Magic Cloud, Giant Pig
// 12x Raft, 10x Dragon

public class TravelCard extends Card {

    CardType aType;

    /**
     * CONSTRUCTOR : Creates a Hitbox object.
     *
     * @param minX  left-most border of the image
     * @param maxX  right-most border of the image
     * @param minY  bottom-most border of the image
     * @param maxY  top-most border of the image
     * @param image MinuetoImage to display
     * @throws MinuetoFileException
     */
    // public TravelCard(int minX, int maxX, int minY, int maxY, MinuetoImage image, String name, CardType pType) {
    public TravelCard(CardType pType) throws MinuetoFileException {
        // super(minX, maxX, minY, maxY, image, name);
        aType = pType;
    }

    // public MinuetoImage getMediumImage() { 
    //     return medium;
    // }

    // public MinuetoImage getSmallImage() { 
    //     return small;
    // }

    // public String getMediumAddress() { 
    //     return mediumAddress;
    // }

    // public String getSmallAddress() { 
    //     return smallAddress;
    // }

    // public Boolean isFaceDown() { 
    //     return faceDown;
    // }
}
