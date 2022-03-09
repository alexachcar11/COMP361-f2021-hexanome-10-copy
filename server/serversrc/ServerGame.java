package serversrc;
/*
Instance of Game represent a single elfen game.

min 2 players
max 6 players
 */

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.MinuetoImage;
import org.minueto.window.MinuetoWindow;

import clientsrc.ActionManager;
import clientsrc.ClientMain;
import clientsrc.TokenImage;
import networksrc.ACKManager;
import networksrc.Action;
import networksrc.TokenSelectedAction;

import java.util.*;

// import clientsrc.Card;
// import clientsrc.GoldCard;
// import clientsrc.Mode;
// import clientsrc.Player;
// import clientsrc.Route;
// import clientsrc.Town;
// import clientsrc.TownGoldOption;

public class ServerGame {

    private static final ACKManager ACK_MANAGER = ACKManager.getInstance();

    private ArrayList<Player> players;
    private int numberOfPlayers;
    public static ArrayList<Town> towns;
    public ArrayList<Route> routes;
    public int currentRound;
    private int currentPhase;
    public int gameRoundsLimit;
    public boolean destinationTownEnabled;
    public boolean witchEnabled;
    public Mode mode;
    public TownGoldOption townGoldOption;
    public ArrayList<AbstractCard> faceDownCardPile;
    public ArrayList<AbstractCard> faceUpCardPile;
    public ArrayList<GoldCard> goldCardPile;
    // public Auction auction; not doing this now
    public ArrayList<Token> faceUpTokenPile;
    public TokenStack faceDownTokenStack;
    private String gameID;
    public TownGraph aTownGraph;
    public CardStack aCardStack;
    private List<AbstractCard> disposedCardPile;

