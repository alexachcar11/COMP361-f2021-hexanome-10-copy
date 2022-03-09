package serversrc;

import java.util.Optional;

import org.minueto.MinuetoFileException;

import clientsrc.TokenImage;

public class Token {
    // type of counter
    private final CardType tokenType;
    private Optional<Route> route = Optional.empty();
    private TokenImage tokenImageFile;
    private boolean isFaceUp;

    public Token(CardType pCT) {
        this.tokenType = pCT;
        this.isFaceUp = true;
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

    public void resetRoute(){
        this.route = Optional.empty();
    }

    public boolean getIsFaceUp(){
        return this.isFaceUp;
    }

    public void setIsFaceUpTo(boolean bool){
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
