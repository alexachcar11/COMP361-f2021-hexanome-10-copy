package clientsrc;
/*
Instance of Game represent a single elfen game.

min 2 players
max 6 players
 */

import java.util.ArrayList;

import org.minueto.MinuetoFileException;

import serversrc.CardType;
import serversrc.RouteType;

public class Game {

    private static ArrayList<ClientPlayer> players;
    private int numberOfPlayers;
    public static ArrayList<ClientTown> towns;
    private static ArrayList<ClientRoute> routes;
    private int currentRound = 1;
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

        ClientTown esselen = new ClientTown("Esselen", 38, 103, 99, 152);
        ClientTown yttar = new ClientTown("Yttar", 35, 98, 222, 274);
        ClientTown wylhien = new ClientTown("Wylhien", 187, 234, 30, 75);
        ClientTown parundia = new ClientTown("Parundia", 172, 241, 172, 227);
        ClientTown jaccaranda = new ClientTown("Jaccaranda", 312, 381, 61, 119);
        ClientTown albaran = new ClientTown("AlBaran", 280, 343, 227, 283);
        ClientTown thortmanni = new ClientTown("Throtmanni", 451, 518, 129, 188);
        ClientTown rivinia = new ClientTown("Rivinia", 555, 621, 205, 256);
        ClientTown tichih = new ClientTown("Tichih", 604, 662, 79, 135);
        ClientTown ergeren = new ClientTown("ErgEren", 716, 776, 210, 259);
        ClientTown grangor = new ClientTown("Grangor", 49, 112, 366, 411);
        ClientTown mahdavikia = new ClientTown("MahDavikia", 57, 136, 482, 533);
        ClientTown kihromah = new ClientTown("Kihromah", 164, 223, 314, 367);
        ClientTown ixara = new ClientTown("Ixara", 257, 322, 489, 534);
        ClientTown dagamura = new ClientTown("DagAmura", 281, 339, 345, 394);
        ClientTown lapphalya = new ClientTown("Lapphalya", 415, 482, 383, 437);
        ClientTown feodori = new ClientTown("Feodori", 411, 472, 259, 317);
        ClientTown virst = new ClientTown("Virst", 478, 536, 491, 541);
        ClientTown elvenhold = new ClientTown("Elvenhold", 577, 666, 291, 370);
        ClientTown beata = new ClientTown("Beata", 724, 779, 407, 456);
        ClientTown strykhaven = new ClientTown("Strkhaven", 616, 679, 463, 502);

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

        int[] essWyl1 = new int[] { 92, 109, 59, 75 };
        ClientRoute esselenWylhien = new ClientRoute(esselen, wylhien, RouteType.PLAIN, essWyl1);

        int[] essWyl2 = new int[] { 151, 166, 106, 121 };
        ClientRoute esselenWylhien2 = new ClientRoute(esselen, wylhien, RouteType.RIVER, essWyl2); // river

        int[] essYtt = new int[] { 40, 55, 193, 208 };
        ClientRoute esselenYttar = new ClientRoute(esselen, yttar, RouteType.WOOD, essYtt);

        int[] essPar = new int[] { 124, 139, 167, 182 };
        ClientRoute esselenParundia = new ClientRoute(esselen, parundia, RouteType.WOOD, essPar);

        int[] wylJac = new int[] { 281, 296, 61, 76 };
        ClientRoute WylhienJaccaranda = new ClientRoute(wylhien, jaccaranda, RouteType.MOUNTAIN, wylJac);

        int[] wylPar = new int[] { 172, 187, 131, 146 };
        ClientRoute WylhienParundia = new ClientRoute(wylhien, parundia, RouteType.PLAIN, wylPar);

        int[] wylAlb = new int[] { 249, 264, 135, 150 };
        ClientRoute WylhienAlbaran = new ClientRoute(wylhien, albaran, RouteType.DESERT, wylAlb);

        int[] yttPar = new int[] { 121, 136, 236, 251 };
        ClientRoute yttarParundia = new ClientRoute(yttar, parundia, RouteType.LAKE, yttPar); // lake

        int[] parGra = new int[] { 126, 141, 287, 302 };
        ClientRoute parundiaGrangor = new ClientRoute(parundia, grangor, RouteType.LAKE, parGra); // lake

