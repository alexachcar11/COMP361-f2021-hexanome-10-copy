package clientsrc;

import org.minueto.MinuetoFileException;
import serversrc.CardType;

public class CardSprite extends AbstractSprite {

    private CardType aType;

    /**
     * @throws MinuetoFileException
     */
    public CardSprite(CardType pType) throws MinuetoFileException {
        super("images/elfenroads-sprites/T0" + (pType.ordinal() + 1) + ".png");
        aType = pType;
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
}
