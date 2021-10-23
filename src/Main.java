import org.minueto.*;
import org.minueto.handlers.*;
import org.minueto.image.*;
import org.minueto.window.*;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        // dir containing boot image files
        File bootDir = new File("böppels-and-boots/");

        List<String> bootFileNames = new ArrayList<>();
        // add file names of boot images to the bootFiles list
        for (File file : bootDir.listFiles()) {
            if (file.getName().substring(0, 5).equals("boot-"))
                bootFileNames.add("böppels-and-boots/" + file.getName());
        }

        // make images
        MinuetoImage elfenlandImage;
        MinuetoImage elfengoldImage;
        List<MinuetoImage> bootImages = getBootImages(bootFileNames);
        MinuetoImage playScreenImage;
        MinuetoImage loginScreenImage;
        configImages(bootImages);
        try {
            elfengoldImage = new MinuetoImageFile("elfengold.png");
            elfenlandImage = new MinuetoImageFile("elfenland.png");
            playScreenImage = new MinuetoImageFile("play.png");
            loginScreenImage = new MinuetoImageFile("login.png");
        } catch (MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }

        // create player
        List<Player> players = new ArrayList<>();
        Player p1 = new Player(bootImages.get(1), 600 + 20 * (0 % 4), 300 + 20 * (0 / 4));
        Player p2 = new Player(bootImages.get(0), 600 + 20 * (1 % 4), 300 + 20 * (1 / 4));
        players.add(p1);
        players.add(p2);

        // create window that will contain our game
        MinuetoWindow window = new MinuetoFrame(1024, 768, true);
        GameWindow gameWindow = new GameWindow(window, GameWindow.Screen.ENTRY);
        // make window visible
        gameWindow.window.setVisible(true);

        // create entry screen mouse handler
        MinuetoEventQueue entryScreenQueue = new MinuetoEventQueue();
        gameWindow.window.registerMouseHandler(new MinuetoMouseHandler() {
            @Override
            public void handleMousePress(int x, int y, int button) {
                // click on Play
                if (x <= 665 && x >= 360 && y >= 345 && y <= 445) {
                    gameWindow.currentlyShowing = GameWindow.Screen.LOGIN;
                }

                // click on Quit
                if (x <= 665 && x >= 360 && y >= 530 && y <= 640) {
                    System.exit(0);
                }
            }

            @Override
            public void handleMouseRelease(int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void handleMouseMove(int i, int i1) {
                // Do nothing
            }
        }, entryScreenQueue);

        // create move boot mouse handler
        MinuetoEventQueue moveBootQueue = new MinuetoEventQueue();
        gameWindow.window.registerMouseHandler(new MinuetoMouseHandler() {
            @Override
            public void handleMousePress(int x, int y, int button) {
                System.out.println(x + ", " + y);
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).isTurn) {
                        players.get(i).moveBoot(x, y);
                    }
                }
            }

            @Override
            public void handleMouseRelease(int x, int y, int button) {
            }

            @Override
            public void handleMouseMove(int x, int y) {

            }
        }, moveBootQueue);

        // draw on the window
        while (true) {
            if (gameWindow.currentlyShowing == GameWindow.Screen.ENTRY) {
                gameWindow.window.draw(playScreenImage, 0, 0);
                while (entryScreenQueue.hasNext()) {
                    entryScreenQueue.handle();
                }
            } else if (gameWindow.currentlyShowing == GameWindow.Screen.LOGIN) {
                gameWindow.window.draw(loginScreenImage, 0, 0);

                // TODO: TO THE PERSON CODING LOGIN: in your input handler you should change gameWindow.currentlyShowing to ELFENLAND or ELFENGOLD
                // in order for the login page to stop being drawn and the board game to be start being drawn

            } else if (gameWindow.currentlyShowing == GameWindow.Screen.ELFENLAND) {
                gameWindow.window.draw(elfenlandImage, 0, 0);
            } else if (gameWindow.currentlyShowing == GameWindow.Screen.ELFENGOLD) {
                gameWindow.window.draw(elfengoldImage, 0, 0);
            }

            if (gameWindow.currentlyShowing == GameWindow.Screen.ELFENLAND || gameWindow.currentlyShowing == GameWindow.Screen.ELFENGOLD) {
                // draw boots
                for (int i = 0; i < players.size(); i++) {
                    gameWindow.window.draw(players.get(i).getIcon(), players.get(i).getxPos(), players.get(i).getyPos());
                }
                players.get(0).isTurn = true;   // only player 1 can move
                while (moveBootQueue.hasNext()) {
                    moveBootQueue.handle();
                }
            }

            window.render();
            Thread.yield();
        }
    }

    private static List<MinuetoImage> getBootImages(List<String> pNames) {
        List<MinuetoImage> toReturn = new ArrayList<>();
        for (String name : pNames) {
            try {
                toReturn.add(new MinuetoImageFile(name)); // name is null, gives exception
            } catch (MinuetoFileException e) {
                System.out.println("Could not load image file " + name);
                e.printStackTrace();
            }
        }
        return toReturn;
    }

    private static void configImages(List<MinuetoImage> pImages) {
        for (int i = 0; i < pImages.size(); i++) {
            pImages.set(i, pImages.get(i).rotate(-90));
            pImages.set(i, pImages.get(i).scale(.125, .125));
        }
    }

}