        int[] parAlb = new int[] { 261, 276, 213, 228 };
        ClientRoute parundiaAlbaran = new ClientRoute(parundia, albaran, RouteType.DESERT, parAlb);

        int[] jacThr = new int[] { 384, 399, 135, 150 };
        ClientRoute jaccarandaThrotmanni = new ClientRoute(jaccaranda, thortmanni, RouteType.MOUNTAIN, jacThr);

        int[] jacTic = new int[] { 489, 504, 84, 99 };
        ClientRoute jaccarandaTichih = new ClientRoute(jaccaranda, tichih, RouteType.MOUNTAIN, jacTic);

        int[] thrAlb = new int[] { 383, 398, 203, 218 };
        ClientRoute throtmanniAlbaran = new ClientRoute(thortmanni, albaran, RouteType.DESERT, thrAlb);

        int[] troRiv = new int[] { 534, 549, 191, 206 };
        ClientRoute throtmanniRivinia = new ClientRoute(thortmanni, rivinia, RouteType.WOOD, troRiv);

        int[] thrTic = new int[] { 557, 572, 146, 161 };
        ClientRoute throtmanniTichih = new ClientRoute(thortmanni, tichih, RouteType.PLAIN, thrTic);

        int[] thrFeo = new int[] { 444, 459, 223, 238 };
        ClientRoute throtmanniFeodori = new ClientRoute(thortmanni, feodori, RouteType.DESERT, thrFeo);

        int[] kihDag = new int[] { 243, 258, 347, 362 };
        ClientRoute kihromahDagamura = new ClientRoute(kihromah, dagamura, RouteType.WOOD, kihDag);

        int[] albDag = new int[] { 303, 318, 316, 331 };
        ClientRoute albaranDagamura = new ClientRoute(albaran, dagamura, RouteType.DESERT, albDag);

        int[] dagFeo = new int[] { 366, 381, 319, 334 };
        ClientRoute dagamuraFeodori = new ClientRoute(dagamura, feodori, RouteType.DESERT, dagFeo);

        int[] yttGra1 = new int[] { 46, 61, 323, 338 };
        ClientRoute yttarGrangor = new ClientRoute(yttar, grangor, RouteType.MOUNTAIN, yttGra1);

        int[] yttGra2 = new int[] { 80, 95, 312, 327 };
        ClientRoute yttarGrangor2 = new ClientRoute(yttar, grangor, RouteType.LAKE, yttGra2); // lake

        int[] graMah1 = new int[] { 51, 66, 454, 469 };
        ClientRoute grangorMahdavikia = new ClientRoute(grangor, mahdavikia, RouteType.MOUNTAIN, graMah1);

        int[] graMah2 = new int[] { 88, 103, 443, 458 };
        ClientRoute grangorMahdavikia2 = new ClientRoute(grangor, mahdavikia, RouteType.LAKE, graMah2); // river

        int[] mahIxa1 = new int[] { 167, 182, 514, 529 };
        ClientRoute mahdavikiaIxara = new ClientRoute(mahdavikia, ixara, RouteType.RIVER, mahIxa1); // river

        int[] mahIxa2 = new int[] { 246, 261, 555, 570 };
        ClientRoute mahdavikiaIxara2 = new ClientRoute(mahdavikia, ixara, RouteType.MOUNTAIN, mahIxa2);

        int[] dagLap = new int[] { 355, 370, 405, 420 };
        ClientRoute dagamuraLapphalya = new ClientRoute(dagamura, lapphalya, RouteType.WOOD, dagLap);

        int[] ixaLap = new int[] { 370, 385, 466, 481 };
        ClientRoute ixaraLapphalya = new ClientRoute(ixara, lapphalya, RouteType.WOOD, ixaLap);

        int[] ixaDag = new int[] { 285, 300, 446, 461 };
        ClientRoute ixaraDagamura = new ClientRoute(ixara, dagamura, RouteType.WOOD, ixaDag);

        int[] ixaVir1 = new int[] { 380, 395, 549, 564 };
        ClientRoute ixaraVirst = new ClientRoute(ixara, virst, RouteType.PLAIN, ixaVir1);

