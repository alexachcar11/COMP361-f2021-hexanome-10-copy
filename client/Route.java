
public class Route {
    
    // route needs to have a starting town and an ending town 
    // routes carry one transportation counter maximum, but don't need to have one 
    
    Town aStartingTown;
    Town aEndTown;
    Token aToken; 

    Route(Town pStartingTown, Town pEndTown){ 
        this.aStartingTown = pStartingTown;
        this.aEndTown = pEndTown;
        this.aToken = null;
    }

    /**
     * Place a token on a valid route. 
     * 
     * Ensure that the route isn't already occupied and that the token exists
     * 
     * @param token
     */
    public void placeToken(Player player, Token token) { 
        assert token != null;

        if(this.aToken == null) { 
            throw new IllegalArgumentException();
        } else { 
            player.consumeToken(token);
            this.aToken = token;
            
        }
    }

    public void clearToken() { 
        this.aToken = null;
    }
}
