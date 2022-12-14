package networksrc;

import clientsrc.ClientMain;
import clientsrc.Color;
import clientsrc.ClientPlayer;
import clientsrc.User;
import clientsrc.Game;

public class LaunchGameACK implements Action {

    // TODO: store the game state through a constructor
    public LaunchGameACK() {
    }

    @Override
    public boolean isValid() {
        // TODO: add validation
        return true;
    }

    @Override
    public void execute() {
        // set the session to launched
        ClientMain.currentSession.launch();
        System.out.println("executing launchgameack");

        // create players and assign their location to elvenhold, the base town
        for (User u : ClientMain.currentSession.getUsers()) {
            Color pColor = u.getColor();
            Game currentGame = ClientMain.currentSession.getGame();
            ClientPlayer newPlayer = new ClientPlayer(pColor, u, currentGame);
            currentGame.addPlayer(newPlayer);
            if (u.getName().equals(ClientMain.currentUser.getName())) {
                ClientMain.currentPlayer = newPlayer;
            }
        }

        // modify game objects based on the game state received
        ClientMain.players = ClientMain.currentGame.getPlayers();
        ClientMain.players.remove(ClientMain.currentPlayer);

        // display board screen
        ClientMain.displayOriginalBoard();


        System.out.println("LaunchGameACK received");
    }

}
