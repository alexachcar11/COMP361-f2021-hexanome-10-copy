package clientsrc;

import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

import serversrc.CardType;

// make this implement serializable at some point
public class TokenSprite extends AbstractSprite {

    private CardType tokenType;
    private MinuetoImage mediumImage;
    private MinuetoImage smallImage;
    

    /**
     * CONSTRUCTOR : Creates a Hitbox object.
     *
     * @param minX  left-most border of the image
     * @param maxX  right-most border of the image
     * @param minY  bottom-most border of the image
     * @param maxY  top-most border of the image
     * @param image MinuetoImage to display
     */
    public TokenSprite(CardType tokenType) throws MinuetoFileException {
        super("images/elfenroads-sprites/M0" + (tokenType.ordinal() + 1) + "small.png");
        this.tokenType = tokenType;
        this.x = 0;
        this.y = 0;
        mediumImage = new MinuetoImageFile("images/elfenroads-sprites/M0" + (tokenType.ordinal() + 1) + "medium.png");
        smallImage = new MinuetoImageFile("images/elfenroads-sprites/M0" + (tokenType.ordinal() + 1) + "small.png");
        
    }

    /**
     * 
     * @return file location of image as a string
     */
    @Override
    public String getFileAddress() {
        CardType cT = this.getTokenType();
        return "images/elfenroads-sprites/M0" + (cT.ordinal() + 1) + "small.png";
    }

    /**
     * 
     * @return returns the name of the token, which is the same as the CardType it
     *         represents as a string
     */
    @Override
    public String getTokenName() {
        return tokenType.toString();
    }

    /**
     * 
     * @param tokenString
     * @return new tokenImage representing CardType matching tokenString
     * @throws MinuetoFileException
     * @throws IllegalArgumentException if tokenString does not match a CardType
     */
    public static TokenSprite getTokenSpriteByString(String tokenString)
            throws MinuetoFileException, IllegalArgumentException {
        for (CardType cT : CardType.values()) {
            if (cT.toString().equals(tokenString)) {
                return new TokenSprite(cT);
            }
        }
        throw new IllegalArgumentException(tokenString + " is not a valid type for a token.");
    }

    /**
     * 
     * @return associated CardType
     */
    public CardType getTokenType() {
        return this.tokenType;
    }

    public MinuetoImage getMediumImage() { 
        return mediumImage;
    }

    public MinuetoImage getSmallImage() { 
        return smallImage;
    }

    @Override
    public boolean equals(Object o){
        // If the object is compared with itself then return true 
        if (o == this) {
            return true;
        }
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof TokenSprite)) {
            return false;
        }
        TokenSprite ts = (TokenSprite) o;

        return this.getTokenName().equalsIgnoreCase(ts.getTokenName());
    }
}
