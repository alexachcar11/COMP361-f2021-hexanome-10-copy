package clientsrc;

import java.util.ArrayList;

import networksrc.Server;
import serversrc.Player;
// import serversrc.Town;
// import serversrc.TownMarker;
import serversrc.ServerGame;
import serversrc.Town;
import serversrc.Route;

public class ClientTown {

    // fields
    private String townName;
    int minX;
    int maxX;
    int minY;
    int maxY;
    ArrayList<TownMarker> townMarkers = new ArrayList<>();
    ArrayList<ClientPlayer> playersThatPassed = new ArrayList<>();

    // keeps track of the player boots that are on the town
    ArrayList<ClientPlayer> playersHere = new ArrayList<>();

    // keeps track of all towns so we can search them by name
    private static ArrayList<ClientTown> allTowns = new ArrayList<>();
    private int goldValue;

    /**
     * CONSTRUCTOR : Creates a Town object
     * 
     * @param townName town's name
     * @param minX     left-most border of the town
     * @param maxX     right-most border of the town
     * @param minY     bottom-most border of the town
     * @param maxY     top-most border of the town
     */

    public ClientTown(String townName, int minX, int maxX, int minY, int maxY) {
        this.townName = townName;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        // if(Game.getNumberOfPlayers()) {
        this.goldValue = goldValue;

        // }
        allTowns.add(this);
    }

    public static ClientTown getTownByName(String stringTown) {
        for (ClientTown t : allTowns) {
            if (t.getTownName().equalsIgnoreCase(stringTown)) {
                return t;
            }
        }
        // no town with such name
        return null;
    }

    /**
     * GETTER : get the town's name
     * 
     * @return townName
     */
    public String getTownName() {
        return townName;
    }

    // 2 towns are the same if they have same name
    public boolean equal(ClientTown t) {
        return t.getTownName().equalsIgnoreCase(this.townName);
    }

    /**
     * GETTER : get the left-most border of the town
     * 
     * @return minX
     */
    public int getMinX() {
        return minX;
    }

    /**
     * GETTER : get the right-most border of the town
     * 
     * @return minX
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * GETTER : get the bottom-most border of the town
     * 
     * @return minX
     */
    public int getMinY() {
        return minY;
    }

    /**
     * GETTER : get the top-most border of the town
     * 
     * @return minX
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * Adds a new player to the town
     * Function called each time a player moves -> call this function on the town
     * 
     * @param player
     */
    public void addPlayer(ClientPlayer player) {
        playersHere.add(player);
        playersThatPassed.add(player);
    }

    /**
     * Removes a player from the town
     * Function called on the specific town when a player moves their boot away from
     * the town.
     * 
     * @param player
     */
    public void removePlayer(ClientPlayer player) {
        playersHere.remove(player);
    }

    /**
     * Returns the location of the town reflected as a list of the format [min x,
     * min y, max x, max y]
     * 
     * @return
     */
    public int[] getLocation() {

        int[] location = new int[4];

        location[0] = getMinX();
        location[1] = getMinY();
        location[2] = getMaxX();
        location[3] = getMaxY();

        return location;
    }

    public ArrayList<ClientPlayer> getPlayersThatPassed() {
        return playersThatPassed;
    }

    public int getGoldValue() {
        return this.goldValue;
    }

    public void setGoldValue(int value) {
        this.goldValue = value;
    }

    public Town getServerTown() {
        Town returnTown = null;
        for (Town t : ServerGame.towns) {
            if (t.getTownName().equals(this.townName)) {
                returnTown = t;
            }
        }
        return returnTown;
    }

    /**
     * Return all ClientRoutes associated with this town
     * @return ArrayList<ClientRoute> associated with this town
     */
    public ArrayList<ClientRoute> getRoutes() { 

        // initialize list of routes for return 
        ArrayList<ClientRoute> listOfRoutes = new ArrayList<>(); 

        // get all routes that are going out of the current town 
        // look over all routes 
        for(ClientRoute r : Game.getAllRoutes()) { 
            if(r.getDest().equals(this) || r.getSource().equals(this)) { 
                listOfRoutes.add(r);
            }
        }

        return listOfRoutes;
    }

    public int getNumberPlayersHere() {
        return playersHere.size();
    }

    // public boolean notClickingOnATown(int x, int y) {
    // for(Town t : ServerGame.getTowns()) {

    // }
    // }

    // public ArrayList<Route> getRoutes() {

    // // initialize list of routes for return
    // ArrayList<Route> listOfRoutes = new ArrayList<>();

    // // get all routes that are going out of the current town
    // // look over all routes
    // for(Route r : getAllRoutes()) {
    // if(r.getDestTown().equals(this) || r.getSourceTown().equals(this)) {
    // listOfRoutes.add(r);
    // }
    // }

    // return listOfRoutes;
    // }
}
