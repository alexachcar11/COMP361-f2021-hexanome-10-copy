package serversrc;

import org.json.simple.parser.JSONParser;

import unirest.shaded.com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        File saveGameFile = new File("saved-games/" + game.getGameID() + ".json");
        HashMap<String, Object> gameData = new HashMap<>();
        Field[] gameFields = ServerGame.class.getDeclaredFields();
        for (Field field : gameFields) {
            try {
                gameData.put(field.toString(), field.get(game));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // String jsonGame = game.getJSON();
        String jsonGame = GSON.toJson(gameData);
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
