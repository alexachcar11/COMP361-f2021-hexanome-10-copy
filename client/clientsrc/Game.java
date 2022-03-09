package clientsrc;
/*
Instance of Game represent a single elfen game.

min 2 players
max 6 players
 */

import java.util.ArrayList;

// import serversrc.Card;
// import serversrc.GoldCard;
// import serversrc.Mode;
// import serversrc.Player;
// import serversrc.Route;
// import serversrc.Town;
// import serversrc.TownGoldOption;

public class Game {

    private ArrayList<Player> players;
    private int numberOfPlayers;
    public static ArrayList<Town> towns;
    private ArrayList<Route> routes;
    private int currentRound;
    private int currentPhase;
    private int gameRoundsLimit;
    private boolean destinationTownEnabled;
    private boolean witchEnabled;
    private Mode mode;
    private TownGoldOption townGoldOption;
    private static ArrayList<Card> faceDownCardPile;
    private ArrayList<Card> faceUpCardPile;
    private ArrayList<GoldCard> goldCardPile;
    //private Auction auction; not doing this now


    /**
     * CONSTRUCTOR : creates an instance of Game object
     */
    public Game(int numberOfPlayers, int gameRoundsLimit, boolean destinationTownEnabled, boolean witchEnabled, Mode mode, TownGoldOption townGoldOption) {

        this.players = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayers;
        this.gameRoundsLimit = gameRoundsLimit;
        this.destinationTownEnabled = destinationTownEnabled;
        this.witchEnabled = witchEnabled;
        this.townGoldOption = townGoldOption;
        this.mode = mode;
        this.currentRound = 1;

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

    public static boolean notClickingOnATown(int x, int y) {
        for(Town t: towns) {

            if (t.minX < x && t.minY < y && t.maxX > x && t.maxY > y) {
                return false;
            }
        }

        return false;
    }

    public static Card getFaceDownCard(String cardString){
        for (Card aCard : faceDownCardPile){
            if (aCard.getName().equalsIgnoreCase(cardString)){
                return aCard;
            }
        }
        return null; //hopefully this never happens LOL
    }

}
