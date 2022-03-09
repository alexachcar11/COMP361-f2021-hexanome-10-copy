package serversrc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// import clientsrc.Player;
// import clientsrc.Token;
// import clientsrc.Town;

public class Route {

    //
    private Town source;
    private Town dest;
    Token aToken; // TODO: there could be multiple tokens, list ?
    // upstream
    boolean isUpstream;
    private RouteType type;

    Route(Town pStartingTown, Town pEndTown, RouteType rType) {
        this.source = pStartingTown;
        this.dest = pEndTown;
        this.aToken = null;
        this.type = rType;
    }

    // overload if it's a river
    // n = 0 means it's downstream, n = 1 means it's upstream
    Route(Town pStartingTown, Town pEndTown, boolean upstream) {
        this.isUpstream = upstream;
        this.source = pStartingTown;
        this.dest = pEndTown;
        this.aToken = null;
        this.type = RouteType.RIVER;
    }

    // sets Upstream with a boolean
    public void setUpstream(boolean b) {
        isUpstream = b;
    }

    public Town getSource() {
        return source;
    }

    public Town getDest() {
        return dest;
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
        } else if (!token.isObstacle()) {
            this.aToken = token;
        } else
            throw new RuntimeException("Cannot add an obstacle without first placing a travel counter.");
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
        return cardType == CardType.RAFT && isUpstream ? CostCard.getCost(type.ordinal(), cardType.ordinal() + 1)
                : CostCard.getCost(type.ordinal(), cardType.ordinal());
    }

    /**
     * @pre this.aToken != null
     * @return cost to travel the path with correct cards, in current state
     */
    public int cost() {
        assert aToken != null;
        return aToken.cost();
    }

    public List<AbstractCard> getRequiredCards(Town town) {
        List<AbstractCard> toReturn = new ArrayList<>();
        for (int i = 0; i < this.cost(); i++) {
            toReturn.add(new TravelCard(this.aToken.getTokenType()));
        }
        return toReturn;
    }
}
