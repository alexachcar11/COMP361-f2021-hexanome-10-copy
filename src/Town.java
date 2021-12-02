public class Town {



    //fields
    private String townName;
    private Hitbox townHitbox;

    /**
     * CONSTRUCTOR : Creates a TownMarker object (the cubes)
     * @param townName town in which the townmarker is placed
     * @param townHitbox Hitbox: an array of integers representing the town's hitbox on the game window
     */
    //constructor
    public Town(String townName, Hitbox townHitbox){
        this.townName = townName;
        this.townHitbox = new Hitbox(0,0,0,0);

    }

    // get functions
    public Hitbox getTownHitbox() {
        return townHitbox;
    }
    public String getTownName(){
        return townName;
    }
}
