/*
Instances of the Hitbox class contain a min/max x and min/max y coordinates to locate the object on the screen.
 */


public class Hitbox {

    int minX;
    int maxX;
    int minY;
    int maxY;

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
}
