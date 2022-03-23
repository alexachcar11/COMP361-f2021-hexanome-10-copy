package clientsrc;

// import serversrc.Player;

public class TownMarker {

    public ClientPlayer aPlayer;
    public String townName;

    /**
     * CONSTRUCTOR : Creates a TownMarker object (the cubes)
     * 
     * @param townName town in which the townmarker is placed
     */
    public TownMarker(String townName, ClientPlayer pPlayer) {
        this.aPlayer = pPlayer;
        this.townName = townName;
    }
}
