
/* This class contains all info relevant to a single Player */
import java.util.*;

public class Player {
    boolean isTurn = false;

    private Client aClient;
    private Boot aBoot;

    int gold;
    GUI guiDisplayed; // TODO: initialize this
    Boot boot;
    List<Card> cardsInHand;
    List<Token> tokensInHand;

    private String aName;

    public Player(Client pClient, Color pColor) {
        aClient = pClient;
        aName = aClient.getHost();
        // TODO: fix these coordinates to match start town
        aBoot = new Boot(pColor, 577, 666, 291, 370);
        this.gold = 0;
        // TODO: guiDisplayed
        this.cardsInHand = new ArrayList<>();
        this.tokensInHand = new ArrayList<>();
    }

    public void setTurn(boolean bool) {
        isTurn = bool;
    }
}
