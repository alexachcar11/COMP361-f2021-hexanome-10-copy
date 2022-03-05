package clientsrc;
import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

// import serversrc.Card;
// import serversrc.CardType;

// 10x Unicorn, Troll Wagon, Elfcycle, Magic Cloud, Giant Pig
// 12x Raft, 10x Dragon

public class TravelCard extends Card{

     // field
     CardType aType;
     // requirements for different regions (same as on the game's transportation chart)
     // for index: 0 = plain, 1 = wood, 2 = desert, 3 = mountain, 4 = river (river has 1 requirement, but if it's updraft will need to add 1 more)
     // values indicate requirement depending on type of travel card, and 0 means can't travel on that region
     int[] requirements = new int[5];
     MinuetoImage medium;
     MinuetoImage small;

     /**
     * CONSTRUCTOR : Creates a Hitbox object.
     *
     * @param minX  left-most border of the image
     * @param maxX  right-most border of the image
     * @param minY  bottom-most border of the image
     * @param maxY  top-most border of the image
     * @param image MinuetoImage to display
     * @throws MinuetoFileException
     */
    // public TravelCard(int minX, int maxX, int minY, int maxY, MinuetoImage image, String name, CardType pType) {
    public TravelCard(CardType pType) throws MinuetoFileException {
        // super(minX, maxX, minY, maxY, image, name);
        aType = pType;
        // set up requirements depending on type
        switch (aType) {
            case CLOUD: 
                requirements[0] = 2; 
                requirements[1] = 2; 
                requirements[3] = 1; 
                medium = new MinuetoImageFile("images/elfenroads-sprites/T03medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/T03small.png");
                break;
            case UNICORN: 
                requirements[1] = 1; 
                requirements[2] = 2; 
                requirements[3] = 1; 
                medium = new MinuetoImageFile("images/elfenroads-sprites/T04medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/T04small.png");
                break;
            case TROLL: 
                requirements[0] = 1; 
                requirements[1] = 2; 
                requirements[2] = 2; 
                requirements[3] = 2; 
                medium = new MinuetoImageFile("images/elfenroads-sprites/T05medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/T05small.png");
                break;
            case ELFCYCLE: 
                requirements[0] = 1;   
                requirements[1] = 1; 
                requirements[3] = 2; 
                medium = new MinuetoImageFile("images/elfenroads-sprites/T02medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/T02small.png");
                break;
            case PIG: 
                requirements[0] = 1; 
                requirements[1] = 1; 
                medium = new MinuetoImageFile("images/elfenroads-sprites/T01medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/T01small.png");
                break;
            case DRAGON: 
                requirements[0] = 1; 
                requirements[1] = 2; 
                requirements[2] = 1; 
                requirements[3] = 1; 
                medium = new MinuetoImageFile("images/elfenroads-sprites/T06medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/T06small.png");
                break;
            case RAFT: 
                medium = new MinuetoImageFile("images/elfenroads-sprites/T07medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/T07small.png");
                requirements[4] = 1; break;
        }
    }

    public MinuetoImage getMediumImage() { 
        return medium;
    }

    public MinuetoImage getSmallImage() { 
        return small;
    }
}
