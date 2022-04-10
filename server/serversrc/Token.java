package serversrc;

import java.io.Serializable;
import java.util.Optional;

import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

import clientsrc.TokenSprite;

public class Token implements Serializable{
    // type of counter
    private final CardType tokenType;
    private Optional<Route> route = Optional.empty();
    private TokenSprite tokenImageFile;
    private MinuetoImage mediumImage;
    private MinuetoImage smallImage;
    private String mediumAddress;
    private String smallAddress;
    private boolean isFaceUp;

    public Token(CardType pCT) {
        this.tokenType = pCT;
        this.isFaceUp = true;
        try {
            this.tokenImageFile = new TokenSprite(this.tokenType);
            mediumImage = new MinuetoImageFile("images/elfenroads-sprites/M0" + (pCT.ordinal() + 1) + "medium.png");
            smallImage = new MinuetoImageFile("images/elfenroads-sprites/M0" + (pCT.ordinal() + 1) + "small.png");
            mediumAddress = "images/elfenroads-sprites/M0" + (pCT.ordinal() + 1) + "medium.png";
            smallAddress = "images/elfenroads-sprites/M0" + (pCT.ordinal() + 1) + "small.png";
        } catch (MinuetoFileException e) {
            e.printStackTrace();
        }
    }

    public static Token getTokenByName(String tokenName) {
        for (CardType cT : CardType.values()) {
            if (cT.toString().equals(tokenName)) {
                return new Token(cT);
            }
        }
        throw new IllegalArgumentException(tokenName + "is not a valid token type.");
    }

    public CardType getTokenType() {
        return tokenType;
    }

    public void resetRoute() {
        this.route = Optional.empty();
    }

    public boolean getIsFaceUp() {
        return this.isFaceUp;
    }

    public void setIsFaceUpTo(boolean bool) {
        this.isFaceUp = bool;
    }

    /**
     * sets the route that the token is placed on
     * 
     * @param route
     */
    public void setRoute(Route route) {
        this.route = Optional.ofNullable(route);
    }

    /**
     * overridden in Obstacle class
     * 
     * @return false
     */
    public boolean isObstacle() {
        return false;
    }

    public TokenSprite getTokenImage() {
        return this.tokenImageFile;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Token)) {
            return false;
        }
        Token toCompare = (Token) o;
        return this.tokenType == toCompare.tokenType;
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

    @Override
    public String toString() {
        return this.tokenType.toString();
    }
}
