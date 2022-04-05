package clientsrc;
/*
Instance of Game represent a single elfen game.

min 2 players
max 6 players
 */

import java.util.ArrayList;

import org.minueto.MinuetoFileException;

import serversrc.CardType;

// import serversrc.Card;
// import serversrc.GoldCard;
// import serversrc.Mode;
// import serversrc.Player;
// import serversrc.Route;
// import serversrc.Town;
// import serversrc.TownGoldOption;

public class Game {

    private static ArrayList<ClientPlayer> players;
    private int numberOfPlayers;
    public static ArrayList<Town> towns;
    private ArrayList<ClientRoute> routes;
    private int currentRound;
    private int currentPhase;
    private int gameRoundsLimit;
    private boolean destinationTownEnabled;
    private boolean witchEnabled;
    private Mode mode;
    private TownGoldOption townGoldOption;
    private static ArrayList<CardSprite> faceDownCardPile;
    private ArrayList<CardSprite> faceUpCardPile;
    private TownGraph aTownGraph;
    private TokenSprite auctionToken = null;
    private int auctionBid = 0;
    // private ArrayList<GoldCard> goldCardPile;
    // private Auction auction; not doing this now

    /**
     * CONSTRUCTOR : creates an instance of Game object
     */
    public Game(int numberOfPlayers, int gameRoundsLimit, boolean destinationTownEnabled, boolean witchEnabled,
            Mode mode, TownGoldOption townGoldOption) {

        this.players = new ArrayList<>();
        this.faceDownCardPile = new ArrayList<>();
        this.faceUpCardPile = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayers;
        this.gameRoundsLimit = gameRoundsLimit;
        this.destinationTownEnabled = destinationTownEnabled;
        this.witchEnabled = witchEnabled;
        this.townGoldOption = townGoldOption;
        this.mode = mode;
        this.currentRound = 1;

        towns = new ArrayList<>();
        routes = new ArrayList<>();

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

        ClientRoute esselenWylhien = new ClientRoute(esselen, wylhien);
        ClientRoute esselenYttar = new ClientRoute(esselen, yttar);
        ClientRoute esselenParundia = new ClientRoute(esselen, parundia);
        ClientRoute WylhienJaccaranda = new ClientRoute(wylhien, jaccaranda);
        ClientRoute WyhlienParundia = new ClientRoute(wylhien, parundia);
        ClientRoute yttarParundia = new ClientRoute(yttar, parundia);
        ClientRoute parundiaAlbaran = new ClientRoute(parundia, albaran);
        ClientRoute jaccarandaThrotmanni = new ClientRoute(jaccaranda, thortmanni);
        ClientRoute jaccarandaTichih = new ClientRoute(jaccaranda, tichih);
        ClientRoute throtmanniAlbaran = new ClientRoute(thortmanni, albaran);
        ClientRoute throtmanniRivinia = new ClientRoute(thortmanni, rivinia);
        ClientRoute throtmanniTichih = new ClientRoute(thortmanni, tichih);
        ClientRoute throtmanniFeodori = new ClientRoute(thortmanni, feodori);
        ClientRoute kihromahDagamura = new ClientRoute(kihromah, dagamura);
        ClientRoute albaranDagamura = new ClientRoute(albaran, dagamura);
        ClientRoute dagamuraFeodori = new ClientRoute(dagamura, feodori);
        ClientRoute yttarGrangor = new ClientRoute(yttar, grangor);
        ClientRoute grangorMahdavikia = new ClientRoute(grangor, mahdavikia);
        ClientRoute mahdavikiaIxara = new ClientRoute(mahdavikia, ixara);
        ClientRoute dagamuraLapphalya = new ClientRoute(dagamura, lapphalya);
        ClientRoute ixaraLapphalya = new ClientRoute(ixara, lapphalya);
        ClientRoute ixaraDagamura = new ClientRoute(ixara, dagamura);
        ClientRoute ixaraVirst = new ClientRoute(ixara, virst);
        ClientRoute virstLapphalya = new ClientRoute(virst, lapphalya);
        ClientRoute virstStrykhaven = new ClientRoute(virst, strykhaven);
        ClientRoute lapphalyaElvenhold = new ClientRoute(lapphalya, elvenhold);
        ClientRoute beataStrykhaven = new ClientRoute(beata, strykhaven);
        ClientRoute beataElvenhold = new ClientRoute(beata, elvenhold);
        ClientRoute elvenholdStrykhaven = new ClientRoute(elvenhold, strykhaven);
        ClientRoute elvenholdRivinia = new ClientRoute(elvenhold, rivinia);
        ClientRoute riviniaTichih = new ClientRoute(rivinia, tichih);
        ClientRoute tichihErgeren = new ClientRoute(tichih, ergeren);

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

        aTownGraph = new TownGraph();
        aTownGraph.addEdges(routes);

    }

    public void setAuctionToken(TokenSprite pTok){
        this.auctionToken = pTok;
    }

    public TownGraph getTownGraph() {
        return this.aTownGraph;
    }

    /**
     * Adds a player to the players arraylist. If the max number of players has
     * already been reached, throw an error
     * 
     * @param player player to add to the game
     */
    public void addPlayer(ClientPlayer player) throws IndexOutOfBoundsException {
        if (players.size() <= numberOfPlayers) {
            players.add(player);
            Town elvenhold = Game.getTownByName("Elvenhold");
            elvenhold.addPlayer(player);
            player.setTown(elvenhold);
        } else {
            throw new IndexOutOfBoundsException("The max number of players has already been reached.");
        }
    }

    public void clearAllTokensOnMap(){
        for(ClientRoute r: routes){
            r.clearToken();
        }
    }
    
    // GETTER for number of players in the game instance
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getNumberOfRounds() {
        return gameRoundsLimit;
    }

    public boolean isDestinationTownEnabled() {
        return destinationTownEnabled;
    }

    public boolean isWitchEnabled() {
        return witchEnabled;
    }

    public TownGoldOption getTownGoldOption() {
        return townGoldOption;
    }

    public Mode getMode() {
        return mode;
    }

    public static ArrayList<Town> getTowns() {
        return towns;
    }

    // GETTER: gets town in towns by name
    public static Town getTownByName(String name) {
        for (Town t : towns) {
            if (t.getTownName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    public static boolean notClickingOnATown(int x, int y) {
        for (Town t : towns) {

            if (t.minX < x && t.minY < y && t.maxX > x && t.maxY > y) {
                return false;
            }
        }

        return false;
    }

    public static CardSprite getFaceDownCard(String cardString) throws MinuetoFileException {
        for (CardType cT : CardType.values()) {
            if (cT.toString().equalsIgnoreCase(cardString)) {
                CardSprite finCard = new CardSprite(cT);
                return finCard;
            }
        }
        throw new IllegalArgumentException();

    }

    public ArrayList<ClientPlayer> getPlayers() {
        return players;
    }

    public void setPhase(int phase) {
        this.currentPhase = phase;
    }

    public int getCurrentPhase() {
        return this.currentPhase;
    }

}