    /**
     * CONSTRUCTOR : creates an instance of Game object
     */
    public ServerGame(int numberOfPlayers, int gameRoundsLimit, boolean destinationTownEnabled, boolean witchEnabled,
            Mode mode, TownGoldOption townGoldOption, String gameID) {

        this.players = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayers;
        this.gameRoundsLimit = gameRoundsLimit;
        this.destinationTownEnabled = destinationTownEnabled;
        this.witchEnabled = witchEnabled;
        this.mode = mode;
        this.townGoldOption = townGoldOption;
        this.currentRound = 1;

        towns = new ArrayList<>();
        routes = new ArrayList<>();
        disposedCardPile = new ArrayList<>();

        // TODO: initialize faceDownCardPile, goldCardPile and auction depending on the
        // mode

        // TODO: initialize faceDownCardPile, faceUpCardPile, goldCardPile and auction
        // depending on the mode

        Town esselen = new Town("Esselen", 38, 103, 99, 152);
        Town yttar = new Town("Yttar", 35, 98, 222, 274);
        Town wylhien = new Town("Wylhien", 187, 234, 30, 75);
        Town parundia = new Town("Parundia", 172, 241, 172, 227);
        Town jaccaranda = new Town("Jaccaranda", 312, 381, 61, 119);
        Town albaran = new Town("AlBaran", 280, 343, 227, 283);
        Town thortmanni = new Town("Throtmanni", 451, 518, 129, 188);
        Town rivinia = new Town("Rivinia", 555, 621, 205, 256);
        Town tichih = new Town("Tichih", 604, 662, 79, 135);
        Town ergeren = new Town("ErgEren", 716, 776, 210, 259);
        Town grangor = new Town("Grangor", 49, 112, 366, 411);
        Town mahdavikia = new Town("MahDavikia", 57, 136, 482, 533);
        Town kihromah = new Town("Kihromah", 164, 223, 314, 367);
        Town ixara = new Town("Ixara", 257, 322, 489, 534);
        Town dagamura = new Town("DagAmura", 281, 339, 345, 394);
        Town lapphalya = new Town("Lapphalya", 415, 482, 383, 437);
        Town feodori = new Town("Feodori", 411, 472, 259, 317);
        Town virst = new Town("Virst", 478, 536, 491, 541);
        Town elvenhold = new Town("Elvenhold", 577, 666, 291, 370);
        Town beata = new Town("Beata", 724, 779, 407, 456);
        Town strykhaven = new Town("Strkhaven", 616, 679, 463, 502);

        towns.add(esselen);
        towns.add(yttar);
        towns.add(wylhien);
        towns.add(parundia);
        towns.add(jaccaranda);
        towns.add(albaran);
        towns.add(thortmanni);
        towns.add(rivinia);
        towns.add(tichih);
        towns.add(ergeren);
        towns.add(grangor);
        towns.add(mahdavikia);
        towns.add(kihromah);
        towns.add(ixara);
        towns.add(dagamura);
        towns.add(lapphalya);
        towns.add(feodori);
        towns.add(virst);
        towns.add(elvenhold);
        towns.add(beata);
        towns.add(strykhaven);

        Route esselenWylhien = new Route(esselen, wylhien, RouteType.PLAIN);
        Route esselenWylhien2 = new Route(esselen, wylhien, true); // river
        Route esselenYttar = new Route(esselen, yttar, RouteType.WOOD);
        Route esselenParundia = new Route(esselen, parundia, RouteType.WOOD);
        Route WylhienJaccaranda = new Route(wylhien, jaccaranda, RouteType.MOUNTAIN);
        Route WylhienParundia = new Route(wylhien, parundia, RouteType.PLAIN);
        Route WylhienAlbaran = new Route(wylhien, albaran, RouteType.DESERT);
        Route yttarParundia = new Route(yttar, parundia, true); // lake
        Route parundiaGrangor = new Route(parundia, grangor, true); // lake
        Route parundiaAlbaran = new Route(parundia, albaran, RouteType.DESERT);
        Route jaccarandaThrotmanni = new Route(jaccaranda, thortmanni, RouteType.MOUNTAIN);
        Route jaccarandaTichih = new Route(jaccaranda, tichih, RouteType.MOUNTAIN);
        Route throtmanniAlbaran = new Route(thortmanni, albaran, RouteType.DESERT);
        Route throtmanniRivinia = new Route(thortmanni, rivinia, RouteType.WOOD);
        Route throtmanniTichih = new Route(thortmanni, tichih, RouteType.PLAIN);
        Route throtmanniFeodori = new Route(thortmanni, feodori, RouteType.DESERT);
        Route kihromahDagamura = new Route(kihromah, dagamura, RouteType.WOOD);
        Route albaranDagamura = new Route(albaran, dagamura, RouteType.DESERT);
        Route dagamuraFeodori = new Route(dagamura, feodori, RouteType.DESERT);
        Route yttarGrangor = new Route(yttar, grangor, RouteType.MOUNTAIN);
        Route yttarGrangor2 = new Route(yttar, grangor, true); // lake
        Route grangorMahdavikia = new Route(grangor, mahdavikia, RouteType.MOUNTAIN);
        Route grangorMahdavikia2 = new Route(grangor, mahdavikia, true); // river
        Route mahdavikiaIxara = new Route(mahdavikia, ixara, true); // river
        Route mahdavikiaIxara2 = new Route(mahdavikia, ixara, RouteType.MOUNTAIN);
        Route dagamuraLapphalya = new Route(dagamura, lapphalya, RouteType.WOOD);
        Route ixaraLapphalya = new Route(ixara, lapphalya, RouteType.WOOD);
        Route ixaraDagamura = new Route(ixara, dagamura, RouteType.WOOD);
        Route ixaraVirst = new Route(ixara, virst, RouteType.PLAIN);
        Route ixaraVirst2 = new Route(ixara, virst, true); // river
        Route virstLapphalya = new Route(virst, lapphalya, RouteType.PLAIN);
        Route virstStrykhaven = new Route(virst, strykhaven, RouteType.MOUNTAIN);
        Route virstStrykhaven2 = new Route(virst, strykhaven, true); // lake
        Route virstElvenhold = new Route(virst, elvenhold, true); // lake
        Route lapphalyaElvenhold = new Route(lapphalya, elvenhold, RouteType.PLAIN);
        Route beataStrykhaven = new Route(beata, strykhaven, RouteType.PLAIN);
        Route beataElvenhold = new Route(beata, elvenhold, RouteType.PLAIN);
        Route beataElvenhold2 = new Route(beata, elvenhold, false); // lake
        Route elvenholdStrykhaven = new Route(elvenhold, strykhaven, true); // lake
        Route elvenholdRivinia = new Route(elvenhold, rivinia, false); // river
        Route riviniaTichih = new Route(rivinia, tichih, false); // river
        Route tichihErgeren = new Route(tichih, ergeren, RouteType.WOOD);
        Route elvenholdErgeren = new Route(elvenhold, ergeren, RouteType.WOOD);
        Route feodoriRivinia = new Route(feodori, rivinia, RouteType.WOOD);
        Route lapphalyaRivinia = new Route(lapphalya, rivinia, RouteType.WOOD);
        Route feodoriLapphalya = new Route(feodori, lapphalya, RouteType.WOOD);
        Route feodoriAlbaran = new Route(feodori, albaran, RouteType.WOOD);

        routes.add(parundiaGrangor);
        routes.add(WylhienAlbaran);
        routes.add(feodoriAlbaran);
        routes.add(feodoriLapphalya);
        routes.add(lapphalyaRivinia);
        routes.add(feodoriRivinia);
        routes.add(elvenholdErgeren);
        routes.add(beataElvenhold2);
        routes.add(virstStrykhaven2);
        routes.add(virstElvenhold);
        routes.add(yttarGrangor2);
        routes.add(grangorMahdavikia2);
        routes.add(mahdavikiaIxara2);
        routes.add(ixaraVirst2);
        routes.add(esselenWylhien2);
        routes.add(virstLapphalya);
        routes.add(virstStrykhaven);
        routes.add(esselenParundia);
        routes.add(esselenYttar);
        routes.add(esselenWylhien);
        routes.add(WylhienParundia);
        routes.add(WylhienJaccaranda);
        routes.add(yttarGrangor);
        routes.add(yttarParundia);
        routes.add(parundiaAlbaran);
        routes.add(albaranDagamura);
        routes.add(throtmanniAlbaran);
        routes.add(throtmanniFeodori);
        routes.add(throtmanniRivinia);
        routes.add(throtmanniTichih);
        routes.add(tichihErgeren);
        routes.add(riviniaTichih);
        routes.add(elvenholdRivinia);
        routes.add(elvenholdStrykhaven);
        routes.add(lapphalyaElvenhold);
        routes.add(beataElvenhold);
        routes.add(beataStrykhaven);
        routes.add(mahdavikiaIxara);
        routes.add(dagamuraFeodori);
        routes.add(dagamuraLapphalya);
        routes.add(ixaraDagamura);
        routes.add(ixaraLapphalya);
        routes.add(ixaraVirst);
        routes.add(jaccarandaThrotmanni);
        routes.add(jaccarandaTichih);
        routes.add(kihromahDagamura);
        routes.add(grangorMahdavikia);

        // initialize town graph
        this.aTownGraph = new TownGraph();
        this.aTownGraph.addEdges(routes);
        this.faceUpTokenPile = new ArrayList<>();

        // add all counters ingame to faceDownTokenPile
        // depending on mode, tokens are different
        if (this.mode == Mode.ELFENLAND) {
            // create tokens and add to list
            this.faceDownTokenStack = TokenStack.getFullTokenStackEL();
        } else if (this.mode == Mode.ELFENGOLD) {
            // TODO
            this.faceDownTokenStack = TokenStack.getFullTokenStackEG();
        }
        // initialize TravelCard array

        // initialize TravelCard objects
        if (this.mode == Mode.ELFENLAND) {
            //
            for (int j = 0; j < 6; j++) {
                for (int i = 0; i < 10; i++) {
                    AbstractCard newCard = new TravelCard(CardType.values()[j]);
                    faceDownCardPile.add(newCard);
                }
            }

            // initialize raft cards
            for (int i = 0; i < 12; i++) {
                AbstractCard newCard = new TravelCard(CardType.RAFT);
                faceDownCardPile.add(newCard);
            }

        }

    }

