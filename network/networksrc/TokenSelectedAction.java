package networksrc;

import clientsrc.ClientMain;
import serversrc.GameLobby;
import serversrc.Player;
import serversrc.ServerGame;
import serversrc.Token;

public class TokenSelectedAction implements Action {

    private ServerGame game;
    private Token token;
    private String playerName;

    public TokenSelectedAction(String currentGameID, String tName) {
        this.game = GameLobby.getGameLobby(currentGameID).getServerGame();
        serversrc.CardType toRemove = null;
        for (serversrc.CardType cT : serversrc.CardType.values()) {
            if (cT.toString().equals(tName)) {
                toRemove = cT;
                break;
            }
        }
        if (toRemove == null) {
            throw new IllegalArgumentException("Invalid token name.");
        }
        this.token = new Token(toRemove);
        this.playerName = ClientMain.currentPlayer.getName();
    }

    @Override
    public boolean isValid() {
        return game.faceUpTokenPile.contains(token)
                || game.faceDownTokenStack.contains(this.token);
    }

    @Override
    public void execute() {
        serversrc.Player player = Player.getPlayerByName(this.playerName);
        player.addToken(this.token);
        if (this.game.faceUpTokenPile.remove(this.token)) {
            game.faceUpTokenPile.add(game.faceDownTokenStack.pop());
        } else {
            game.faceDownTokenStack.remove(this.token);
        }
        final String tokenName = this.token.getTokenType().toString();
        final String name = this.playerName;
        ACKManager.getInstance().sendToSender(new Action() {

            @Override
            public boolean isValid() {
                return true;
            }

            @Override
            public void execute() {
                System.out.println(
                        "Removed a " + tokenName + " travel counter from the pile and added it to " + name
                                + "'s tokens.");
                // update gui to reflect tokens in hand
            }

        }, playerName);
    }

}
