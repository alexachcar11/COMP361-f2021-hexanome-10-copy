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

    /**
     * CONSTRUCTOR : creates an instance of Game object
     */
    public Game(int numberOfPlayers) {
        this.players = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayers;
        // towns.add(new Town("Esselen", 583, maxX, minY, 296));
        // towns.add(new Town("Yttar", minX, maxX, minY, maxY));
        // towns.add(new Town("Wylhien", minX, maxX, minY, maxY));
        // towns.add(new Town("Parundia", minX, maxX, minY, maxY));
        // towns.add(new Town("Jaccaranda", minX, maxX, minY, maxY));
        // towns.add(new Town("AlBaran", minX, maxX, minY, maxY));
        // towns.add(new Town("Throtmanni", minX, maxX, minY, maxY));
        // towns.add(new Town("Rivinia", minX, maxX, minY, maxY));
        // towns.add(new Town("Tichih", minX, maxX, minY, maxY));
        // towns.add(new Town("ErgEren", minX, maxX, minY, maxY));
        // towns.add(new Town("Parundia", minX, maxX, minY, maxY));
        // towns.add(new Town("Grangor", minX, maxX, minY, maxY));
        // towns.add(new Town("MahDavikia", minX, maxX, minY, maxY));
        // towns.add(new Town("Kihromah", minX, maxX, minY, maxY));
        // towns.add(new Town("Ixara", minX, maxX, minY, maxY));
        // towns.add(new Town("DagAmura", minX, maxX, minY, maxY));
        // towns.add(new Town("Lapphalya", minX, maxX, minY, maxY));
        // towns.add(new Town("Feodori", minX, maxX, minY, maxY));
        // towns.add(new Town("Virst", minX, maxX, minY, maxY));
        // towns.add(new Town("Elvenhold", minX, maxX, minY, maxY));
        // towns.add(new Town("Beata", minX, maxX, minY, maxY));
        // towns.add(new Town("Strkhaven", minX, maxX, minY, maxY));

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