    /**
     * Adds a player to the players arraylist. If the max number of players has
     * already been reached, throw an error
     * 
     * @param player player to add to the game
     */
    public void addPlayer(Player player) throws IndexOutOfBoundsException {
        if (players.size() <= numberOfPlayers) {
            players.add(player);
            // give player an obstacle
            Obstacle aObstacle = new Obstacle();
            player.addToken(aObstacle);
        } else {
            throw new IndexOutOfBoundsException("The max number of players has already been reached.");
        }
    }

    // GETTER for number of players in the game instance
    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    public int getGameRoundsLimit() {
        return gameRoundsLimit;
    }

    public boolean isDestinationTownEnabled() {
        return destinationTownEnabled;
    }

    public boolean isWitchEnabled() {
        return witchEnabled;
    }

    public Mode getMode() {
        return mode;
    }

    public TownGoldOption getTownGoldOption() {
        return townGoldOption;
    }

    public static ArrayList<Town> getTowns() {
        return towns;
    }

    public static boolean notClickingOnATown(int x, int y) {
        for (Town t : towns) {
            if (t.minX < x && t.minY < y && t.maxX > x && t.maxY > y) {
                return false;
            }
        }

        return false;
    }

    public ArrayList<Player> getAllPlayers() {
        return players;
    }

