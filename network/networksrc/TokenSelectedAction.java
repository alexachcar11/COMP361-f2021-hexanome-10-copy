package networksrc;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import clientsrc.ClientMain;
import serversrc.GameLobby;
import serversrc.Player;
import serversrc.ServerGame;
import serversrc.Token;

public class TokenSelectedAction implements Action {

    private String serverGameID;
    private String tokenString;
    private String playerName;

    public TokenSelectedAction(String tName) {
        this.serverGameID = ClientMain.currentSession.getSessionID();
        this.tokenString = tName;
        this.playerName = ClientMain.currentPlayer.getName();
    }

    @Override
    public boolean isValid() {
        ServerGame game = GameLobby.getGameLobby(this.serverGameID).getServerGame();
        if (game.getCurrentPhase() != 3){
            return false;
        }
        if (this.tokenString.equals("random"))
        {
            return !game.faceDownTokenStack.isEmpty();
        }
        Token tokenToAdd = Token.getTokenByName(this.tokenString);
        return game.faceUpTokenPile.contains(tokenToAdd)
                || game.faceDownTokenStack.contains(tokenToAdd);
    }

    @Override
    public void execute() {
        serversrc.Player player = Player.getPlayerByName(this.playerName);
        ServerGame game = GameLobby.getGameLobby(this.serverGameID).getServerGame();
        Token tokenToAdd;
        if (this.tokenString.equals("random")) {
            tokenToAdd = game.faceDownTokenStack.pop();
        } else {
            tokenToAdd = Token.getTokenByName(this.tokenString);
            game.faceUpTokenPile.remove(tokenToAdd);
            game.faceUpTokenPile.add(game.faceDownTokenStack.pop());
        }
        player.addToken(tokenToAdd);
        HashMap<String, List<String>> playerTokens = game.getTokenInventoryMap();
        ActionManager.getInstance().sentToAllPlayersInGame(new DealTokenACK(playerTokens), game);
        String playerName = game.getCurrentPlayer().getName();
        System.out.println(playerName + " just picked a token.");
        game.nextPlayer();
        System.out.println("Now it's " + game.getCurrentPlayer().getName() + "'s turn.");
        if (game.getCurrentPhase() == 3){
            System.out.println("Calling phaseThree, current phase: " + game.getCurrentPhase());
            game.phaseThree();
        }
        
    }

}
