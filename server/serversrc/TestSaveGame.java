package serversrc;

public class TestSaveGame {

    public static void main(String args[]) {
        SaveGameManager manager = SaveGameManager.instance();
        ServerGame testGame = new ServerGame(5, 3, false, false,
                Mode.ELFENLAND, TownGoldOption.NO, "owensgame");
        manager.saveGame(testGame);
    }

}