    public Player getCurrentTurn() {
        for (Player p : this.players) {
            if (p.getIsTurn()) {
                return p;
            }
        }
        // no player's turn, shouldn't happen
        return null;
    }

    // TODO
    public void updateFaceUpToken(Token pToken) {

    }

    public String getGameID() {
        return gameID;
    }

    public void nextPhase() {
        if (currentPhase == 6) {

        } else {
            this.currentPhase++;
        }
    }

    public void phaseOne() {
        // shuffle
        aCardStack.shuffle();

        for (Player p : players) {
            for (int i = 0; i < 8; i++) {
                p.addCard(aCardStack.pop());
            }
        }
    }

    public void phaseTwo() {
        faceDownTokenStack.shuffle();

        for (Player p : players) {
            p.addToken(faceDownTokenStack.pop());
        }
    }

    @SuppressWarnings("unchecked")
    public void phaseThree() {
        for (int i = 0; i < 5; i++)
            faceUpTokenPile.add(faceDownTokenStack.pop());
        final List<Token> faceUpCopy = (ArrayList<Token>) faceUpTokenPile.clone();
        Player currentTurn = this.getCurrentTurn();
        String currentPlayerName = currentTurn.getName();
        // anonymous action class
        ACK_MANAGER.sendToSender(new Action() {

            @Override
            public boolean isValid() {
                return true;
            }

            @Override
            public void execute() {
                MinuetoWindow window = ClientMain.WINDOW;
                for (Token t : faceUpCopy) {
                    // change these coords
                    t.getTokenImage().setPos(200 + faceUpCopy.indexOf(t) * 20, 200);
                    window.draw(t.getTokenImage(), t.getTokenImage().getX(), t.getTokenImage().getY());
                }
                MinuetoMouseHandler tokenSelect = new MinuetoMouseHandler() {

                    @Override
                    public void handleMousePress(int xClicked, int yClicked, int arg2) {
                        List<TokenImage> imageList = faceUpCopy.stream().map((token) -> token.getTokenImage())
                                .collect(Collectors.toList());
                        for (TokenImage t : imageList) {
                            if (t.hasCollidePoint(xClicked, yClicked)) {
                                // inform server that user has selected t
                                ActionManager.getInstance()
                                        .sendActionAndGetReply(new TokenSelectedAction(
                                                ClientMain.currentSession.getSessionID(), t.getTokenName()));
                                break;
                            }
                        }
                    }

                    @Override
                    public void handleMouseRelease(int arg0, int arg1, int arg2) {
                    }

                    @Override
                    public void handleMouseMove(int arg0, int arg1) {
                    }
                };
                MinuetoEventQueue selectTokenQueue = new MinuetoEventQueue();
                window.registerMouseHandler(tokenSelect, selectTokenQueue);
            }
        }, currentPlayerName);
    }

    // method that checks if all players passed turn, to know if we move on to next
    // phase/round
    public boolean didAllPlayersPassTurn() {
        // checks if all players has turnPassed as true
        for (Player p : this.players) {
            // if one player doesn't have turnPassed as true, return false
            if (!p.getTurnPassed()) {
                return false;
            }
        }
        return true;
    }

