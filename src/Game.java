/*
Instance of Game represent a single elfen game.

min 2 players
max 6 players
 */

import java.util.ArrayList;

public class Game {

   private ArrayList<Player> players;
   private int numberOfPlayers;

    /**
     * CONSTRUCTOR : creates an instance of Game object
     */
    public Game(int numberOfPlayers) {
        this.players = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayers;
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

}
