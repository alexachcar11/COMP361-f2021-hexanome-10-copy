package serversrc;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import networksrc.ActionManager;
import unirest.shaded.com.google.gson.Gson;

public class TestSaveGame {

    public static void main(String args[]) {
        SaveGameManager manager = SaveGameManager.instance();
        ServerGame testGame = new ServerGame(5, 3, false, false,
                Mode.ELFENLAND, TownGoldOption.NO, "owensgame");
        // Field[] fields = ServerGame.class.getDeclaredFields();
        // List<Field> fieldsList = Arrays.asList(fields);
        // fieldsList.forEach((field) -> {
        // if (field.getType().equals(Gson.class) ||
        // field.getType().equals(ActionManager.class))
        // return;
        // field.setAccessible(true);
        // try {
        // int modifiers = field.getModifiers();
        // System.out.println(
        // field.getName() + "..." + field.getType().toString() + " " +
        // Modifier.isStatic(modifiers));
        // } catch (IllegalArgumentException e) {
        // e.printStackTrace();
        // }
        // });
        // manager.saveGame(testGame);
        System.out.println(testGame.getJSON());
    }

}
