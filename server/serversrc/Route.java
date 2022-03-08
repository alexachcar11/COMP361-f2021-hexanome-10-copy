package serversrc;

import javax.management.RuntimeErrorException;

// import clientsrc.Player;
// import clientsrc.Token;
// import clientsrc.Town;

public class Route {

    // route needs to have a starting town and an ending town
    // routes carry one transportation counter maximum, but don't need to have one

    Town aStartingTown;
    Town aEndTown;
    Token aToken; // TODO: there could be multiple tokens, list ?
    // road or river
    boolean isRiver = false;
    // upstream
    boolean isUpstream;
    private RouteType type;
    private static final int[][] COST_ARRAY = {
            { 1, 1, 2, 0, 1, 1, 0, 0 },
            { 1, 1, 2, 1, 2, 2, 0, 0 },
            { 0, 0, 0, 2, 2, 1, 0, 0 },
            { 0, 2, 1, 1, 2, 1, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 1, 2 }
    };

    Route(Town pStartingTown, Town pEndTown) {
        this.aStartingTown = pStartingTown;
        this.aEndTown = pEndTown;
        this.aToken = null;
    }

    // overload if it's a river
    // n = 0 means it's downstream, n = 1 means it's upstream
    Route(Town pStartingTown, Town pEndTown, int n) {
        this.isRiver = true;
        if (n == 1) {
            this.isUpstream = true;
        } else if (n == 0) {
            this.isUpstream = false;
        }
        // if n is not 1 or 0
        else {
            throw new IllegalArgumentException();
        }
        this.aStartingTown = pStartingTown;
        this.aEndTown = pEndTown;
        this.aToken = null;
    }

    public boolean getisRiver() {
        return isRiver;
    }

    // sets Upstream with a boolean
    public void setUpstream(boolean b) {
        isUpstream = b;
    }

    public Town getSource() {
        return this.aStartingTown;
    }

    public Town getDest() {
        return this.aEndTown;
    }

    /**
     * Place a token on a valid route.
     * 
     * Ensure that the route isn't already occupied and that the token exists
     * 
     * Check that counter is already placed before adding Obstacle
     * 
     * @param token
     */
    public void placeToken(Token token) {
        assert token != null;
        if (this.aToken != null) {
            if (token.isObstacle()) {
                ((Obstacle) token).setTokenOnPath(this.aToken);
                this.aToken = token;
            } else
                throw new RuntimeException("Cannot add more than one travel counter to a route.");
        } else {
            if (!token.isObstacle()) {
                this.aToken = token;
            } else
                throw new RuntimeException("Cannot add an obstacle without first placing a travel counter.");
        }
    }

    public void clearToken() {
        this.aToken = null;
    }

    /**
     * 
     * @param cardType
     * @return cost to travel on path with a given CardType, assuming no obstacles
     */
    public int costWithCardType(CardType cardType) {
        return cardType == CardType.RAFT && isUpstream ? COST_ARRAY[type.ordinal()][cardType.ordinal() + 1]
                : COST_ARRAY[type.ordinal()][cardType.ordinal()];
    }

    /**
     * @pre this.aToken != null
     * @return cost to travel the path with correct cards, in current state
     */
    public int cost() {
        assert aToken != null;
        return aToken.cost();
    }
}
