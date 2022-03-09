package networksrc;

import clientsrc.ClientMain;

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

        // modify game objects based on the game state received
<<<<<<< Updated upstream
=======
        // TODO: iterate thru playerNames, index for loop --> use to iterate thru playerCards, give Players their cards :)
        for (int i = 0; i < playerNames.size(); i++){

            String playerID = playerNames.get(i);
            ArrayList<String> aPlayersCards = playerCards.get(i);

            ClientMain.receivePhaseOne(playerID, aPlayersCards);


        }


>>>>>>> Stashed changes
        // display the new board

        System.out.println("LaunchGameACK received");
    }
    
}