    /*
     * Notes for moving boot:
     * - It's currently the move boot phase of the game (phase 5)
     * - It's currently player's turn to move boot (Player.getIsTurn())
     * - Player (from client) sends coordinate where they clicked
     * - server receives coordinate (or receive route clicked ?)
     * - server makes sure it's a valid coordinates
     * - server checks wether or not that town is adjacent to player's town (is
     * there a route) (send message to client if not valid)
     *
     * ^^^^^^^^^^^^^^^^^^^^^^^^^ (this part might not be necessary for m7)
     * - server checks wether the player has cards required to move to that town
     * - if it doesn't then return message to client
     * - if it does then do the move and take away player's cards
     * - send meesage to client to move boot (update state on all player's screens)
     *
     * ^^^^^^^^^^^^^^^^^^^^^^^^^
     * - Player can move as many time as he wishes, (until no more moves available
     * click on end turn)
     * - goes to next player's turn
     * - if everyone passed turn (keep track of this somehow) go to next phase of
     * the game (not necessary for m7?)
     */

    // @pre: current phase == 5 and it's player's turn and town is adjacent
    public void playerMovedBoot(Player p, Route r) {
        // check if the route is valid (i.e. it's adjacent to player's town)
        // if it's valid, move boot
        if (r.getSource() == p.getTown() || r.getDest() == p.getTown()) {
            // remove the cards from the player
            p.getCards().removeAll(r.getRequiredCards(p.getTown()));
            // get the town player's trying to go to
            Town dstTown;
            if (r.getSource() == p.getTown()) {
                dstTown = r.getDest();
            } else {
                dstTown = r.getSource();
            }
            // update player's town location done in Player.moveBoot(Town t)
            p.moveBoot(dstTown);
            // update the town's player list is done in p.moveBoot(dstTown)
        }
    }

    public int getCurrentPhase() {
        return this.currentPhase;
    }

    public TownGraph getTownGraph() {
        return this.aTownGraph;
    }

    public Town getTownByName(String townName) {
        for (Town t : towns) {
            if (t.getTownName().equalsIgnoreCase(townName)) {
                return t;
            }
        }
        return null;
    }
    /*
     * Operation: Game::loadGame(savedGame: Game)
     * Scope: Player;
     * Messages: Player::{gameSessionCreationConfirmation;
     * gameSessionCreationFailed_e}
     * Post: Upon success, sends a confirmation message to the player that their
     * gameState has been saved. Otherwise, sends a “gameSessionCreationFailed_e”
     * message.
     */

