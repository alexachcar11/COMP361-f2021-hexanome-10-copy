package serversrc;
/*
Instance of Game represent a single elfen game.

min 2 players
max 6 players
 */

import java.util.ArrayList;
import java.util.*;

// import clientsrc.Card;
// import clientsrc.GoldCard;
// import clientsrc.Mode;
// import clientsrc.Player;
// import clientsrc.Route;
// import clientsrc.Town;
// import clientsrc.TownGoldOption;

public class ServerGame {

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
    public ArrayList<Card> faceDownCardPile;
    public ArrayList<Card> faceUpCardPile;
    public ArrayList<GoldCard> goldCardPile;
    //public Auction auction; not doing this now
    public ArrayList<Token> faceUpTokenPile;
    public TokenStack faceDownTokenPile;
    private String gameID;
    public TownGraph aTownGraph;


    /**
     * CONSTRUCTOR : creates an instance of Game object
     */
    public ServerGame(int numberOfPlayers, int gameRoundsLimit, boolean destinationTownEnabled, boolean witchEnabled, Mode mode, TownGoldOption townGoldOption, String gameID) {

        this.players = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayers;
        this.gameRoundsLimit = gameRoundsLimit;
        this.destinationTownEnabled = destinationTownEnabled;
        this.witchEnabled = witchEnabled;
        this.mode = mode;
        this.currentRound = 1;
        this.gameID = gameID;


        towns = new ArrayList<>();
        routes = new ArrayList<>();

        // TODO: initialize faceDownCardPile, faceUpCardPile, goldCardPile and auction depending on the mode

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

        // TODO: add region to the construction of routes (Dijian (me) will do this soon)
        Route esselenWylhien = new Route(esselen, wylhien);
        Route esselenYttar = new Route(esselen, yttar);
        Route esselenParundia = new Route(esselen, parundia);
        Route WylhienJaccaranda = new Route(wylhien, jaccaranda);
        Route WyhlienParundia = new Route(wylhien, parundia);
        Route yttarParundia = new Route(yttar, parundia);
        Route parundiaAlbaran = new Route(parundia, albaran);
        Route jaccarandaThrotmanni = new Route(jaccaranda, thortmanni);
        Route jaccarandaTichih = new Route(jaccaranda, tichih);
        Route throtmanniAlbaran = new Route(thortmanni, albaran);
        Route throtmanniRivinia = new Route(thortmanni, rivinia);
        Route throtmanniTichih = new Route(thortmanni, tichih);
        Route throtmanniFeodori = new Route(thortmanni, feodori);
        Route kihromahDagamura = new Route(kihromah, dagamura);
        Route albaranDagamura = new Route(albaran, dagamura);
        Route dagamuraFeodori = new Route(dagamura, feodori);
        Route yttarGrangor = new Route(yttar, grangor);
        Route grangorMahdavikia = new Route(grangor, mahdavikia);
        Route mahdavikiaIxara = new Route(mahdavikia, ixara);
        Route dagamuraLapphalya = new Route(dagamura, lapphalya);
        Route ixaraLapphalya = new Route(ixara, lapphalya);
        Route ixaraDagamura = new Route(ixara, dagamura);
        Route ixaraVirst = new Route(ixara, virst);
        Route virstLapphalya = new Route(virst, lapphalya);
        Route virstStrykhaven = new Route(virst, strykhaven);
        Route lapphalyaElvenhold = new Route(lapphalya, elvenhold);
        Route beataStrykhaven = new Route(beata, strykhaven);
        Route beataElvenhold = new Route(beata, elvenhold);
        Route elvenholdStrykhaven = new Route(elvenhold, strykhaven);
        Route elvenholdRivinia = new Route(elvenhold, rivinia);
        Route riviniaTichih = new Route(rivinia, tichih);
        Route tichihErgeren = new Route(tichih, ergeren);

        routes.add(virstLapphalya);
        routes.add(virstStrykhaven);
        routes.add(esselenParundia);
        routes.add(esselenYttar);
        routes.add(esselenWylhien);
        routes.add(WyhlienParundia);
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

        // add all counters ingame to faceDownTokenPile
        // first make list with all tokens:
        // depending on mode, tokens are different
        List<Token> allTokens = new ArrayList<>();
        // list of the counter types
        if (this.mode == Mode.ELFENLAND){
            //create tokens and add to list
            for (int j = 0; j<6; j++){
                for (int i = 0; i < 8; i++){
                    Token tok = new TransportationCounter(CardType.values()[j]);
                    allTokens.add(tok);
                }
            }
        }
        else if (this.mode == Mode.ELFENGOLD){
            // TODO
        }

        this.faceDownTokenPile = new TokenStack(allTokens);
    }

