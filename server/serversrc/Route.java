package serversrc;

import java.util.ArrayList;
import java.util.List;


public class Route {
    
    // route needs to have a starting town and an ending town 
    // routes carry one transportation counter maximum, but don't need to have one 
    
    Town aStartingTown;
    Town aEndTown;
    TransportationCounter aCounter;       // TODO: there could be multiple tokens, list ?
    // road or river
    boolean isRiver = false;
    // upstream
    boolean isUpstream;
    MapRegion aRegion;
    // might be subject to change 
    boolean hasObstacle;

    Route(Town pStartingTown, Town pEndTown, MapRegion pRegion){ 
        this.aStartingTown = pStartingTown;
        this.aEndTown = pEndTown;
        this.aCounter = null;
        this.aRegion = pRegion;
        this.hasObstacle = false;
    }

    // overload if it's a river
    // n = 0 means it's downstream, n = 1 means it's upstream
    Route(Town pStartingTown, Town pEndTown, MapRegion pRegion, int n){
        this.isRiver = true;
        if(n == 1){
            this.isUpstream = true;
        }
        else if(n == 0){
            this.isUpstream = false;
        }
        // if n is not 1 or 0
        else {
            throw new IllegalArgumentException();
        }
        this.aStartingTown = pStartingTown;
        this.aEndTown = pEndTown;
        this.aCounter = null;
        this.aRegion = pRegion;
        this.hasObstacle = false;
    }

    public boolean getisRiver(){
        return this.aRegion.equals(MapRegion.RIVER);
    }

    // sets Upstream with a boolean
    public void setUpstream(boolean b){
        isUpstream = b;
    }

    public Town getSource(){
        return this.aStartingTown;
    }

    public Town getDest(){
        return this.aEndTown;
    }

    /**
     * Place a token on a valid route. 
     * 
     * Ensure that the route isn't already occupied and that the token exists
     * 
     * @param token
     */
    public void placeToken(Player player, Token pCounter) { 
        assert pCounter != null;

        if(this.aCounter == null) { 
            throw new IllegalArgumentException();
        } else { 
            player.consumeToken(pCounter);
            if (pCounter instanceof Obstacle){
                placeObstacle(player);
            }
            else{
                this.aCounter = (TransportationCounter) pCounter;
            }
            
        }
    }

    public void placeObstacle(Player player){
        //remove obstacle from player's hand
        player.removeObstacle();
        // add obstacle to route
        this.hasObstacle = true;
    }

    // reset route's token delets obstacle from game and returns it
    public Token removeToken() { 
        Token temp = this.aCounter;
        this.aCounter = null;
        this.hasObstacle = false;
        return temp;
    }

    // @pre should check if there's a counter on road first 
    // (extra note, check region type is valid for the counter when placing counters
    // so that means counter will be valid at this stage no matter the region)
    public List<AbstractCard> getRequiredCards(Town currTown){
        List<AbstractCard> output = new ArrayList<>();
        // Card goldCard = new Card(CardType.GOLD);
        // witch card ? don't think it goes here...
        
        // for river and lake
        if (aRegion.equals(MapRegion.RIVER)){
            Card raftCard = new Card(CardType.RAFT);
            output.add(raftCard);
            // check for upstream/downstream
            if (currTown.getTownName().equalsIgnoreCase(this.aStartingTown.getTownName())){
                if (isUpstream){
                    // add a raft card
                    output.add(raftCard);
                }
            }
            else{
                if (!isUpstream){
                    output.add(raftCard);
                }
            }
        }
        else{
            switch( this.aCounter.getCounterType()){

                case CLOUD:
                    Card cloudCard = new Card(CardType.CLOUD);
                    // if there's an obstacle, add an extra one
                    if(this.hasObstacle){
                        output.add(cloudCard);
                    }

                    // check by region
                    switch(aRegion){
                        case MOUNTAIN:
                            output.add(cloudCard);
                            break;
                        case PLAIN:
                            output.add(cloudCard);
                            output.add(cloudCard);
                            break;
                        case WOOD:
                            output.add(cloudCard);
                            output.add(cloudCard);
                            break;
                        default:
                            break;
                        
                    }
                    break;

                case DRAGON:
                    Card dragonCard = new Card(CardType.DRAGON);
                    if(this.hasObstacle){
                        output.add(dragonCard);
                    }

                    switch(aRegion){
                        case DESERT:
                            output.add(dragonCard);
                            break;
                        case MOUNTAIN:
                            output.add(dragonCard);
                            break;
                        case PLAIN:
                            output.add(dragonCard);
                            break;
                        case WOOD:
                            output.add(dragonCard);
                            output.add(dragonCard);
                            break;
                        default:
                            break;
                    }
                    break;

                case ELFCYCLE:
                    Card elfcyleCard = new Card(CardType.ELFCYCLE);
                    if(this.hasObstacle){
                        output.add(elfcyleCard);
                    }

                    switch(aRegion){
                        case MOUNTAIN:
                            output.add(elfcyleCard);
                            output.add(elfcyleCard);
                            break;
                        case PLAIN:
                            output.add(elfcyleCard);
                            break;
                        case WOOD:
                            output.add(elfcyleCard);
                            break;
                        default:
                            break;
                        
                    }
                    break;
                // TODO: for elfengold
                case GOLD:

                    break;

                case PIG:
                    Card pigCard = new Card(CardType.PIG);
                    if(this.hasObstacle){
                        output.add(pigCard);
                    }

                    switch(aRegion){
                        case PLAIN:
                            output.add(pigCard);
                            break;
                        case WOOD:
                            output.add(pigCard);
                            break;
                        default:
                            break;
                    }
                    break;
                case TROLL:
                    Card trollCard = new Card(CardType.TROLL);
                    if(this.hasObstacle){
                        output.add(trollCard);
                    }

                    switch(aRegion){
                        case DESERT:
                            output.add(trollCard);
                            output.add(trollCard);
                            break;
                        case MOUNTAIN:
                            output.add(trollCard);
                            output.add(trollCard);
                            break;
                        case PLAIN:
                            output.add(trollCard);
                            break;
                        
                        case WOOD:
                            output.add(trollCard);
                            output.add(trollCard);
                            break;
                        default:
                            break;
                    }
                    break;

                case UNICORN:
                    Card unicornCard = new Card(CardType.UNICORN);
                    if(this.hasObstacle){
                        output.add(unicornCard);
                    }

                    switch(aRegion){
                        case DESERT:
                            output.add(unicornCard);
                            output.add(unicornCard);
                            break;
                        case MOUNTAIN:
                            output.add(unicornCard);
                            break;
                        case WOOD:
                            output.add(unicornCard);
                            break;
                        default:
                            break;
                    }
                    break;
                // case WITCH:
                //     break;
                default:
                    break;

            }
        }
        return output;
    }
}
