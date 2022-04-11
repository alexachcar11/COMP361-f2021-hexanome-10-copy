package clientsrc;

import java.util.ArrayList;

// import serversrc.Player;
// import serversrc.Token;
// import serversrc.Town;
import serversrc.RouteType;

public class ClientRoute {

    // route needs to have a starting town and an ending town
    // routes carry one transportation counter maximum, but don't need to have one

    ClientTown aStartingTown;
    ClientTown aEndTown;
    TokenSprite aToken; // TODO: there could be multiple tokens, list ?
    // road or river
    boolean isRiver = false;
    // upstream
    boolean isUpstream;
    private RouteType type;
    private int[] hitbox;


    private static ArrayList<ClientRoute> allRoutes = new ArrayList<>();

    public ClientRoute(ClientTown pStartingTown, ClientTown pEndTown, RouteType rType, int[] pHitbox) {
        this.aStartingTown = pStartingTown;
        this.aEndTown = pEndTown;
        this.aToken = null;
        this.type = rType;
        this.hitbox = pHitbox;

        allRoutes.add(this);
    }

    // overload if it's a river
    // n = 0 means it's downstream, n = 1 means it's upstream
    public ClientRoute(ClientTown pStartingTown, ClientTown pEndTown, int n) {
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
        allRoutes.add(this);
    }

    public boolean getisRiver() {
        return isRiver;
    }

    public ArrayList<ClientRoute> getAllRoutes() {
        return allRoutes;
    }

    // sets Upstream with a boolean
    public void setUpstream(boolean b) {
        isUpstream = b;
    }

    public ClientTown getSource() {
        return this.aStartingTown;
    }

    public ClientTown getDest() {
        return this.aEndTown;
    }

    /**
     * Place a token on a valid route.
     * 
     * Ensure that the route isn't already occupied and that the token exists
     * 
     * @param token
     */
    public void placeToken(ClientPlayer player, TokenSprite token) {
        assert token != null;

        if (this.aToken == null) {
            throw new IllegalArgumentException();
        } else {
            // remove token from the players inventory
            player.consumeToken(token);
            // update token field
            this.aToken = token;
        }
    }

    public void setToken(TokenSprite pToken){
        this.aToken = pToken;
    }

    public void clearToken() {
        // update token field
        this.aToken = null;
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

    public String getSourceTownString() { 
        return aStartingTown.getTownName();
    }

    public String getDestTownString() { 
        return aEndTown.getTownName();
    }

    public String formatRequirements() { 
        String result = "To traverse this route you require ";

        if(type == RouteType.DESERT) { 
            result += "either one dragon, or two unicorns, troll wagons.";
        } else if (type == RouteType.LAKE) {
            result += "one boat card";
        } else if (type == RouteType.MOUNTAIN) {
            result += "either one cloud, unicord, dragon, or two elfcycles, troll wagons.";
        } else if (type == RouteType.PLAIN) {
            result += "either one pig, elfcycle, troll wagon, dragon, or two clouds.";
        } else if (type == RouteType.WOOD) {
            result += "either one pig, elfcycle, unicorn, or two clouds, troll wagons, dragons.";
        } else if (type == RouteType.RIVER && isUpstream == true) {
            result += "two boat cards.";
        } else if (type == RouteType.LAKE && isUpstream == false) {
            result += "one boat card.";
        }
        return result;
    }

}
