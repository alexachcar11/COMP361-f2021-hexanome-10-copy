package serversrc;

import java.util.Optional;

import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImageFile;

public class Token {
    // type of counter
    private final CardType tokenType;
    private boolean faceDown;
    private Optional<Route> route = Optional.empty();
    private MinuetoImageFile tokenImageFile;

    public Token(CardType pCT) {
        this.tokenType = pCT;
        this.faceDown = false;
        try {
            this.tokenImageFile = new MinuetoImageFile("images/elfenroads-sprites/M0" + (pCT.ordinal() + 1) + ".png");
        } catch (MinuetoFileException e) {
            e.printStackTrace();
        }
    }

    public CardType getTokenType() {
        return tokenType;
    }

    public boolean isFaceDown() {
        return this.faceDown;
    }

    public void setFaceDown() {
        this.faceDown = true;
    }

    public void setFaceUp() {
        this.faceDown = false;
    }

    /**
     * 
     * @return number of cards needed to cross route
     */
    public int cost() {
        assert route.isPresent();
        return route.get().costWithCardType(tokenType);
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

    public MinuetoImageFile getMinuetoImageFile() {
        return this.tokenImageFile;
    }
}
