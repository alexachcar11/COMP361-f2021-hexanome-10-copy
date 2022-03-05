package clientsrc;
import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

// import serversrc.CardType;
// import serversrc.Token;

public class TransportationCounter extends Token {

    // field
    CardType aType;
    MinuetoImage medium; 
    MinuetoImage small;

    public TransportationCounter(MinuetoImage image, CardType pType) throws MinuetoFileException {
        super(image);
        aType = pType;
        switch (aType) {
            case CLOUD: 
                medium = new MinuetoImageFile("images/elfenroads-sprites/M03medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/M03small.png");
                break;
            case UNICORN: 
                
                medium = new MinuetoImageFile("images/elfenroads-sprites/M04medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/M04small.png");
                break;
            case TROLL: 
                
                medium = new MinuetoImageFile("images/elfenroads-sprites/M05medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/M05small.png");
                break;
            case ELFCYCLE: 
                
                medium = new MinuetoImageFile("images/elfenroads-sprites/M02medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/M02small.png");
                break;
            case PIG: 
                
                medium = new MinuetoImageFile("images/elfenroads-sprites/M01medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/M01small.png");
                break;
            case DRAGON: 
                
                medium = new MinuetoImageFile("images/elfenroads-sprites/M06medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/M06small.png");
                break;
        }
    }

    public MinuetoImage getMediumImage() { 
        return medium;
    }

    public MinuetoImage getSmallImage() { 
        return small;
    }

    public CardType getType(){
        return aType;
    }
}
