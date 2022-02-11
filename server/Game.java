import java.util.ArrayList;

/**
 * Represents one playable game.
 */

public class Game {

    // FIELDS
    private int currentPhase;
    private int currentRound;
    private int gameRoundsLimit;
    private boolean destinationTownEnabled;
    private boolean witchEnabled;
    private Mode mode;
    private ArrayList<Player> players;


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
    public int getCurrentPhase() {
        return currentPhase;
    }

    public int getCurrentRound() {
        return currentRound;
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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setCurrentPhase(int currentPhase) {
        this.currentPhase = currentPhase;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public void setGameRoundsLimit(int gameRoundsLimit) {
        this.gameRoundsLimit = gameRoundsLimit;
    }

    public void setDestinationTownEnabled(boolean destinationTownEnabled) {
        this.destinationTownEnabled = destinationTownEnabled;
    }

    public void setWitchEnabled(boolean witchEnabled) {
        this.witchEnabled = witchEnabled;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
