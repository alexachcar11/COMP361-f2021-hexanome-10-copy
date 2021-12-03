/* This class contains all info relevant to a single Player */

import org.minueto.image.MinuetoImage;
import java.util.ArrayList;

public class Player {

    String username;
    int gold;
    GUI guiDisplayed; // TODO: initialize this
    Boot boot;
    ArrayList<Card> cardsInHand;
    ArrayList<Token> tokensInHand;

    public Player(String username, Color color) {
        this.username = username;
        this.boot = new Boot(color);
        this.gold = 0;
        // TODO: guiDisplayed
        this.cardsInHand = new ArrayList<>();
        this.tokensInHand = new ArrayList<>();
    }

}
