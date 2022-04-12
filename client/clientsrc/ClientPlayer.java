
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
        TokenSprite temp = null;
        for (TokenSprite token: tokensInHand)
        {
            if (token.getTokenType() == serversrc.CardType.OBSTACLE)
            {
                temp = token;
                break;
            }
        }
        tokensInHand.clear();
        if (temp != null)
        {
            tokensInHand.add(temp);
        }
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
        // prints
        System.out.print("Moving boot, removing player from town: " + inTown.getTownName());
        // remove the player from the old town
        inTown.playersHere.remove(this);

        // set the town of the player to the new town
        this.inTown = t;

        // prints
        System.out.print("Moving boot, moving player to town: " + inTown.getTownName());
        // add the player to the list of players located at the new town
        t.addPlayer(this);
        // drawBoot();

        // TODO: check if the player has traveled to the new town in the past already,
        // -> if yes, do nothing
        // -> if no, collect the town marker and remove the town marker from the town

        // note: actually GUI movement is all done inside of client main
    }

    /*
     * Operation: Player::playCaravan()
     * Scope: Game; Player; Road; Card;
     * Messages: Player::{promptForCardSacrifice, displayAvailableBoardMovements}
     * Post: Prompts the player to select a card to sacrifice and highlights all
     * available routes.
     * 
     */

    /*
     * Operation: Player::chooseRouteForCaravan(travelRoute: Route)
     * Scope: Player; Game; Road;
     * Messages: Player::{displayAvailableBoardMovements, promptForCardSacrifice}
     * Post: Prompts the player all possible board movements and ask for which card
     * the player would like to sacrifice.
     * 
     */

    /*
     * Operation: Player::pickSacrificesForCaravan(cardsToSacrifice: Set{Card})
     * Scope: Game; Player; Card;
     * Messages: Player::{gameState}
     * Post: Sends a new game state to the player.
     * 
     */

    /*
     * Operation: Player::playWitchToFly(town: Town)
     * Scope: Game; Player; Road; Town; Card;
     * Messages: Player::{displayWitchTravelTowns}
     * Post: Highlight all possible destination towns.
     * 
     */

    /*
     * Operation: playWitchToCrossObstacle()
     * Scope: Game; Player; Road;
     * Messages: Player::{displayBlockedRoutes}
     * Post: Highlight all blocked routes on the map.
     * 
     */

    /*
     * Operation: Player::chooseToReceiveTownGold()
     * Scope: Player; Game;
     * Messages: Player::{gameState}
     * Post: Sends a new game state to the player.
     */

    /*
     * Operation: Player::chooseToReceiveCards()
     * Scope: Player; Game; Card;
     * Messages: Player::{gameState}
     * Post: Sends a new game state to the player.
     */

    /*
     * Operation: Player::playTransportationCounter()
     * Scope: Player; Game; Token;
     * Messages: Player::{displayUnmarkedRoutes()}
     * Post: Sends a new game state to the player.
     */

    
    // returns the removed card and removes card from hand
    public void removeCard(CardSprite card) {
        this.cardsInHand.remove(card);
    }

    /*
     * Operation: Player::selectCounterLocation(transportationCounter:
     * TransportationCounter, travelRoute: Route)
     * Scope: Game; Player; Road;
     * Messages: Player::{gameState}
     * Post: Sends a new game state to the player that a counter is placed on an
     * available route on the map.
     */

    /*
     * Operation: Player::playObstacle()
     * Scope: Player; Game; Token;
     * Messages: Player::{displayMarkedRoutes}
     * Post: The effect of playObstacle operation is to declare that the next step
     * is to place an obstacle on the map. All marked routes will be highlighted.
     * The next step is chosen from either playing a transport counter, an obstacle,
     * a sea monster, a double transport spell or an exchange spell.
     */

    /*
     * Operation: Player::placeObstacleOnRoute(travelRoute: TravelRoute)
     * Scope: Player; Game; Road; Token;
     * Messages: Player::{gameState}
     * Post: Sends a new game state to the player that a counter is placed on an
     * available route on the map.
     */

    /*
     * Operation: Player::playSeaMonster()
     * Scope: Player; Game; Token;
     * Messages: Player::{displayMarkedWater}
     * Post: The effect of playSeaMonster operation is to declare that the next step
     * is to place a sea monster on the map. All marked water will be highlighted.
     * The next step is chosen from either playing a transport counter, an obstacle,
     * a sea monster, a double transport spell or an exchange spell.
     */

    /*
     * Operation: Player::placeSeaMonsterOnWater(travelRoute: TravelRoute)
     * Scope: Player; Game; Token;
     * Messages: Player::{gameState}
     * Post: Sends a new game state to the player that a counter is placed on an
     * available route on the map.
     */

    /*
     * Operation: Player::playDoubleTransportSpell()
     * Scope: Player; Game; Token;
     * Messages: Player::{displayMarkedRoutes; promptForCounterSacrifice}
     * Post: The effect of playDoubleTransportSpell operation is to declare that the
     * next step is to place a double transportation counter on the map. All marked
     * routes will be highlighted. The next step is chosen from either playing a
     * transport counter, an obstacle, a sea monster, a double transport spell or an
     * exchange spell.
     */

    /*
     * Operation: Player::playExchangeSpell()
     * Scope: Player; Game; Token;
     * Messages: Player::{promptForTwoCounterSwap}
     * Pre: There exists at least 2 counters on the map.
     * Post: The effect of playExchangeSpell operation is to declare that the next
     * step is to place an exchange spell on the map. Prompts the player to select 2
     * counters placed on the map and confirms the swap. The next step is chosen
     * from either playing a transport counter, an obstacle, a sea monster, a double
     * transport spell or an exchange spell.
     */

    /*
     * Operation: Player::swap(travelRoutes: Set{TravelRoute},
     * transportationCounters: Set{TranportationCounter))
     * Scope: Game; Player; Road;
     * Messages: Player::{gameState}
     * Post: Sends a new game state to the player indicating that two counters have
     * been swapped on the map.
     */

    /*
     * Operation: Player::keepTransportationCounter(transportationCounter:
     * TransportationCounter)
     * Scope: Player; Game; Token;
     * Messages: Player::{gameState}
     * Post: Sends a new game state to the player indicating that two counters have
     * been swapped on the map.
     */

    /*
     * Operation: Player::bid(gold: int)
     * Scope: Player; Auction;
     * Messages: Player::{gameState; bidTooLow_e}
     * Post: Sends a new game state to the player that the bid is placed if the bid
     * is higher than the current highest bid and update auctionInfo. If the bid is
     * not high enough, sends a “bidTooLow_e” message to the player.
     */

    /*
     * Operation: Player::bidNothing()
     * Scope: Player; Auction;
     * Messages: Player::{gameState}
     * Post: Sends a new game state that the player will no longer bid for the
     * current transportation counter.
     */
}
