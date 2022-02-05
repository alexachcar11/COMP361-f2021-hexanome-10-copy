import org.minueto.image.MinuetoImage;

// 10x Unicorn, Troll Wagon, Elfcycle, Magic Cloud, Giant Pig
// 12x Raft, 10x Dragon

public class TravelCard extends Card{

     // field
     CardType aType;
     // requirements for different regions (same as on the game's transportation chart)
     // for index: 0 = plain, 1 = wood, 2 = desert, 3 = mountain, 4 = river (river has 1 requirement, but if it's updraft will need to add 1 more)
     // values indicate requirement depending on type of travel card, and 0 means can't travel on that region
     int[] requirements = new int[5];

     /**
     * CONSTRUCTOR : Creates a Hitbox object.
     *
     * @param minX  left-most border of the image
     * @param maxX  right-most border of the image
     * @param minY  bottom-most border of the image
     * @param maxY  top-most border of the image
     * @param image MinuetoImage to display
     */
    public TravelCard(int minX, int maxX, int minY, int maxY, MinuetoImage image, String name, CardType pType) {
        super(minX, maxX, minY, maxY, image, name);
        aType = pType;
        // set up requirements depending on type
        switch(aType){
            case CLOUD: requirements[0] = 2; requirements[1] = 2; requirements[3] = 1; break;
            case UNICORN: requirements[1] = 1; requirements[2] = 2; requirements[3] = 1; break;
            case TROLL: requirements[0] = 1; requirements[1] = 2; requirements[2] = 2; requirements[3] = 2; break;
            case ELFCYCLE: requirements[0] = 1; requirements[1] = 1; requirements[3] = 2; break;
            case PIG: requirements[0] = 1; requirements[1] = 1; break;
            case DRAGON: requirements[0] = 1; requirements[1] = 2; requirements[2] = 1; requirements[3] = 1; break;
            case RAFT: requirements[4] = 1; break;
        }
    }
}
