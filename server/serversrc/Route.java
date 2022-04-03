package serversrc;

import java.util.ArrayList;
import java.util.List;

public class Route {

    //
    private Town source;
    private Town dest;
    Token aToken; // TODO: there could be multiple tokens, list ?
    // upstream
    boolean isUpstream;
    private RouteType type;
    private int[] hitbox;

    Route(Town pStartingTown, Town pEndTown, RouteType rType, int[] pHitbox) {
        // Route(Town pStartingTown, Town pEndTown, RouteType rType) {
        this.source = pStartingTown;
        this.dest = pEndTown;
        this.aToken = null;
        this.type = rType;
        this.hitbox = pHitbox;
    }

    // overload if it's a river
    // n = 0 means it's downstream, n = 1 means it's upstream
    Route(Town pStartingTown, Town pEndTown, boolean upstream, int[] pHitbox) {
        this.isUpstream = upstream;
        this.source = pStartingTown;
        this.dest = pEndTown;
        this.aToken = null;
        this.type = RouteType.RIVER;
        this.hitbox = pHitbox;
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

    // public int[] getHitBox() {
    // return hitbox;
    // }

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
        if (this.type == RouteType.RIVER || this.type == RouteType.LAKE) {
            throw new RuntimeException("Cannot add transportation counters to waterways.");
        }
        if (this.aToken != null) {
            if (token.isObstacle()) {
                ((Obstacle) token).setTokenOnPath(this.aToken);
                this.aToken = token;
                this.aToken.setRoute(this);
            } else
                throw new RuntimeException("Cannot add more than one travel counter to a route.");
        } else if (!token.isObstacle()) {
            this.aToken = token;
            this.aToken.setRoute(this);
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
    public int costWithCardType(CardType cardType, Town startingTown) {
        // startingTown doesn't matter if it's a lake
        if (this.type == RouteType.LAKE) {
            return 2;
        }
        return cardType == CardType.RAFT && this.isUpstream(startingTown)
                ? CostCard.getCost(type.ordinal(), cardType.ordinal() + 1)
                : CostCard.getCost(type.ordinal(), cardType.ordinal());
    }

    /**
     * @pre this.aToken != null
     * @return cost to travel the path with correct cards, in current state
     */
    public int cost(Town startingTown) {
        assert aToken != null;
        if (this.aToken.isObstacle())
            return costWithCardType(this.aToken.getTokenType(), startingTown) + 1;
        else
            return costWithCardType(this.aToken.getTokenType(), startingTown);
    }

    // reset route's token delets obstacle from game and returns it
    public Token removeToken() {
        Token temp = this.aToken;
        if (temp == null)
            return null;
        if (temp.isObstacle()) {
            temp = ((Obstacle) temp).getInnerToken();
        }
        temp.resetRoute();
        this.aToken = null;
        return temp;
    }

    // DIJIAN'S VERSION OF GETREQUIREDCARDS
    // // @pre should check if there's a counter on road first
    // // (extra note, check region type is valid for the counter when placing
    // counters
    // // so that means counter will be valid at this stage no matter the region)
    // public List<AbstractCard> getRequiredCards(Town currTown){
    // List<AbstractCard> output = new ArrayList<>();
    // // Card goldCard = new Card(CardType.GOLD);
    // // witch card ? don't think it goes here...

    // // for river and lake
    // if (aRegion.equals(MapRegion.RIVER)){
    // Card raftCard = new Card(CardType.RAFT);
    // output.add(raftCard);
    // // check for upstream/downstream
    // if
    // (currTown.getTownName().equalsIgnoreCase(this.aStartingTown.getTownName())){
    // if (isUpstream){
    // // add a raft card
    // output.add(raftCard);
    // }
    // }
    // else{
    // if (!isUpstream){
    // output.add(raftCard);
    // }
    // }
    // }
    // else{
    // switch( this.aCounter.getCounterType()){

    // case CLOUD:
    // Card cloudCard = new Card(CardType.CLOUD);
    // // if there's an obstacle, add an extra one
    // if(this.hasObstacle){
    // output.add(cloudCard);
    // }

    // // check by region
    // switch(aRegion){
    // case MOUNTAIN:
    // output.add(cloudCard);
    // break;
    // case PLAIN:
    // output.add(cloudCard);
    // output.add(cloudCard);
    // break;
    // case WOOD:
    // output.add(cloudCard);
    // output.add(cloudCard);
    // break;
    // default:
    // break;

    // }
    // break;

    // case DRAGON:
    // Card dragonCard = new Card(CardType.DRAGON);
    // if(this.hasObstacle){
    // output.add(dragonCard);
    // }

    // switch(aRegion){
    // case DESERT:
    // output.add(dragonCard);
    // break;
    // case MOUNTAIN:
    // output.add(dragonCard);
    // break;
    // case PLAIN:
    // output.add(dragonCard);
    // break;
    // case WOOD:
    // output.add(dragonCard);
    // output.add(dragonCard);
    // break;
    // default:
    // break;
    // }
    // break;

    // case ELFCYCLE:
    // Card elfcyleCard = new Card(CardType.ELFCYCLE);
    // if(this.hasObstacle){
    // output.add(elfcyleCard);
    // }

    // switch(aRegion){
    // case MOUNTAIN:
    // output.add(elfcyleCard);
    // output.add(elfcyleCard);
    // break;
    // case PLAIN:
    // output.add(elfcyleCard);
    // break;
    // case WOOD:
    // output.add(elfcyleCard);
    // break;
    // default:
    // break;

    // }
    // break;
    // // TODO: for elfengold
    // case GOLD:

    // break;

    // case PIG:
    // Card pigCard = new Card(CardType.PIG);
    // if(this.hasObstacle){
    // output.add(pigCard);
    // }

    // switch(aRegion){
    // case PLAIN:
    // output.add(pigCard);
    // break;
    // case WOOD:
    // output.add(pigCard);
    // break;
    // default:
    // break;
    // }
    // break;
    // case TROLL:
    // Card trollCard = new Card(CardType.TROLL);
    // if(this.hasObstacle){
    // output.add(trollCard);
    // }

    // switch(aRegion){
    // case DESERT:
    // output.add(trollCard);
    // output.add(trollCard);
    // break;
    // case MOUNTAIN:
    // output.add(trollCard);
    // output.add(trollCard);
    // break;
    // case PLAIN:
    // output.add(trollCard);
    // break;

    // case WOOD:
    // output.add(trollCard);
    // output.add(trollCard);
    // break;
    // default:
    // break;
    // }
    // break;

    // case UNICORN:
    // Card unicornCard = new Card(CardType.UNICORN);
    // if(this.hasObstacle){
    // output.add(unicornCard);
    // }

    // switch(aRegion){
    // case DESERT:
    // output.add(unicornCard);
    // output.add(unicornCard);
    // break;
    // case MOUNTAIN:
    // output.add(unicornCard);
    // break;
    // case WOOD:
    // output.add(unicornCard);
    // break;
    // default:
    // break;
    // }
    // break;
    // // case WITCH:
    // // break;
    // default:
    // break;

    public List<AbstractCard> getRequiredCards(Town startingTown) {
        List<AbstractCard> toReturn = new ArrayList<>();
        for (int i = 0; i < this.cost(startingTown); i++) {
            toReturn.add(new TravelCard(this.aToken.getTokenType()));
        }
        return toReturn;
    }

    public boolean isUpstream(Town startingTown) {
        return startingTown == this.source && this.type == RouteType.RIVER;
    }

    public boolean hasCounter() {
        return (!(this.aToken == null));
    }

    public Town getSourceTown() { 
        return source;
    }

    public String getSourceTownString() { 
        return source.getTownName();
    }

    public Town getDestTown() { 
        return source;
    }

    public String getDestTownString() { 
        return source.getTownName();
    }

    public int getMinX(){ 
        return hitbox[0];
    }

    public int getMaxX(){ 
        return hitbox[1];
    }

    public int getMinY(){ 
        return hitbox[2];
    }

    public int getMaxY(){ 
        return hitbox[3];
    }
}
