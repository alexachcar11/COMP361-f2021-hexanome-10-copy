package serversrc;

public class TestSaveGame {

    public static void main(String args[]) {
        SaveGameManager manager = SaveGameManager.instance();
        ServerGame testGame = new ServerGame(5, 3, false, false,
                Mode.ELFENLAND, TownGoldOption.NO, "owensgame");
        System.out.println(manager.saveGame(testGame));
        // ServerGame loadGame = manager.loadGame("owensgame");
        // System.out.println(loadGame.getGameID());
    }

}
