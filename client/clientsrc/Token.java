package clientsrc;
/*
Abstract class for Tokens
 */


import org.minueto.image.MinuetoImage;

public abstract class Token extends Image{

    /**
     * CONSTRUCTOR : Creates a Hitbox object.
     *
     * @param minX  left-most border of the image
     * @param maxX  right-most border of the image
     * @param minY  bottom-most border of the image
     * @param maxY  top-most border of the image
     * @param image MinuetoImage to display
     */
    public Token(int minX, int maxX, int minY, int maxY, MinuetoImage image) {
        super(minX, maxX, minY, maxY, image);
    }
}
