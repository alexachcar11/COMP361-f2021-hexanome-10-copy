public class CreateNewGame extends Action {

    int numberOfPlayers;
    int numberOfRounds;
    Mode mode;
    boolean witchEnabled;
    boolean destinationTownEnabled;


    enum Mode {
        // TODO: what are the real modes? not relevant for M5
        ELFENGOLD, ELFENLAND
    }

    @Override
    public boolean isValid() {
        // TODO: eventually fill this
        return true;
    }

    @Override
    public void execute() {
        // TODO: send info to lobby service to create game
        // TOOD: get confirmation from lobbyservice
    }

}
