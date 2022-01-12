/*
Instances of the Image class contain a min/max x and min/max y coordinates to locate the object on the screen
and an image to display
 */

import org.minueto.image.MinuetoImage;

// should extend minuetoimage i think
public class Image {

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private MinuetoImage image;

    /**
     * CONSTRUCTOR : Creates a Hitbox object.
     * 
     * @param minX  left-most border of the image
     * @param maxX  right-most border of the image
     * @param minY  bottom-most border of the image
     * @param maxY  top-most border of the image
     * @param image MinuetoImage to display
     */
    public Image(int minX, int maxX, int minY, int maxY, MinuetoImage image) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.image = image;
    }

    /**
     * GETTER : Returns the left-most border of the image
     * 
     * @return minX
     */
    public int getMinX() {
        return minX;
    }

    /**
     * SETTER: set the left-most border of the image to minX
     * 
     * @param minX left-most border of the image
     */
    public void setMinX(int minX) {
        this.minX = minX;
    }

    /**
     * GETTER : Returns the right-most border of the image
     * 
     * @return maxX right-most border of the image
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * SETTER: set the right-most border of the image to maxX
     * 
     * @param maxX top-most border of the image
     */
    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    /**
     * GETTER : Returns the top-most border of the image
     * 
     * @return minY top-most border of the image
     */
    public int getMinY() {
        return minY;
    }

    /**
     * SETTER: set the top-most border of the image to minY
     * 
     * @param minY top-most border of the image
     */
    public void setMinY(int minY) {
        this.minY = minY;
    }

    /**
     * GETTER : Returns the bottom-most border of the image
     * 
     * @return maxY bottom-most border of the image
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * SETTER: set the bottom-most border of the image to maxY
     * 
     * @param maxY bottom-most border of the image
     */
    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public MinuetoImage getMImage() {
        return image;
    }
}
