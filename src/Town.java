public class Town {

    //fields
    private String townName;
    int minX;
    int maxX;
    int minY;
    int maxY;

    /**
     * CONSTRUCTOR : Creates a Town object
     * @param townName town's name
     * @param minX left-most border of the town
     * @param maxX right-most border of the town
     * @param minY bottom-most border of the town
     * @param maxY top-most border of the town
     */
    public Town(String townName, int minX, int maxX, int minY, int maxY) {
        this.townName = townName;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    /**
     * GETTER : get the town's name
     * @return townName
     */
    public String getTownName() {
        return townName;
    }

    /**
     * GETTER : get the left-most border of the town
     * @return minX
     */
    public int getMinX() {
        return minX;
    }

    /**
     * GETTER : get the right-most border of the town
     * @return minX
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * GETTER : get the bottom-most border of the town
     * @return minX
     */
    public int getMinY() {
        return minY;
    }

    /**
     * GETTER : get the top-most border of the town
     * @return minX
     */
    public int getMaxY() {
        return maxY;
    }
}
