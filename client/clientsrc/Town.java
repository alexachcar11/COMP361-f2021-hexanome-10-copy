package clientsrc;

import java.util.ArrayList;

// import serversrc.Player;
// import serversrc.Town;
// import serversrc.TownMarker;

public class Town {

    //fields
    private String townName;
    int minX;
    int maxX;
    int minY;
    int maxY;
    ArrayList<TownMarker> townMarkers = new ArrayList<>();

    // keeps track of the player boots that are on the town
    ArrayList<Player> playersHere = new ArrayList<>();

    // keeps track of all towns so we can search them by name
    private static ArrayList<Town> allTowns = new ArrayList<>();

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
        // if(Game.getNumberOfPlayers()) { 

        // }
        allTowns.add(this);
    }

    public static Town getTownByName(String stringTown){
        for (Town t: allTowns){
            if (t.getTownName().equalsIgnoreCase(stringTown)){
                return t;
            }
        }
        // no town with such name
        return null;
    }

    /**
     * GETTER : get the town's name
     * @return townName
     */
    public String getTownName() {
        return townName;
    }

    // 2 towns are the same if they have same name
    public boolean equal(Town t){
        return t.getTownName().equalsIgnoreCase(this.townName);
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

    /**
     * Adds a new player to the town
     * Function called each time a player moves -> call this function on the town 
     * @param player
     */
    public void addPlayer(Player player) { 
        playersHere.add(player);

    }

    /**
     * Removes a player from the town 
     * Function called on the specific town when a player moves their boot away from the town.
     * @param player
     */
    public void removePlayer(Player player) { 
        playersHere.remove(player);
    }

    /**
     * Returns the location of the town reflected as a list of the format [min x, min y, max x, max y]
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

    // public boolean notClickingOnATown(int x, int y) { 
    //     for(Town t : ServerGame.getTowns()) { 

    //     }
    // }
}
