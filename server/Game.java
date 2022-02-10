import java.util.ArrayList;

/**
 * Represents one playable game.
 */

public class Game {

    // FIELDS
    int currentPhase;
    int currentRound;
    int gameRoundsLimit;
    boolean destinationTownEnabled;
    boolean witchEnabled;
    Mode mode;
    ArrayList<Player> players;


    // CONSTRUCTOR
    public Game(int gameRoundsLimit, boolean destinationTownEnabled, boolean witchEnabled, Mode mode) {
        this.gameRoundsLimit = gameRoundsLimit;
        this.destinationTownEnabled = destinationTownEnabled;
        this.witchEnabled = witchEnabled;
        this.mode = mode;
        this.currentPhase = 1;
        this.currentRound = 1;
        this.players = new ArrayList<>();
    }


    // METHODS

}
