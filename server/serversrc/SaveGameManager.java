package serversrc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class SaveGameManager {

    private static final SaveGameManager INSTANCE = new SaveGameManager();
    private static final Registrator REGISTRATOR = Registrator.instance();

    private SaveGameManager() {

    }

    public static SaveGameManager instance() {
        return INSTANCE;
    }

    public boolean saveGame(ServerGame game) {
        File saveGameFile = new File("saved-games/" + game.getGameID());
        FileOutputStream fileStream;
        try {
            fileStream = new FileOutputStream(saveGameFile);
            ObjectOutputStream output = new ObjectOutputStream(fileStream);
            output.writeObject(game);
            output.close();
            fileStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ServerGame loadGame(String gameID) {
        ServerGame loadedGame = null;
        try {
            String fileName = "saved-games/" + gameID;
            File saveFile = new File(fileName);
            FileInputStream file = new FileInputStream(saveFile);
            ObjectInputStream in = new ObjectInputStream(file);

            // deserialize object
            loadedGame = (ServerGame) in.readObject();

            in.close();
            file.close();
            saveFile.delete();

            return loadedGame;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // returns null if something goes wrong
        return null;
    }

    public List<ServerGame> getSavedGames() {
        return null;
    }
}
