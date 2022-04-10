package serversrc;

import java.io.Serializable;

// import clientsrc.Player;
// import clientsrc.Token;

public class Auction implements Serializable{
    private int aCurrentBid;
    private Player HighestBidPlayer;
    private Token aToken;

    public Auction(Token pToken){
        this.aCurrentBid = 0;
        this.HighestBidPlayer = null;
        this.aToken = pToken;
    }

    // returns current bid
    public int getCurrentBid(){
        return this.aCurrentBid;
    }

    /*  pre: all players chose to passTurn
    *   
    *   @return the winning player, if no one bid, return null
    *   post: The winning player's gold is deducted by bid amount
    *   and token is added to his hand
    */
    public Player getWinner(){

        if (this.HighestBidPlayer == null){
            return this.HighestBidPlayer;
        }
        // add token to Player
        // TODO: ( inside player add method ) Player.addToken(Token pToken)
        /* this.HighestBidPlayer.addToken(this.aToken); */
        // TODO: ( inside player add method ) Player.deductGold(int amount)
        /* this.HighestBidPlayer.deductGold(this.aCurrentBid); */
        return this.HighestBidPlayer;

    }

    // a player bids, returns -1 if bid fails, 1 if success 
    public int bid(int pBid, Player pPlayer){
        // TODO: ( inside player add method ) Player.getGold()
        // check if player has enough to bid that amount
        /* if (pBid > pPlayer.getGold()){
        *     return -1;
        }*/

        // check if pBid is smaller or equal then current bid
        if (pBid <= this.aCurrentBid){
            return -1;
        }

        // otherwise bid is a success, update fields
        this.aCurrentBid = pBid;
        this.HighestBidPlayer = pPlayer;
        return 1;
    }

}
