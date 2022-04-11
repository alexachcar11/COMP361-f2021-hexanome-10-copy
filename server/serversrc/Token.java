package serversrc;

import java.io.Serializable;
import java.util.Optional;

public class Token implements Serializable {
    // type of counter
    private final CardType tokenType;
    private transient Optional<Route> route = Optional.empty();
    private boolean isFaceUp;

    public Token(CardType pCT) {
        this.tokenType = pCT;
        this.isFaceUp = true;
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

    @Override
    public String toString() {
        return this.tokenType.toString();
    }
}
