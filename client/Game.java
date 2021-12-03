/*
Instance of Game represent a single elfen game.

min 2 players
max 6 players
 */

import java.util.ArrayList;

public class Game {

   private ArrayList<Player> players;
   private int numberOfPlayers;
   public ArrayList<Town> towns; 
   public ArrayList<Route> routes;

    /**
     * CONSTRUCTOR : creates an instance of Game object
     */
    public Game(int numberOfPlayers) {
        this.players = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayers;
        towns.add(new Town("Esselen", 38, 103, 99, 152));
        towns.add(new Town("Yttar", 35, 98, 222, 274));
        towns.add(new Town("Wylhien", 187, 234, 30, 75));
        towns.add(new Town("Parundia", 172, 241, 172, 227));
        towns.add(new Town("Jaccaranda", 312, 381, 61, 119));
        towns.add(new Town("AlBaran", 280, 343, 227, 283));
        towns.add(new Town("Throtmanni", 451, 518, 129, 188));
        towns.add(new Town("Rivinia", 555, 621, 205, 256));
        towns.add(new Town("Tichih", 604, 662, 79, 135));
        towns.add(new Town("ErgEren", 716, 776, 210, 259));
        towns.add(new Town("Grangor", 49, 112, 366, 411));
        towns.add(new Town("MahDavikia", 57, 136, 482, 533));
        towns.add(new Town("Kihromah", 164, 223, 314, 367));
        towns.add(new Town("Ixara", 257, 322, 489, 534));
        towns.add(new Town("DagAmura", 281, 339, 345, 394));
        towns.add(new Town("Lapphalya", 415, 482, 383, 437));
        towns.add(new Town("Feodori", 411, 472, 259, 317));
        towns.add(new Town("Virst", 478, 536, 491, 541));
        towns.add(new Town("Elvenhold", 577, 666, 291, 370));
        towns.add(new Town("Beata", 724, 779, 407, 456));
        towns.add(new Town("Strkhaven", 616, 679, 463, 502));


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

}
