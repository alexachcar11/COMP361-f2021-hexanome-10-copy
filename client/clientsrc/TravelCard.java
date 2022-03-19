package clientsrc;

import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

// 10x Unicorn, Troll Wagon, Elfcycle, Magic Cloud, Giant Pig
// 12x Raft, 10x Dragon

public class TravelCard extends Card {

    CardType aType;
    MinuetoImage mediumImage;
    MinuetoImage smallImage;
    String mediumAddress;
    String smallAddress;

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
    // public TravelCard(int minX, int maxX, int minY, int maxY, MinuetoImage image,
    // String name, CardType pType) {
    public TravelCard(CardType pType) throws MinuetoFileException {
        // super(minX, maxX, minY, maxY, image, name);
        aType = pType;
        if (aType == CardType.CLOUD) {
            mediumImage = new MinuetoImageFile("images/elfenroads-sprites/T03medium.png");
            smallImage = new MinuetoImageFile("images/elfenroads-sprites/T03small.png");
            mediumAddress = "images/elfenroads-sprites/T03medium.png";
            smallAddress = "images/elfenroads-sprites/T03small.png";
        } else if (aType == CardType.DRAGON) {
            mediumImage = new MinuetoImageFile("images/elfenroads-sprites/T06medium.png");
            smallImage = new MinuetoImageFile("images/elfenroads-sprites/T06small.png");
            mediumAddress = "images/elfenroads-sprites/T06medium.png";
            smallAddress = "images/elfenroads-sprites/T06small.png";
        } else if (aType == CardType.ELFCYCLE) {
            mediumImage = new MinuetoImageFile("images/elfenroads-sprites/T02medium.png");
            smallImage = new MinuetoImageFile("images/elfenroads-sprites/T02small.png");
            mediumAddress = "images/elfenroads-sprites/T02medium.png";
            smallAddress = "images/elfenroads-sprites/T02small.png";
        } else if (aType == CardType.PIG) {
            mediumImage = new MinuetoImageFile("images/elfenroads-sprites/T01medium.png");
            smallImage = new MinuetoImageFile("images/elfenroads-sprites/T01small.png");
            mediumAddress = "images/elfenroads-sprites/T01medium.png";
            smallAddress = "images/elfenroads-sprites/T01small.png";
        } else if (aType == CardType.RAFT) {
            mediumImage = new MinuetoImageFile("images/elfenroads-sprites/T07medium.png");
            smallImage = new MinuetoImageFile("images/elfenroads-sprites/T07small.png");
            mediumAddress = "images/elfenroads-sprites/T07medium.png";
            smallAddress = "images/elfenroads-sprites/T07small.png";
        } else if (aType == CardType.TROLL) {
            mediumImage = new MinuetoImageFile("images/elfenroads-sprites/T05medium.png");
            smallImage = new MinuetoImageFile("images/elfenroads-sprites/T05small.png");
            mediumAddress = "images/elfenroads-sprites/T05medium.png";
            smallAddress = "images/elfenroads-sprites/T05small.png";
        } else if (aType == CardType.UNICORN) {
            mediumImage = new MinuetoImageFile("images/elfenroads-sprites/T04medium.png");
            smallImage = new MinuetoImageFile("images/elfenroads-sprites/T04small.png");
            mediumAddress = "images/elfenroads-sprites/T04medium.png";
            smallAddress = "images/elfenroads-sprites/T04small.png";
        }
        // needs gold and witch when we do elfengold
    }

    public MinuetoImage getMediumImage() {
        return mediumImage;
    }

    public MinuetoImage getSmallImage() {
        return smallImage;
    }

    public String getMediumAddress() {
        return mediumAddress;
    }

    public String getSmallAddress() {
        return smallAddress;
    }

}