    /*
     * Operation: Game::availableBootColors(Set{color})
     * Scope: Game; Player;
     * Messages: Player::{availableBootColors}
     * Post: Sends the player a set of boot colors available for the player to
     * choose from.
     * 
     * Operation: Game::bootColorConfirmation(bootColor: color)
     * Scope: Player; Boot;
     * New: newBoot: Boot;
     * Messages: Player::{gameState; bootColorInvalid_e};
     * Post: Sends a new game state to the player that they are allocated a boot
     * with color of their choice if their choice of color is available. If the
     * chosen colour is taken, sends the player a message, which informs them to
     * pick another boot color.
     * 
     * Operation: Game::displayFaceUpTokens(tokensToDisplay: Set{Token})
     * Scope: GUI; Game; Token;
     * Messages: GUI::{displayFaceUpTokens}
     * Post: Sends all face up tokens available for display to the GUI.
     * 
     * Operation: Game::isYourTurn(player: Player)
     * Scope: Player; Game; Auction;
     * Messages: Player::{gameState}
     * Post: Sends a new game state and notifies the player it is now their turn.
     * 
     * Operation: Game::displayFaceUpCards(cardsToDisplay: Set{Card})
     * Scope: GUI; Game; Card;
     * Messages: GUI::{displayFaceUpCard}
     * Post: Sends all face up cards available for display to the GUI.
     * 
     * Operation: Game::displayAvailableBoardMovements()
     * Scope: GUI; Game; Road; Card;
     * Messages: GUI::{displayAvailableBoardMovements}
     * Post: Prompts the player to select a card to sacrifice and highlights all
     * available routes.
     * 
     * Operation: Game::promptForCardSacrifice(numToSacrifice: int)
     * Scope: GUI; Game; Card;
     * Messages: GUI::{promptForCardSacrifice}
     * Post: Sends a new game state to the player.
     * 
     * Operation: Game::displayBlockedRoutes()
     * Scope: GUI; Game; Road; Town; Card;
     * Messages: GUI::{displayBlockedRoutes}
     * Post: Highlight all blocked routes on the map.
     * 
     * Operation: Game::promptForObstacleToCrossByWitch()
     * Scope: GUI; Game; Road;
     * Messages: GUI::{displayBlockedRoutes}
     * Post: Highlight all blocked routes on the map.
     * 
     * Operation: Game::displayUnmarkedRoutes()
     * Scope: GUI, Game;
     * Messages: GUI::{displayUnmarkedRoutes}
     * Post: The effect of playTravelCard operation is to declare that the next step
     * is to place a travel counter on the map. All unmarked routes will be
     * highlighted.
     * 
     * Operation: Game::displayMarkedRoutes()
     * Scope: GUI; Game; Token;
     * Messages: GUI::{displayMarkedRoutes}
     * Post: The effect of playObstacle operation is to declare that the next step
     * is to place an obstacle on the map. All marked routes will be highlighted.
     * 
     * Operation: Game::displayMarkedWater()
     * Scope: GUI; Game; Token;
     * Messages: GUI::{displayMarkedWater}
     * Post: The effect of playSeaMonster operation is to declare that the next step
     * is to place a sea monster on the map. All marked water will be highlighted.
     * 
     * Operation: Game::promptForCounterSacrifice()
     * Scope: GUI; Game; Token;
     * Messages: GUI::{promptForCounterSacrifice}
     * Post: The effect of playDoubleTransportSpell operation is to declare that the
     * next step is to place a double transportation counter on the map. The player
     * will be prompted for which counter they would like to sacrifice.
     * 
     * Operation: Game::promptForTwoCounterSwap()
     * Scope: Game; GUI; Road;
     * Messages: GUI::{promptForTwoCounterSwap}
     * Post: The effect of playExchangeSpell operation is to declare that the next
     * step is to place an exchange spell on the map. Prompts the player to select 2
     * counters placed on the map and confirms the swap.
     * 
     * Operation: Game::displayRoundNumberAndStartingPlayer(roundNumber: int,
     * player: Player)
     * Scope: Game, GUI
     * Messages: GUI::{displayFinalResults}
     * Post: Displays the current round number and the starting player for this
     * round.
     * 
     * Operation: Game::displayFinalResults()
     * Scope: Game; GUI
     * Messages: GUI::{displayFinalResults}
     * Post: Displays the final tallies for each of the players in the lobby.
     * 
     * Operation: Game::displayGameWinner(player: Player)
     * Scope: Game, GUI
     * Messages: GUI::{displayGameWinner}
     * Post: Display the game winner to all players.
     * 
     * Operation: Game::gameState()
     * Scope: Game; Player;
     * Messages: Player::{gameState}
     * Post: Sends a new game state to the player.
     * 
     * Operation: Game::promptToKeepTransportationCounter()
     * Scope: GUI; Game; Token;
     * Messages: GUI::{promptToKeepTransportationCounter}
     * Post: Prompts which transportation counter the player would like to keep in
     * their hand.
     * 
     * Operation: Game::displayLatestAuctionInfo(auction: Auction)
     * Scope: GUI; Auction; Token;
     * Messages: GUI::{displayLatestAuctionInfo}
     * Post: Displays the latest auction information to the player before they make
     * their bid.
     * 
     * Operation: Game::displayBidWinner()
     * Scope: GUI; Auction; Token;
     * Messages: GUI::{displayBidWinner}
     * Post: Displays the bid winner.
     * 
     * Operation: Game::displayNoBidsMade()
     * Scope: GUI; Auction;
     * Messages: GUI::{displayNoBidsMade}
     * Post: If no players have made a bid on a particular auctioned counter, then
     * display to all players that no players have made a bid.
     * 
     */

}