        int[] ixaVir2 = new int[] { 439, 454, 553, 568 };
        ClientRoute ixaraVirst2 = new ClientRoute(ixara, virst, RouteType.RIVER, ixaVir2); // river

        int[] virLap = new int[] { 453, 468, 472, 487 };
        ClientRoute virstLapphalya = new ClientRoute(virst, lapphalya, RouteType.PLAIN, virLap);

        int[] virStr1 = new int[] { 586, 601, 561, 576 };
        ClientRoute virstStrykhaven = new ClientRoute(virst, strykhaven, RouteType.MOUNTAIN, virStr1);

        int[] virStr2 = new int[] { 579, 594, 486, 501 };
        ClientRoute virstStrykhaven2 = new ClientRoute(virst, strykhaven, RouteType.LAKE, virStr2); // lake

        int[] virElv = new int[] { 565, 580, 436, 451 };
        ClientRoute virstElvenhold = new ClientRoute(virst, elvenhold, RouteType.LAKE, virElv); // lake

        int[] lapElv = new int[] { 528, 543, 394, 409 };
        ClientRoute lapphalyaElvenhold = new ClientRoute(lapphalya, elvenhold, RouteType.PLAIN, lapElv);

        int[] beaStr = new int[] { 730, 745, 493, 508 };
        ClientRoute beataStrykhaven = new ClientRoute(beata, strykhaven, RouteType.PLAIN, beaStr);

        int[] beaElv1 = new int[] { 700, 715, 400, 415 };
        ClientRoute beataElvenhold = new ClientRoute(beata, elvenhold, RouteType.PLAIN, beaElv1);

        int[] beaElv2 = new int[] { 728, 743, 377, 392 };
        ClientRoute beataElvenhold2 = new ClientRoute(beata, elvenhold, RouteType.LAKE, beaElv2); // lake

        int[] elvStr = new int[] { 622, 637, 427, 442 };
        ClientRoute elvenholdStrykhaven = new ClientRoute(elvenhold, strykhaven, RouteType.LAKE, elvStr); // lake

        int[] elvRiv = new int[] { 637, 652, 257, 272 };
        ClientRoute elvenholdRivinia = new ClientRoute(rivinia, elvenhold, RouteType.RIVER, elvRiv); // river

        int[] rivTic = new int[] { 621, 636, 176, 191 };
        ClientRoute riviniaTichih = new ClientRoute(tichih, rivinia, RouteType.RIVER, rivTic); // river

        int[] ticErg = new int[] { 689, 704, 178, 193 };
        ClientRoute tichihErgeren = new ClientRoute(tichih, ergeren, RouteType.WOOD, ticErg);

        int[] elvErg = new int[] { 719, 734, 296, 311 };
        ClientRoute elvenholdErgeren = new ClientRoute(elvenhold, ergeren, RouteType.WOOD, elvErg);

        int[] feoRiv = new int[] { 490, 505, 259, 274 };
        ClientRoute feodoriRivinia = new ClientRoute(feodori, rivinia, RouteType.WOOD, feoRiv);

        int[] lapRiv = new int[] { 511, 526, 316, 331 };
        ClientRoute lapphalyaRivinia = new ClientRoute(lapphalya, rivinia, RouteType.WOOD, lapRiv);

        int[] feoLap = new int[] { 451, 466, 344, 359 };
        ClientRoute feodoriLapphalya = new ClientRoute(feodori, lapphalya, RouteType.WOOD, feoLap);

        int[] feoAlb = new int[] { 376, 391, 259, 274 };
        ClientRoute feodoriAlbaran = new ClientRoute(feodori, albaran, RouteType.WOOD, feoAlb);
                