    public Town getTownByName(String townName){
        for(Town t:towns){
            if (t.getTownName().equalsIgnoreCase(townName)){
                return t;
            }
        }
        return null;
    }

    /**
     * Adds a player to the players arraylist. If the max number of players has already been reached, throw an error
     * @param player player to add to the game
     */
    public void addPlayer(Player player) throws IndexOutOfBoundsException{
        if (players.size() <= numberOfPlayers) {
            players.add(player);
        } else {
            throw new IndexOutOfBoundsException("The max number of players has already been reached.");
        }
    }

    //GETTER for number of players in the game instance
    public int getNumberOfPlayers() { 
        return this.numberOfPlayers;
    }

    public static ArrayList<Town> getTowns() { 
        return towns;
    }

    public static boolean notClickingOnATown(int x, int y) { 
        for(Town t: towns) { 
            if (t.minX < x && t.minY < y && t.maxX > x && t.maxY > y) { 
                return false;
            }
        }

        return false;
    }

    public ArrayList<Player> getAllPlayers() {
        return players;
    }
    // TODO
    public void updateFaceUpToken(Token pToken){

    }

    public String getGameID() {
        return gameID;
    }

    public void nextPhase(){
        if (currentPhase == 6){

        }
        else{
            this.currentPhase++;
        }
    }

    /*
    *   Notes for moving boot:
    *   - It's currently the move boot phase of the game (phase 5)
    *   - It's currently player's turn to move boot (Player.getIsTurn())
    *   - Player (from client) sends coordinate where they clicked
    *   - server receives coordinate (or receive route clicked ?)
    *   - server makes sure it's a valid coordinates
    *   - server checks wether or not that town is adjacent to player's town (is there a route) (send message to client if not valid)
    *
    *   ^^^^^^^^^^^^^^^^^^^^^^^^^ (this part might not be necessary for m7)
    *   - server checks wether the player has cards required to move to that town
    *   - if it doesn't then return message to client
    *   - if it does then do the move and take away player's cards
    *   - send meesage to client to move boot (update state on all player's screens)
    *
    *   ^^^^^^^^^^^^^^^^^^^^^^^^^
    *   - Player can move as many time as he wishes, (until no more moves available click on end turn)
    *   - goes to next player's turn
    *   - if everyone passed turn (keep track of this somehow) go to next phase of the game (not necessary for m7?)
    */

    // @pre: current phase == 5 and it's player's turn and town is adjacent
    public void playerMovedBoot(Player p, Route r){
        
        // check if the route is valid (i.e. it's adjacent to player's town)
        // if it's valid, move boot
        if (r.getSource() == p.getTown() || r.getDest() == p.getTown()){
            // remove the cards from the player
            // update player's town location
            // update the town's player list
        }
    }

    public int getCurrentPhase(){
        return this.currentPhase;
    }

    public TownGraph getTownGraph(){
        return this.aTownGraph;
    }


    /*
    Operation: Game::loadGame(savedGame: Game)
    Scope: Player;
    Messages: Player::{gameSessionCreationConfirmation; gameSessionCreationFailed_e}
    Post: Upon success, sends a confirmation message to the player that their gameState has been saved. Otherwise, sends a “gameSessionCreationFailed_e” message.
    */

