package serversrc;

import java.util.ArrayList;

public class Auction {
    private int aCurrentBid;
    private Player HighestBidPlayer;
    private Token aToken;
    private ArrayList<Player> biddersList;
    private Player lastPassedPlayer;
    private int IndLastPassedPlayer;

    public Auction(Token pToken, ArrayList<Player> pList){
        this.aCurrentBid = 0;
        this.HighestBidPlayer = null;
        this.aToken = pToken;
        biddersList = pList;
    }
    public Auction(ArrayList<Player> pList){
        this.aCurrentBid = 0;
        this.HighestBidPlayer = null;
        this.aToken = null;
        biddersList = pList;
    }

    public void setToken(Token pToken){
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
        this.HighestBidPlayer.addToken(this.aToken);
        // deduct gold
        this.HighestBidPlayer.deductGold(this.aCurrentBid);
        return this.HighestBidPlayer;
    }

    public boolean isValidBid(int pBid, Player pPlayer){
        // check if player has enough to bid that amount
        if (pBid > pPlayer.getGold()){
            return false;
        }
        // check if pBid is smaller or equal then current bid
        if (pBid <= this.aCurrentBid){
            return false;
        }
        return true;
    }

    // a player bids, returns -1 if bid fails, 1 if success 
    public void bid(int pBid, Player pPlayer){
        if(!isValidBid(pBid, pPlayer)){
            return; // do nothing if not valid bid
        }
        // otherwise bid is a success, update fields
        this.aCurrentBid = pBid;
        this.HighestBidPlayer = pPlayer;

        // should check if everyone else passed turn ?
    }
    public Token getToken() {
        return this.aToken;
    }
    public ArrayList<Player> getBiddersList(){
        return this.biddersList;
    }
    public void setBiddersList(ArrayList<Player> p){
        this.biddersList = p;
    }
    public void setLastPassedPlayer(Player p){
        this.lastPassedPlayer = p;
        this.IndLastPassedPlayer = this.biddersList.indexOf(p);
    }

    public Player getLastPassedPlayer(){
        return this.lastPassedPlayer;
    }
    public int getIndLastPassedPlayer(){
        return this.IndLastPassedPlayer;
    }

}