        // ClientRoute esselenWylhien = new ClientRoute(esselen, wylhien);
        // ClientRoute esselenYttar = new ClientRoute(esselen, yttar);
        // ClientRoute esselenParundia = new ClientRoute(esselen, parundia);
        // ClientRoute WylhienJaccaranda = new ClientRoute(wylhien, jaccaranda);
        // ClientRoute WyhlienParundia = new ClientRoute(wylhien, parundia);
        // ClientRoute yttarParundia = new ClientRoute(yttar, parundia);
        // ClientRoute parundiaAlbaran = new ClientRoute(parundia, albaran);
        // ClientRoute jaccarandaThrotmanni = new ClientRoute(jaccaranda, thortmanni);
        // ClientRoute jaccarandaTichih = new ClientRoute(jaccaranda, tichih);
        // ClientRoute throtmanniAlbaran = new ClientRoute(thortmanni, albaran);
        // ClientRoute throtmanniRivinia = new ClientRoute(thortmanni, rivinia);
        // ClientRoute throtmanniTichih = new ClientRoute(thortmanni, tichih);
        // ClientRoute throtmanniFeodori = new ClientRoute(thortmanni, feodori);
        // ClientRoute kihromahDagamura = new ClientRoute(kihromah, dagamura);
        // ClientRoute albaranDagamura = new ClientRoute(albaran, dagamura);
        // ClientRoute dagamuraFeodori = new ClientRoute(dagamura, feodori);
        // ClientRoute yttarGrangor = new ClientRoute(yttar, grangor);
        // ClientRoute grangorMahdavikia = new ClientRoute(grangor, mahdavikia);
        // ClientRoute mahdavikiaIxara = new ClientRoute(mahdavikia, ixara);
        // ClientRoute dagamuraLapphalya = new ClientRoute(dagamura, lapphalya);
        // ClientRoute ixaraLapphalya = new ClientRoute(ixara, lapphalya);
        // ClientRoute ixaraDagamura = new ClientRoute(ixara, dagamura);
        // ClientRoute ixaraVirst = new ClientRoute(ixara, virst);
        // ClientRoute virstLapphalya = new ClientRoute(virst, lapphalya);
        // ClientRoute virstStrykhaven = new ClientRoute(virst, strykhaven);
        // ClientRoute lapphalyaElvenhold = new ClientRoute(lapphalya, elvenhold);
        // ClientRoute beataStrykhaven = new ClientRoute(beata, strykhaven);
        // ClientRoute beataElvenhold = new ClientRoute(beata, elvenhold);
        // ClientRoute elvenholdStrykhaven = new ClientRoute(elvenhold, strykhaven);
        // ClientRoute elvenholdRivinia = new ClientRoute(elvenhold, rivinia);
        // ClientRoute riviniaTichih = new ClientRoute(rivinia, tichih);
        // ClientRoute tichihErgeren = new ClientRoute(tichih, ergeren);

        routes.add(virstLapphalya);
        routes.add(virstStrykhaven);
        routes.add(esselenParundia);
        routes.add(esselenYttar);
        routes.add(esselenWylhien);
        routes.add(WylhienParundia);
        routes.add(WylhienJaccaranda);
        routes.add(WylhienAlbaran);
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
        routes.add(virstElvenhold);
        routes.add(feodoriAlbaran);
        routes.add(feodoriLapphalya);
        routes.add(lapphalyaRivinia);
        routes.add(feodoriRivinia);
        routes.add(elvenholdErgeren);
        routes.add(beataElvenhold2);
        routes.add(yttarGrangor2);
        routes.add(esselenWylhien2);
        routes.add(ixaraVirst2);
        routes.add(virstStrykhaven2);
        routes.add(mahdavikiaIxara2);
        routes.add(grangorMahdavikia2);
        routes.add(parundiaGrangor);

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
            ClientTown elvenhold = Game.getTownByName("Elvenhold");
            elvenhold.addPlayer(player);
            player.setTown(elvenhold);
        } else {
            throw new IndexOutOfBoundsException("The max number of players has already been reached.");
        }
    }

    public void setRound(int r){
        this.currentRound = r;
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

    public static ArrayList<ClientTown> getTowns() {
        return towns;
    }

    // GETTER: gets town in towns by name
    public static ClientTown getTownByName(String name) {
        for (ClientTown t : towns) {
            if (t.getTownName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    public static boolean notClickingOnATown(int x, int y) {
        for (ClientTown t : towns) {

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

    public int getCurrentRound() { 
        return this.currentRound;
    }

    public static ArrayList<ClientRoute> getAllRoutes() { 
        return routes;
    }
}
