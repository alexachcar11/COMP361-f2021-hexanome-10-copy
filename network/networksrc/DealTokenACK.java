package networksrc;

import java.util.HashMap;

import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;

public class DealTokenACK implements Action {
    private HashMap<String, String> playerTokens;

    public DealTokenACK(HashMap<String, String> playerTokens) {
        this.playerTokens = playerTokens;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("BEFORE PHASE TWO");
        try {
            for (String playerName : this.playerTokens.keySet()) {
                ClientMain.receivePhaseTwo(playerName, this.playerTokens.get(playerName));
            }
        } catch (MinuetoFileException e) {
            e.printStackTrace();
        }
        System.out.println("AFTER PHASE TWO");
    }

}
