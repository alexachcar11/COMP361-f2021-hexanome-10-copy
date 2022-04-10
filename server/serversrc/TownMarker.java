package serversrc;

import java.io.Serializable;

// import clientsrc.Player;

public class TownMarker implements Serializable{

    public Player aPlayer;
    public String townName;

    /**
     * CONSTRUCTOR : Creates a TownMarker object (the cubes)
     * @param townName town in which the townmarker is placed
     */
    public TownMarker(String townName, Player pPlayer){
        this.aPlayer = pPlayer;
        this.townName = townName;
    }
}
