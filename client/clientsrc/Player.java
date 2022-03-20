
/* This class contains all info relevant to a single Player */
package clientsrc;

import java.util.*;
import java.util.stream.Collectors;

import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImageFile;

public class Player {
    boolean isTurn = false;

    private int gold;
    private GUI guiDisplayed; // TODO: initialize this
    private List<TravelCard> cardsInHand;
    private List<TokenImage> tokensInHand;
    private Town inTown;

    private MinuetoImageFile bootImage;
    private Color color;

    private String aName;

    // used in ActionManager
    private User aUser;
    private Game currentGame;
    private static ArrayList<Player> allPlayers = new ArrayList<Player>();

    public Player(Color pColor, User pUser, Game currentGame) {

        // inTown = elvenhold; // fix this
        this.gold = 0;
        this.cardsInHand = new ArrayList<>();
        this.tokensInHand = new ArrayList<>();
        this.aUser = pUser;
        this.currentGame = currentGame;
        this.color = pColor;
        this.aName = pUser.getName();
        try {
            this.bootImage = new MinuetoImageFile(
                    "images/boot" + pColor.toString().toLowerCase() + ".png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // aBootAction = new BootAction(this);
        allPlayers.add(this);
    }

    public static Player getPlayerByName(String name) {
        for (Player p : allPlayers) {
            if (p.getServerUser().getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public String getName() {
        return aUser.getName();
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

    public GUI getGui() {
        return guiDisplayed;
    }

    // public void draw() {
    // int x = boot.getCoords()[0];
    // int y = boot.getCoords()[2];
    // guiDisplayed.getWindow().draw(boot.getMImage(), x, y);
    // }

    // public Action getBootAction() {
    // return aBootAction;
    // }

    public void addCardStringArray(ArrayList<String> cardArray) throws MinuetoFileException {
        for (String cardString : cardArray) {
            cardsInHand.add(Game.getFaceDownCard(cardString));
        }
    }

    public void addTokenString(String token) throws MinuetoFileException {
        tokensInHand.add(TokenImage.getTokenImageByString(token));
    }

    /**
     * adds TokenImages with names in tokenStrings to tokensInHand
     * 
     * @param tokenStrings
     */
    public void addTokenStringList(List<String> tokenStrings) {
        List<TokenImage> tokenImages = tokenStrings.stream()
                .map((tokenString) -> {
                    try {
                        return TokenImage.getTokenImageByString(tokenString);
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

    public List<TravelCard> getCardsInHand() {
        return cardsInHand;
    }

    public List<TokenImage> getTokensInHand() {
        return tokensInHand;
    }

    public void drawBoot() { 
        ClientMain.gui.window.draw(bootImage, inTown.minX, inTown.maxY);
    }



    /**
     * Sets the inTown parameter to Town t
     * 
     * @param t
     */
    public void setTown(Town t) {
        inTown = t;
        t.addPlayer(this);
    }

    public void moveBoot(Town t) {
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

    public void consumeToken(TokenImage token) {
        assert token != null;

        if (tokensInHand.contains(token)) {
            tokensInHand.remove(token);
            // do we need to put the token back into the pool of tokens?
        } else {
            throw new IllegalArgumentException();
        }
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
