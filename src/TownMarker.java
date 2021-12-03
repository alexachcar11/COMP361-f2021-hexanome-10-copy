public class TownMarker {

    public boolean conquered;
    public String townName;

    /**
     * CONSTRUCTOR : Creates a TownMarker object (the cubes)
     * @param townName town in which the townmarker is placed
     * @param conquered boolean: true if the marker has been conquered by the player, false otherwise (i.e. still on the board)
     */
    public TownMarker(String townName, boolean conquered){
        this.conquered = false;
        this.townName = townName;
    }
}
