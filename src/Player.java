
/* This class contains all info relevant to a single Player */
import java.util.*;

public class Player {

    boolean isTurn = false;

    private Client aClient;
    private Boot aBoot;

    private int gold;
    private GUI guiDisplayed; // TODO: initialize this
    private Boot boot;
    private List<Card> cardsInHand;
    private List<Token> tokensInHand;

    private String aName;

    public Player(Client pClient, Color pColor) {
        aClient = pClient;
        aName = aClient.getHost();
        aBoot = new Boot(pColor);
        this.gold = 0;
        // TODO: guiDisplayed
        this.cardsInHand = new ArrayList<>();
        this.tokensInHand = new ArrayList<>();
    }

    public void setTurn(boolean bool) {
        isTurn = bool;
    }

    public int[] getCoords() {
        return boot.getCoords();
    }
}
