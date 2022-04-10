package serversrc;

import unirest.shaded.com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
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
    public ServerGame loadGame(String filename){
        ServerGame loadedGame = null;
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // deserialize object
            loadedGame = (ServerGame)in.readObject();

            in.close();
            file.close();

            return loadedGame;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        // returns null if something goes wrong
        return null;
    }
}
