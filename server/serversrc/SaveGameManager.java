package serversrc;

import unirest.shaded.com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;

public class SaveGameManager {

    private static final SaveGameManager INSTANCE = new SaveGameManager();
    private static final Gson GSON = new Gson();

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
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}
