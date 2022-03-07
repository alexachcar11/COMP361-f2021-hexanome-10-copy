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
    String mediumAddress;
    String smallAddress;

    public TransportationCounter(MinuetoImage image, CardType pType) throws MinuetoFileException {
        super(image);
        aType = pType;
        switch (aType) {
            case CLOUD: 
                mediumAddress = "images/elfenroads-sprites/M03medium.png";
                smallAddress = "images/elfenroads-sprites/M03small.png";

                medium = new MinuetoImageFile("images/elfenroads-sprites/M03medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/M03small.png");
                break;
            case UNICORN: 
                mediumAddress = "images/elfenroads-sprites/M04medium.png";
                smallAddress = "images/elfenroads-sprites/M04small.png";

                medium = new MinuetoImageFile("images/elfenroads-sprites/M04medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/M04small.png");
                break;
            case TROLL: 

                mediumAddress = "images/elfenroads-sprites/M05medium.png";
                smallAddress = "images/elfenroads-sprites/M05small.png";
            
                medium = new MinuetoImageFile("images/elfenroads-sprites/M05medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/M05small.png");
                break;
            case ELFCYCLE: 
                mediumAddress = "images/elfenroads-sprites/M02medium.png";
                smallAddress = "images/elfenroads-sprites/M02small.png";

                medium = new MinuetoImageFile("images/elfenroads-sprites/M02medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/M02small.png");
                break;
            case PIG: 
                mediumAddress = "images/elfenroads-sprites/M01medium.png";
                smallAddress = "images/elfenroads-sprites/M01small.png";

                medium = new MinuetoImageFile("images/elfenroads-sprites/M01medium.png");
                small = new MinuetoImageFile("images/elfenroads-sprites/M01small.png");
                break;
            case DRAGON: 
                mediumAddress = "images/elfenroads-sprites/M06medium.png";
                smallAddress = "images/elfenroads-sprites/M06small.png";

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

    public String getMediumAddress() { 
        return mediumAddress;
    }

    public String getSmallAddress() { 
        return smallAddress;
    }

    public CardType getType(){
        return aType;
    }
}