    /*
    Operation: Game::availableBootColors(Set{color})
    Scope: Game; Player;
    Messages: Player::{availableBootColors}
    Post: Sends the player a set of boot colors available for the player to choose from.

    Operation: Game::bootColorConfirmation(bootColor: color)
    Scope: Player; Boot;
    New: newBoot: Boot;
    Messages: Player::{gameState; bootColorInvalid_e};
    Post: Sends a new game state to the player that they are allocated a boot with color of their choice if their choice of color is available. If the chosen colour is taken, sends the player a message, which informs them to pick another boot color.

    Operation: Game::displayFaceUpTokens(tokensToDisplay: Set{Token})
    Scope: GUI; Game; Token;
    Messages: GUI::{displayFaceUpTokens}
    Post: Sends all face up tokens available for display to the GUI.

    Operation: Game::isYourTurn(player: Player)
    Scope: Player; Game; Auction;
    Messages: Player::{gameState}
    Post: Sends a new game state and notifies the player it is now their turn.

    Operation: Game::displayFaceUpCards(cardsToDisplay: Set{Card})
    Scope: GUI; Game; Card;
    Messages: GUI::{displayFaceUpCard}
    Post: Sends all face up cards available for display to the GUI.

    Operation: Game::displayAvailableBoardMovements()
    Scope: GUI; Game; Road; Card;
    Messages: GUI::{displayAvailableBoardMovements}
    Post: Prompts the player to select a card to sacrifice and highlights all available routes.

    Operation: Game::promptForCardSacrifice(numToSacrifice: int)
    Scope: GUI; Game; Card;
    Messages: GUI::{promptForCardSacrifice}
    Post: Sends a new game state to the player.

    Operation: Game::displayBlockedRoutes()
    Scope: GUI; Game; Road; Town; Card;
    Messages: GUI::{displayBlockedRoutes}
    Post: Highlight all blocked routes on the map.

    Operation: Game::promptForObstacleToCrossByWitch()
    Scope: GUI; Game; Road;
    Messages: GUI::{displayBlockedRoutes}
    Post: Highlight all blocked routes on the map.

    Operation: Game::displayUnmarkedRoutes()
    Scope: GUI, Game;
    Messages: GUI::{displayUnmarkedRoutes}
    Post: The effect of playTravelCard operation is to declare that the next step is to place a travel counter on the map. All unmarked routes will be highlighted.

    Operation: Game::displayMarkedRoutes()
    Scope: GUI; Game; Token;
    Messages: GUI::{displayMarkedRoutes}
    Post: The effect of playObstacle operation is to declare that the next step is to place an obstacle on the map. All marked routes will be highlighted.

    Operation: Game::displayMarkedWater()
    Scope: GUI; Game; Token;
    Messages: GUI::{displayMarkedWater}
    Post: The effect of playSeaMonster operation is to declare that the next step is to place a sea monster on the map. All marked water will be highlighted.

    Operation: Game::promptForCounterSacrifice()
    Scope: GUI; Game; Token;
    Messages: GUI::{promptForCounterSacrifice}
    Post: The effect of playDoubleTransportSpell operation is to declare that the next step is to place a double transportation counter on the map. The player will be prompted for which counter they would like to sacrifice.

    Operation: Game::promptForTwoCounterSwap()
    Scope: Game; GUI; Road;
    Messages: GUI::{promptForTwoCounterSwap}
    Post: The effect of playExchangeSpell operation is to declare that the next step is to place an exchange spell on the map. Prompts the player to select 2 counters placed on the map and confirms the swap.

    Operation: Game::displayRoundNumberAndStartingPlayer(roundNumber: int, player: Player)
    Scope: Game, GUI
    Messages: GUI::{displayFinalResults}
    Post: Displays the current round number and the starting player for this round.

    Operation: Game::displayFinalResults()
    Scope: Game; GUI
    Messages: GUI::{displayFinalResults}
    Post: Displays the final tallies for each of the players in the lobby.

    Operation: Game::displayGameWinner(player: Player)
    Scope: Game, GUI
    Messages: GUI::{displayGameWinner}
    Post: Display the game winner to all players.

    Operation: Game::gameState()
    Scope: Game; Player;
    Messages: Player::{gameState}
    Post: Sends a new game state to the player.

    Operation: Game::promptToKeepTransportationCounter()
    Scope: GUI; Game; Token;
    Messages: GUI::{promptToKeepTransportationCounter}
    Post: Prompts which transportation counter the player would like to keep in their hand.

    Operation: Game::displayLatestAuctionInfo(auction: Auction)
    Scope: GUI; Auction; Token;
    Messages: GUI::{displayLatestAuctionInfo}
    Post: Displays the latest auction information to the player before they make their bid.

    Operation: Game::displayBidWinner()
    Scope: GUI; Auction; Token;
    Messages: GUI::{displayBidWinner}
    Post: Displays the bid winner.

    Operation: Game::displayNoBidsMade()
    Scope: GUI; Auction;
    Messages: GUI::{displayNoBidsMade}
    Post: If no players have made a bid on a particular auctioned counter, then display to all players that no players have made a bid.

     */

}
