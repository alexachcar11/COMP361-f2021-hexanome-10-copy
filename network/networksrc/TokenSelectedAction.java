package networksrc;

import java.util.HashMap;
import java.util.List;

import clientsrc.ClientMain;
import serversrc.GameLobby;
import serversrc.Player;
import serversrc.ServerGame;
import serversrc.Token;

public class TokenSelectedAction implements Action {

    private String serverGameID;
    private String tokenString;
    private String playerName;

    public TokenSelectedAction(String currentGameID, String tName) {
        this.serverGameID = currentGameID;
        this.tokenString = tName;
        this.playerName = ClientMain.currentPlayer.getName();
    }

    @Override
    public boolean isValid() {
        ServerGame game = GameLobby.getGameLobby(this.serverGameID).getServerGame();
        Token tokenToAdd = Token.getTokenByString(this.tokenString);
        return game.faceUpTokenPile.contains(tokenToAdd)
                || game.faceDownTokenStack.contains(tokenToAdd);
    }

    @Override
    public void execute() {
        serversrc.Player player = Player.getPlayerByName(this.playerName);
        ServerGame game = GameLobby.getGameLobby(this.serverGameID).getServerGame();
        Token tokenToAdd = Token.getTokenByString(this.tokenString);
        player.addToken(tokenToAdd);
        if (game.faceUpTokenPile.remove(tokenToAdd)) {
            game.faceUpTokenPile.add(game.faceDownTokenStack.pop());
        } else {
            game.faceDownTokenStack.remove(tokenToAdd);
        }
        HashMap<String, List<String>> playerTokens = game.getTokenInventoryMap();
        ACKManager.getInstance().sentToAllPlayersInGame(new DealTokenACK(playerTokens), game);
    }

}
