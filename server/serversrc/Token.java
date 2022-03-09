package serversrc;

import java.util.Optional;

import org.minueto.MinuetoFileException;

import clientsrc.TokenImage;

public class Token {
    // type of counter
    private final CardType tokenType;
    private Optional<Route> route = Optional.empty();
    private TokenImage tokenImageFile;

    public Token(CardType pCT) {
        this.tokenType = pCT;
        try {
            this.tokenImageFile = new TokenImage(this.tokenType.toString(),
                    "images/elfenroads-sprites/M0" + (pCT.ordinal() + 1) + ".png");
        } catch (MinuetoFileException e) {
            e.printStackTrace();
        }
    }

    public CardType getTokenType() {
        return tokenType;
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

    public TokenImage getTokenImage() {
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
}
