package networksrc;

import serversrc.*;

public class AuctionBidAction implements Action{

    int bid;
    String senderName;

    public AuctionBidAction(String sender, int pBid){
        this.bid = pBid;
        this.senderName = sender;
    }

    @Override
    public boolean isValid() {
        Player playerWhoSent = Player.getPlayerByName(senderName);
        ServerGame playersCurrentGame = playerWhoSent.getCurrentGame();
        // check if it's player's turn
        if (!playerWhoSent.getIsTurn()){
            return false;
        }
        // check phase
        if (playersCurrentGame.getCurrentPhase() != 10){
            return false;
        }
        // player should be in bidder's list
        if (!playersCurrentGame.getAuction().getBiddersList().contains(playerWhoSent)){
            return false;
        }
        // there should be a token on auction at this time
        return playersCurrentGame.getAuction().isValidBid(bid, playerWhoSent);
    }

    @Override
    public void execute() {
        Player playerWhoSent = Player.getPlayerByName(senderName);
        ServerGame playersCurrentGame = playerWhoSent.getCurrentGame();

        // player bids
        playersCurrentGame.getAuction().bid(this.bid, playerWhoSent);
    }
    
}
