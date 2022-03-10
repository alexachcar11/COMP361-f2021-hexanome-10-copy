package networksrc;

import clientsrc.ClientMain;

import java.util.ArrayList;

import org.minueto.MinuetoFileException;

public class DealTravelCardsACK implements Action {

    private String playerName;
    private ArrayList<String> playerCards;

    public DealTravelCardsACK(String playerName, ArrayList<String> playerCards) {
        this.playerName = playerName;
        this.playerCards = playerCards;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() throws MinuetoFileException {
        System.out.println("BEFORE RECIEVE PHASEONE");
        try {
            ClientMain.recievePhaseOne(playerName, playerCards);
        } catch (MinuetoFileException e) {
            e.printStackTrace();
        }
        System.out.println("AFTER RECIEVE PHASEONE");

    }

}
