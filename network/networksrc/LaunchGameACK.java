package networksrc;

import clientsrc.ClientMain;
import clientsrc.Player;
import clientsrc.Game;

public class LaunchGameACK implements Action{

    // TODO: store the game state through a constructor

    @Override
    public boolean isValid() {
        // TODO: add validation
        return true;
    }

    @Override
    public void execute() {
        // set the session to launched
        ClientMain.currentSession.launch();

        // create players and assign their location to elvenhold, the base town
        for (User u: currentSession.getUsers()){ 
            Color pColor = u.getColor;
            Game currentGame = currentSession.getGame();
            Player newPlayer = new Player(pColor, u, currentGame);
            Town elvenhold = currentGame.getTownByName("Elvenhold");
            newPlayer.inTown = elvenhold;
            elvenhold.addPlayer(newPlayer);
        }

        // modify game objects based on the game state received
        // display the new board

        System.out.println("LaunchGameACK received");
    }
    
}
