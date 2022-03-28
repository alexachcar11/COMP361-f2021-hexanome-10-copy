package serversrc;

import org.json.simple.parser.JSONParser;

import unirest.shaded.com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveGameManager {

    private static final SaveGameManager INSTANCE = new SaveGameManager();
    private static final Gson GSON = new Gson();

    private SaveGameManager() {

    }

    public static SaveGameManager instance() {
        return INSTANCE;
    }

    public boolean saveGame(ServerGame game) {
        File saveGameFile = new File("saved-games/" + game.getGameID() + ".json");
        String jsonGame = GSON.toJson(game);
        boolean status = false;
        try {
            status = saveGameFile.createNewFile();
            FileWriter writer = new FileWriter(saveGameFile);
            writer.write(jsonGame);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }
}
