package networksrc;

import clientsrc.ClientMain;

import java.util.HashMap;
import java.util.List;

import org.minueto.MinuetoFileException;

public class DealTravelCardsACK implements Action {

    private HashMap<String, List<String>> playerCards;

    public DealTravelCardsACK(HashMap<String, List<String>> playerCards) {
        this.playerCards = playerCards;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("BEFORE RECIEVE PHASEONE");
        try {
            ClientMain.recievePhaseOne(playerCards);
        } catch (MinuetoFileException e) {
            e.printStackTrace();
        }
        System.out.println("AFTER RECIEVE PHASEONE");
    }

}
