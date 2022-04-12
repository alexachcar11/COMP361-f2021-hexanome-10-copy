
/* This class contains all info relevant to a single Player */
package clientsrc;

import java.util.*;
import java.util.stream.Collectors;

import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

public class ClientPlayer {
    boolean isTurn = false;

    private int gold;
    private List<CardSprite> cardsInHand;
    private List<TokenSprite> tokensInHand;
    private ClientTown inTown;
    private ClientTown targetDestinationTown;

    private MinuetoImageFile bootImage;
    private MinuetoImageFile boppel;
    private Color color;

    private String aName;

    // used in ActionManager
    private User aUser;
    private Game currentGame;
    private static ArrayList<ClientPlayer> allPlayers = new ArrayList<ClientPlayer>();

    public ClientPlayer(Color pColor, User pUser, Game currentGame) {

        // inTown = elvenhold; // fix this
        this.gold = 12;
        this.cardsInHand = new ArrayList<>();
        this.tokensInHand = new ArrayList<>();
        this.aUser = pUser;
        this.currentGame = currentGame;
        this.color = pColor;
        this.aName = pUser.getName();
        try {
            String lower = pColor.toString().toLowerCase().substring(1);
            String upper = pColor.toString().toLowerCase().substring(0,1);
            String bootFileName = upper.toUpperCase() + lower;
            this.bootImage = new MinuetoImageFile(
                    "images/boot" + bootFileName + ".png");
            this.boppel = new MinuetoImageFile(
                    "images/böppels-and-boots/böppel-" + pColor.toString().toLowerCase() + ".png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // aBootAction = new BootAction(this);
        allPlayers.add(this);
    }

    public static ClientPlayer getPlayerByName(String name) {
        for (ClientPlayer p : allPlayers) {
            if (p.getServerUser().getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public String getName() {
        return aUser.getName();
    }

    public ClientTown getCurrentLocation() {
        return inTown;
    }

    public void setTargetDestinationTown(ClientTown pTown) {
        this.targetDestinationTown = pTown;
    }

    // get TargetTown
    public ClientTown getTargetDestinationTown() {
        return this.targetDestinationTown;
    }

    public void drawTargetDestination() throws MinuetoFileException {
        ClientTown targetTown = getTargetDestinationTown();

        MinuetoImage destTownFlag = new MinuetoImageFile("images/flag.png");
        ClientMain.gui.window.draw(destTownFlag, targetTown.getMaxX() + 8, targetTown.getMaxY() + 8);

    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public User getServerUser() {
        return aUser;
    }

    public void setTurn(boolean bool) {
        isTurn = bool;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void incrementGold(int townGoldValue) {
        this.gold += townGoldValue;
    }

    public int getGoldAmount() {
        return this.gold;
    }

    public void addCardStringArray(List<String> cardArray) throws MinuetoFileException {
        for (String cardString : cardArray) {
            cardsInHand.add(Game.getFaceDownCard(cardString));
        }
    }

    public void addTokenString(String token) throws MinuetoFileException {
        tokensInHand.add(TokenSprite.getTokenSpriteByString(token));
    }

    /**
     * adds TokenImages with names in tokenStrings to tokensInHand
     * 
     * @param tokenStrings
     */
    public void addTokenStringList(List<String> tokenStrings) {
        List<TokenSprite> tokenImages = tokenStrings.stream()
                .map((tokenString) -> {
                    try {
                        return TokenSprite.getTokenSpriteByString(tokenString);
                    } catch (MinuetoFileException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
        tokensInHand.clear();
        tokensInHand.addAll(tokenImages);
    }

    public List<CardSprite> getCardsInHand() {
        return cardsInHand;
    }

    public List<TokenSprite> getTokensInHand() {
        return tokensInHand;
    }

    public MinuetoImageFile getBoppel() { 
        return boppel;
    }

    /**
     * Draw the player's boot
     * @param order this player's "spot" at the town (to stack the boots)
     */
    public void drawBoot(int order) {
        this.incrementGold(inTown.getGoldValue());
        ClientMain.gui.window.draw(bootImage, inTown.minX + order*20, inTown.minY + order*15);
    }

    public void clearTokenHand() {
        tokensInHand.clear();
    }

    public void consumeToken(TokenSprite token) {
        assert token != null;

        if (tokensInHand.contains(token)) {
            tokensInHand.remove(token);
            System.out.println("removed token from hand: " + token.getTokenName());
            // do we need to put the token back into the pool of tokens?
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setTown(ClientTown t) {
        inTown = t;
        t.addPlayer(this);
    }

    public void moveBoot(ClientTown t) {
        // remove the player from the old town
        inTown.playersHere.remove(this);

        // set the town of the player to the new town
        this.inTown = t;

        // add the player to the list of players located at the new town
        t.addPlayer(this);
        // drawBoot();

        // TODO: check if the player has traveled to the new town in the past already,
        // -> if yes, do nothing
        // -> if no, collect the town marker and remove the town marker from the town

        // note: actually GUI movement is all done inside of client main
    }
}
