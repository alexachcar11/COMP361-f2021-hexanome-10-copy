package networksrc;

import clientsrc.TownGoldOption;
import clientsrc.Mode;

public class GetGameInfoACK implements Action {

    private int numberOfPlayers;
    private int gameRoundsLimit;
    private boolean destinationTownEnabled;
    private boolean witchEnabled;
    private String mode;
    private String townGoldOption;

    public GetGameInfoACK(int numberOfPlayers, int gameRoundsLimit, boolean destinationTownEnabled, boolean witchEnabled, String mode, String townGoldOption) {
        this.numberOfPlayers = numberOfPlayers;
        this.gameRoundsLimit = gameRoundsLimit;
        this.destinationTownEnabled = destinationTownEnabled;
        this.witchEnabled = witchEnabled;
        this.mode = mode;
        this.townGoldOption = townGoldOption;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        // do nothing
    }
    
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

    public Mode getMode() {
        if (mode.equals("elfenland")) {
            return Mode.ELFENLAND; 
        } else if (mode.equals("elfengold")) {
            return Mode.ELFENGOLD;
        }
        return null;
    }

    public TownGoldOption getTownGoldOption() {
        if (townGoldOption.equals("no")) {
            return TownGoldOption.NO;
        } else if (townGoldOption.equals("yes-default")) {
            return TownGoldOption.YESDEFAULT;
        } else if (townGoldOption.equals("yes-random")) {
            return TownGoldOption.YESRANDOM;
        }
        return null;
    }
}
