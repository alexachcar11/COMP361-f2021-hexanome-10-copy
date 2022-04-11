package testsrc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import serversrc.Mode;
import serversrc.SaveGameManager;
import serversrc.ServerGame;

public class SaveGameTest {

    @Test
    public void testSaveGame() {
        ServerGame game = new ServerGame(4, 3, false, false, Mode.ELFENLAND, serversrc.TownGoldOption.NO, "testgame");
        SaveGameManager MANAGER = SaveGameManager.instance();
        boolean save = MANAGER.saveGame(game);
        assertEquals(save, true);
    }
}
