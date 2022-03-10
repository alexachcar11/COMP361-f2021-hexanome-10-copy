package networksrc;

import clientsrc.ClientMain;

import java.util.ArrayList;

public class DealTravelCardsACK implements Action{

    private String playerName;
    private ArrayList<String> playerCards;

    public DealTravelCardsACK(String playerName, ArrayList<String> playerCards){
        this.playerName = playerName;
        this.playerCards = playerCards;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("BEFORE RECIEVE PHASEONE");
        ClientMain.recievePhaseOne(playerName, playerCards);
        System.out.println("AFTER RECIEVE PHASEONE");

    }

}
