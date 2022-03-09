package serversrc;
/* This class contains all info relevant to a single Player */

import java.util.ArrayList;
import java.util.List;

// import clientsrc.Boot;
// import clientsrc.Card;
// import clientsrc.Color;
// import clientsrc.Token;
// import clientsrc.Town;
import networksrc.*;

public class Player {
    boolean isTurn = false;
    private Boot aBoot;

    private int gold;
    private Boot boot;
    private List<Card> cardsInHand;
    private List<Token> tokensInHand;
    private Town inTown;

    private String aName;
    private Action aBootAction;

    // used in ServerActions
    private ServerUser aServerUser;
    private ServerGame currentGame;

    private static ArrayList<Player> allPlayers = new ArrayList<Player>();

    public Player(ServerUser pServerUser, ServerGame currentGame) {
        aBoot = new Boot();

        // inTown = elvenhold;         // fix this
        this.gold = 0;
        this.cardsInHand = new ArrayList<>();
        this.tokensInHand = new ArrayList<>();

        this.aServerUser = pServerUser;
        this.currentGame = currentGame;
        currentGame.addPlayer(this);
        allPlayers.add(this);
        // aBootAction = new BootAction(this);
    }


    public static Player getPlayerByName(String name) {
        for (Player p : allPlayers) {
            if (p.getServerUser().getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public ServerGame getCurrentGame() {
        return currentGame;
    }

    public ServerUser getServerUser() {
        return aServerUser;
    }
    public void setTurn(boolean bool) {
        isTurn = bool;
    }

    public Action getBootAction() {
        return aBootAction;
    }

    public String getName() {
        return aServerUser.getName();
    }
    public Town getTown(){
        return this.inTown;
    }

    public boolean getIsTurn(){
        return this.isTurn;
    }

    public Color getBootColor(){
        return this.aBoot.getColor();
    }
    // draw counter from face down pile

<<<<<<< Updated upstream
=======
    public void addCard(AbstractCard c){
        cardsInHand.add(c);
    }

    public List<AbstractCard> getCards(){
        return cardsInHand;
    }



>>>>>>> Stashed changes
    /*
    Operation: Player::drawFaceUpToken(token: Token, tStack: TokenStack)
    Scope: Player; Game; Token;
    Messages: Player::{}
    Post: Sends a new game state to the player indicating that the requested travel card is allocated to their hand face-up.
     */
    // draw counter from face up pile and add it to player's counters
    public void drawFaceUpToken(Token pToken, ServerGame sg){
        // inside the game class update the tokens
        sg.updateFaceUpToken(pToken);
        this.tokensInHand.add(pToken);  // add counter to player's hand
        // update client's GUI by:
        // 1) remove on GUI the Token that just got taken
        // 2) add to player's GUI his Token
    }

     /*
    Operation: Player::drawTransportationCounter(tStack: TokenStack)
    Scope: Player; Token; Game;
    Messages: Player::{}
    Post: Sends a new game state to the player indicating that the requested travel card is allocated to their hand.
     */
    // draw random counter and add it to player's counters
    public void drawTransportationCounter(TokenStack tStack){
        this.tokensInHand.add(tStack.pop());
        // update client's GUI by:
        // add to player's GUI his Token
    }

    /*
    Operation: Player::startGame(gameSession: Session)
    Scope: Player; Session;
    Messages: Player::{gameStartConfirmation; gameStartFailed_e}
    Post: Upon success, sends the player a message to confirm that the game has started successfully and moves all players to the game screen. Otherwise, sends a “gameStartFailed_e” message.
     */

    /*
    Operation: Player::quitGameSession()
    Scope: Player;  Session;
    Messages: Player::{quitConfirmation; quitFailed_e}
    Post: Upon success, sends the player a message to confirm they have quit successfully and returns the player back to the lobby. Otherwise, sends a “quitFailed_e” message.
     */

    /*
    Operation: Player::saveGameSession()
    Scope: Session; Game; Player;
    Messages: Player::{saveConfirmation}
    Post: Sends a message to the player to notify the player that the session is saved.

     */

    /*
    Operation: Player::getAvailableBootColors(bootColors: Set{Color})
    Scope: Game; Player;
    Messages: Player::{availableBootColors}
    Post: Sends the player a set of boot colors available for the player to choose from.

     */

    /*
    Operation: Player::pickBootColor(bootColor: Color)
    Scope: Player; Boot;
    New: newBoot: Boot;
    Messages: Player::{gameState; bootColorInvalid_e};
    Post: Sends a new game state to the player that they are allocated a boot with color of their choice if their choice of color is available. If the chosen colour is taken, sends the player a message, which informs them to pick another boot color.

     */


    /*
    Operation: Player::makeTokensFaceDown(tokens: Set{Token})
    Scope: Player; Game; Token;
    Messages: Player::{}
    Post: The tokens chosen by the player are now face down.

     */

    /*
    Operation: Player::drawTravelCard()
    Scope: Player; Game; Card;
    Messages: Player::{}
    Post: Sends a new game state to the player indicating that a travel card is allocated to their hand.

     */

    /*
    Operation: Player::drawFaceUpCard(travelCard: travelCard)
    Scope: Player; Game; Card;
    Messages: Player::{}
    Post: Sends a new game state to the player indicating that the requested travel card is allocated to their hand face-up.

     */

    /*
    Operation: Player::collectGoldPile()
    Scope: Game; Player;
    Messages: Player::{gameState}
    Post: Sends a new game state to the player indicating that they have received gold relative to the amount in the ‘pile’.

     */

    /*
    Operation: Player::passTurn()
    Scope: Player; Game; Auction;
    Messages: Player::{gameState}
    Post: Sends a new game state to the player that their turn is passed and no further action can be taken in the current round.

     */

    /*
    Operation: Player::moveBoot(travelRoute: Route, transportationCards: Set{TransportationCard})
    Scope: Boot; Player;
    Messages: Player::{gameState}
    Post: Upon success, sends a new game state to the player.

     */

    // // TODO: transportationCards input missing ? or change it to not include it...
    // // moveBoot that returns 1 if moveBoot was successful, else return -1 for unsuccessful
    // public int moveBoot(Town toTown, int x, int y){
    //     // Player clicks on town he wishes to travel to
    //     // Check if it's valid input (player can only travel to adjacent towns)
    //     // if not then request player to choose again
    //     // if it's valid, move player's boot to new town
        
    //     if (inTown.hasAdjascent(toTown)){
    //         // check if player has the required cards to move to this town
    //         Route route = inTown.getRoute(toTown);
    //         // if player has cards, take away those cards from player's hand and move boot
    //         if (route.checkCards(this.cardsInHand)){    // note each Token should have field "aRequiredCards"// in Route class implement getRequiredCards(){ token.get}
    //             for (Card cardToDelete: route.getRequiredCards()){  // IMPORTANT OVERRIDE EQUAL IN Card CLASS
    //                 this.cardsInHand.remove(this.cardsInHand.indexOf(cardToDelete));
    //             }
    //             // update inTown to the new Town
    //             inTown = toTown;

    //             // TODO: move boot on the gui using aBoot field from Player class
                
    //             return 1;
    //         }
    //         // otherwise moveBoot ends in failure and returns -1
            
    //     }
    //     // ??? end player's turn ???
    //     return -1;
    // }

    public void moveBoot(Town t) { 
        // remove the player from the old town 
        inTown.playersHere.remove(this);

        // set the town of the player to the new town 
        this.inTown = t;

        // add the player to the list of players located at the new town 
        t.addPlayer(this);

        // TODO: check if the player has traveled to the new town in the past already, 
        //     -> if yes, do nothing 
        //     -> if no, collect the town marker and remove the town marker from the town

        // note: actually GUI movement is all done inside of client main
    }

    /*
    Operation: Player::playCaravan()
    Scope: Game; Player; Road; Card;
    Messages: Player::{promptForCardSacrifice, displayAvailableBoardMovements}
    Post: Prompts the player to select a card to sacrifice and highlights all available routes.

     */

    /*
    Operation: Player::chooseRouteForCaravan(travelRoute: Route)
    Scope: Player; Game; Road;
    Messages: Player::{displayAvailableBoardMovements, promptForCardSacrifice}
    Post: Prompts the player all possible board movements and ask for which card the player would like to sacrifice.

     */

    /*
    Operation: Player::pickSacrificesForCaravan(cardsToSacrifice: Set{Card})
    Scope: Game; Player; Card;
    Messages: Player::{gameState}
    Post: Sends a new game state to the player.

     */

    /*
    Operation: Player::playWitchToFly(town: Town)
    Scope: Game; Player; Road; Town; Card;
    Messages: Player::{displayWitchTravelTowns}
    Post: Highlight all possible destination towns.

     */

    /*
    Operation: playWitchToCrossObstacle()
    Scope: Game; Player; Road;
    Messages: Player::{displayBlockedRoutes}
    Post: Highlight all blocked routes on the map.

     */

    /*
    Operation: Player::chooseToReceiveTownGold()
    Scope: Player; Game;
    Messages: Player::{gameState}
    Post: Sends a new game state to the player.
    */

    /*
    Operation: Player::chooseToReceiveCards()
    Scope: Player; Game; Card;
    Messages: Player::{gameState}
    Post: Sends a new game state to the player.
     */

    /*
    Operation: Player::playTransportationCounter()
    Scope: Player; Game; Token;
    Messages: Player::{displayUnmarkedRoutes()}
    Post: Sends a new game state to the player.
     */

    public void consumeToken(Token token) { 
        assert token != null;

        if(tokensInHand.contains(token)) { 
            tokensInHand.remove(token);
            // do we need to put the token back into the pool of tokens? 
        } else { 
            throw new IllegalArgumentException();
        }
    }

    /*
    Operation: Player::selectCounterLocation(transportationCounter: TransportationCounter, travelRoute: Route)
    Scope: Game; Player; Road;
    Messages: Player::{gameState}
    Post: Sends a new game state to the player that a counter is placed on an available route on the map.
     */

    /*
    Operation: Player::playObstacle()
    Scope: Player; Game; Token;
    Messages: Player::{displayMarkedRoutes}
    Post: The effect of playObstacle operation is to declare that the next step is to place an obstacle on the map. All marked routes will be highlighted. The next step is chosen from either playing a transport counter, an obstacle, a sea monster, a double transport spell or an exchange spell.
     */

    /*
    Operation: Player::placeObstacleOnRoute(travelRoute: TravelRoute)
    Scope: Player; Game; Road; Token;
    Messages: Player::{gameState}
    Post: Sends a new game state to the player that a counter is placed on an available route on the map.
     */

    /*
    Operation: Player::playSeaMonster()
    Scope: Player; Game; Token;
    Messages: Player::{displayMarkedWater}
    Post: The effect of playSeaMonster operation is to declare that the next step is to place a sea monster on the map. All marked water will be highlighted. The next step is chosen from either playing a transport counter, an obstacle, a sea monster, a double transport spell or an exchange spell.
     */

    /*
    Operation: Player::placeSeaMonsterOnWater(travelRoute: TravelRoute)
    Scope: Player; Game; Token;
    Messages: Player::{gameState}
    Post: Sends a new game state to the player that a counter is placed on an available route on the map.
     */

    /*
    Operation: Player::playDoubleTransportSpell()
    Scope: Player; Game; Token;
    Messages: Player::{displayMarkedRoutes; promptForCounterSacrifice}
    Post: The effect of playDoubleTransportSpell operation is to declare that the next step is to place a double transportation counter on the map. All marked routes will be highlighted. The next step is chosen from either playing a transport counter, an obstacle, a sea monster, a double transport spell or an exchange spell.
     */

    /*
    Operation: Player::playExchangeSpell()
    Scope: Player; Game; Token;
    Messages: Player::{promptForTwoCounterSwap}
    Pre: There exists at least 2 counters on the map.
    Post: The effect of playExchangeSpell operation is to declare that the next step is to place an exchange spell on the map. Prompts the player to select 2 counters placed on the map and confirms the swap. The next step is chosen from either playing a transport counter, an obstacle, a sea monster, a double transport spell or an exchange spell.
    */


    /*
    Operation: Player::swap(travelRoutes: Set{TravelRoute}, transportationCounters: Set{TranportationCounter))
    Scope: Game; Player; Road;
    Messages: Player::{gameState}
    Post: Sends a new game state to the player indicating that two counters have been swapped on the map.
     */

    /*
    Operation: Player::keepTransportationCounter(transportationCounter: TransportationCounter)
    Scope: Player; Game; Token;
    Messages: Player::{gameState}
    Post: Sends a new game state to the player indicating that two counters have been swapped on the map.
    */

    /*
    Operation: Player::bid(gold: int)
    Scope: Player; Auction;
    Messages: Player::{gameState; bidTooLow_e}
    Post: Sends a new game state to the player that the bid is placed if the bid is higher than the current highest bid and update auctionInfo. If the bid is not high enough, sends a “bidTooLow_e” message to the player.
    */

    /*
    Operation: Player::bidNothing()
    Scope: Player; Auction;
    Messages: Player::{gameState}
    Post: Sends a new game state that the player will no longer bid for the current transportation counter.
     */
}
