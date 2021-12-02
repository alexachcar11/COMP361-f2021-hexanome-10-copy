/*
Instances of the Hitbox class contain a min/max x and min/max y coordinates to locate the object on the screen.
 */


public class Hitbox {

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    /**
     * CONSTRUCTOR : Creates a Hitbox object.
     * @param minX left-most border of the image
     * @param maxX right-most border of the image
     * @param minY bottom-most border of the image
     * @param maxY top-most border of the image
     */
    public Hitbox(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    /**
     * GETTER : Returns the left-most border of the image
     * @return minX
     */
    public int getMinX() {
        return minX;
    }

    /**
     * SETTER: set the left-most border of the image to minX
     * @param minX left-most border of the image
     */
    public void setMinX(int minX) {
        this.minX = minX;
    }

    /**
     * GETTER : Returns the right-most border of the image
     * @return maxX right-most border of the image
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * SETTER: set the right-most border of the image to maxX
     * @param maxX top-most border of the image
     */
    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    /**
     * GETTER : Returns the bottom-most border of the image
     * @return minY bottom-most border of the image
     */
    public int getMinY() {
        return minY;
    }

    /**
     * SETTER: set the bottom-most border of the image to minY
     * @param minY bottom-most border of the image
     */
    public void setMinY(int minY) {
        this.minY = minY;
    }

    /**
     * GETTER : Returns the top-most border of the image
     * @return maxY top-most border of the image
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * SETTER: set the top-most border of the image to maxY
     * @param maxY top-most border of the image
     */
    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }
}
