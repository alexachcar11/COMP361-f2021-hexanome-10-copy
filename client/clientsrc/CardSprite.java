package clientsrc;

import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

import serversrc.CardType;

public class CardSprite extends AbstractSprite {

    private CardType aType;
    private MinuetoImage mediumImage;
    private MinuetoImage smallImage;

    /**
     * @throws MinuetoFileException
     */
    public CardSprite(CardType pType) throws MinuetoFileException {
        super("images/elfenroads-sprites/T0" + (pType.ordinal() + 1) + ".png");
        aType = pType;
        mediumImage = new MinuetoImageFile("images/elfenroads-sprites/T0" + (pType.ordinal() + 1) + "medium.png");
        smallImage = new MinuetoImageFile("images/elfenroads-sprites/T0" + (pType.ordinal() + 1) + "small.png");
        
    }

    @Override
    public String getFileAddress() {
        return "images/elfenroads-sprites/T0" + (this.aType.ordinal() + 1) + ".png";
    }

    @Override
    public String getTokenName() {
        return this.aType.toString();
    }

    public CardSprite getCardSpriteByString(String cardString)
            throws MinuetoFileException, IllegalArgumentException {
        for (CardType cT : CardType.values()) {
            if (cT.toString().equals(cardString)) {
                return new CardSprite(cT);
            }
        }
        throw new IllegalArgumentException(cardString + " is not a valid type for a card.");
    }

    public MinuetoImage getMediumImage() { 
        return mediumImage;
    }

    public MinuetoImage getSmallImage() { 
        return smallImage;
    }
    
}
