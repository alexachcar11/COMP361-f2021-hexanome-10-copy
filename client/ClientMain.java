
// minueto
import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.MinuetoFileException;
import org.minueto.handlers.MinuetoKeyboard;
import org.minueto.handlers.MinuetoKeyboardHandler;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.*;
import org.minueto.window.MinuetoFrame;
import org.minueto.window.MinuetoWindow;

// other
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

// json
import org.json.simple.parser.ParseException;
import java.io.IOException;

public class ClientMain {

    private static boolean userNameSel = false;
    private static boolean passWordSel = false;
    private static String userString = "";
    private static String passString = "";

    public static void main(String[] args) {

        /* in the Boot class
        File bootDir = new File("images/böppels-and-boots/"); // dir containing boot image files
        
        List<String> bootFileNames = new ArrayList<>();
        // add file names of boot images to the bootFiles list
        for (File file : bootDir.listFiles()) {
            if (file.getName().startsWith("boot-"))
                bootFileNames.add("images/böppels-and-boots/" + file.getName());
        }
         */

        // make images
        MinuetoImage elfenlandImage;
        MinuetoImage elfengoldImage;
        // TODO: fix this List<MinuetoImage> bootImages = getBootImages(bootFileNames);
        MinuetoImage playScreenImage;
        MinuetoImage loginScreenImage;
        MinuetoImage whiteBoxImage;
        MinuetoRectangle lobbyBackground;

        // TODO: place this somewhere else configImages(bootImages);


        try {
            elfengoldImage = new MinuetoImageFile("images/elfengold.png");
            elfenlandImage = new MinuetoImageFile("images/elfenland.png");
            playScreenImage = new MinuetoImageFile("images/play.png");
            loginScreenImage = new MinuetoImageFile("images/login.png");
            whiteBoxImage = new MinuetoRectangle(470, 50, MinuetoColor.WHITE, true);
            lobbyBackground = new MinuetoRectangle(1024, 768, MinuetoColor.BLUE, true);
        } catch (MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }

        // Play Music
        // playSound("music/flute.mid"); TODO: add a mute/unmute button - Dijian and Alex

        // create players TODO: remove this
        List<Player> players = new ArrayList<>();
        // Player p1 = new Player(null, Color.YELLOW);
        // Player p2 = new Player(null, Color.BLACK);
        // players.add(p1);
        // players.add(p2);

        // create window that will contain our game - stays in Main
        MinuetoWindow window = new MinuetoFrame(1024, 768, true);
        GUI gui = new GUI(window, GUI.Screen.MENU);
        window.setMaxFrameRate(60);

        // make window visible
        gui.window.setVisible(true);

        // stack for a word TODO: remove this (Owen)
        Stack<String> writtenWord = new Stack<>();

        // create entry screen mouse handler TODO: where does this go (Lilia / Owen)
        MinuetoEventQueue entryScreenQueue = new MinuetoEventQueue();
        gui.window.registerMouseHandler(new MinuetoMouseHandler() {
            @Override
            public void handleMousePress(int x, int y, int button) {
                // click on Play
                if (x <= 665 && x >= 360 && y >= 345 && y <= 445) {

                    // TODO: Add create lobby instance
                    // create lobby instance if it's not there already

                    gui.currentBackground = GUI.Screen.LOGIN;
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

        // create login screen keyboard and mouse handler
        MinuetoEventQueue loginScreenQueue = new MinuetoEventQueue();

        gui.window.registerKeyboardHandler(new MinuetoKeyboardHandler() {
            private boolean shift = false;
            private MinuetoFont fontArial20 = new MinuetoFont("Arial", 19, false, false);

            @Override
            public void handleKeyPress(int i) {
                // press on enter key takes you to the next screen
                if (i == MinuetoKeyboard.KEY_ENTER) {
                    try {
                        ServerMain.getAvailableGames(); // retrieves all game services in the API
                        ServerMain.getAvailableSessions(); // retrieves all game sessions in the API
                        // TODO: ask the TA if our understanding of game service vs. session is ok
                        // Game service: a saved game that is not loaded up yet (i.e. none of its players are online)
                        // Game session: a game that is loaded and at least one player is ready to play

                        // TODO: place buildDisplay() here
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();

                        // if we in this catch block then there are no available game services or sessions
                        // TODO: for additional functionality, we can put a message that says "there are no game lobbies, please create one or use the refresh button"
                    }


                    gui.currentBackground = GUI.Screen.LOBBY;

                } else if (i == MinuetoKeyboard.KEY_SHIFT) {
                    shift = true;
                } else if (i == MinuetoKeyboard.KEY_DELETE) {
                    if (userNameSel && userString.length() > 0) {
                        userString = userString.substring(0, userString.length() - 1);
                    } else if (passWordSel && passString.length() > 0) {
                        passString = passString.substring(0, passString.length() - 1);
                    } else {
                        return;
                    }
                } else if (shift || i < 65 || i > 90) {
                    writtenWord.push("" + (char) i); // uppercase
                } else {
                    writtenWord.push("" + (char) (i + 32)); // lowercase
                }
                if (userNameSel) {
                    // cover the last entry
                    loginScreenImage.draw(whiteBoxImage, 160, 350);

                    // type inside the textbox for username
                    while (!writtenWord.empty()) {
                        userString = userString + writtenWord.pop();
                    }

                    MinuetoImage username = new MinuetoText(userString, fontArial20, MinuetoColor.BLACK);
                    loginScreenImage.draw(username, 200, 360);
                    writtenWord.clear();
                } else if (passWordSel) {
                    // cover the last entry
                    loginScreenImage.draw(whiteBoxImage, 160, 440);

                    // type inside the textbox for password
                    while (!writtenWord.empty()) {
                        passString = passString + writtenWord.pop();
                    }
                    MinuetoImage password = new MinuetoText(passString, fontArial20, MinuetoColor.BLACK);
                    loginScreenImage.draw(password, 200, 450);
                    writtenWord.clear();
                }
            }

            @Override
            public void handleKeyRelease(int i) {
                if (i == MinuetoKeyboard.KEY_SHIFT) {
                    shift = false;
                }
            }

            @Override
            public void handleKeyType(char c) {
                // do nothing
            }
        }, loginScreenQueue);

        gui.window.registerMouseHandler(new MinuetoMouseHandler() {

            @Override
            public void handleMousePress(int x, int y, int button) {

                MinuetoFont fontArial20 = new MinuetoFont("Arial", 19, false, false);

                // CLICK INSIDE THE USERNAME BOX
                if (x <= 630 && x >= 160 && y >= 350 && y <= 400) {
                    passWordSel = false;
                    userNameSel = true;
                }
                // CLICK INSIDE THE PASSWORD BOX
                else if (x <= 630 && x >= 160 && y >= 440 && y <= 495) {
                    userNameSel = false;
                    passWordSel = true;
                } else {
                    userNameSel = false;
                    passWordSel = false;
                }

                // CLICK ON THE LOGIN BOX AREA
                if (x <= 235 && x >= 165 && y >= 525 && y <= 550) {

                    // TODO: Check if the username exists in the list of usernames
                    // if it exists -> check if password matches -
                    // if password matches --> send to availableGamesScreen
                    // else --> send red text to reflect outcome
                    // if it doesn't exist -> send red text to reflect outcome
                    if (passString.length() == 0 || userString.length() == 0) {

                        if (passString.length() == 0) {
                            // no password
                            String passFail = "Please enter a password";
                            MinuetoImage passwordFailed = new MinuetoText(passFail, fontArial20, MinuetoColor.RED);
                            loginScreenImage.draw(passwordFailed, 200, 450);
                        }
                        if (userString.length() == 0) {
                            // no username
                            String usernameFail = "Please enter a username";
                            MinuetoImage usernameFailed = new MinuetoText(usernameFail, fontArial20, MinuetoColor.RED);
                            loginScreenImage.draw(usernameFailed, 200, 360);
                        }
                    }
                    // change screen after login
                    else {
                        gui.currentBackground = GUI.Screen.ELFENGOLD;
                    }
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
        }, loginScreenQueue);

        // create move boot mouse handler TODO: Dijian
        MinuetoEventQueue moveBootQueue = new MinuetoEventQueue();
        gui.window.registerMouseHandler(new MinuetoMouseHandler() {
            int ind = 0; // index of players

            @Override
            public void handleMousePress(int x, int y, int button) {
                System.out.println("This is x: " + x + ". This is y: " + y);
                // for left click : move boot
                /*
                 * if (button == 1) {
                 * players.get(ind).moveBoot(x, y);
                 * }
                 * // for right click : change next player
                 * else if (button == 3) {
                 * ind++;
                 * if (ind == players.size()) { ind = 0; } // reset index if we reached last
                 * player
                 * }
                 */

                /*
                 * for (int i = 0; i < players.size(); i++) {
                 * // check for player's turn and if button is left click
                 * if (players.get(i).isTurn && button == 1) {
                 * players.get(i).moveBoot(x, y);
                 * break;
                 * }
                 * // if press right mouse button, change to next player
                 * if (button == 3) {
                 * // set isTurn to false for current player
                 * players.get(i).setTurn(false);
                 * 
                 * // if we reached last player, go back to first player
                 * if (i == players.size()-1) {
                 * players.get(0).setTurn(true);
                 * }
                 * else {
                 * // change isTurn to true for next player
                 * players.get(i + 1).setTurn(true);
                 * }
                 * break;
                 * }
                 * }
                 */
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
            if (gui.currentBackground == GUI.Screen.MENU) {
                gui.window.draw(playScreenImage, 0, 0);
                while (entryScreenQueue.hasNext()) {
                    entryScreenQueue.handle();
                }
            } else if (gui.currentBackground == GUI.Screen.LOGIN) {
                gui.window.draw(loginScreenImage, 0, 0);
                while (loginScreenQueue.hasNext()) {
                    loginScreenQueue.handle();
                }

            } else if (gui.currentBackground == GUI.Screen.LOBBY) {
                // draw a blue background
                gui.window.draw(lobbyBackground, 0, 0);

                // display each LobbyServiceGame / LobbyServiceGameSession

                // TODO: here we only use the draw() function from minueto. Avoid using
                // buildDisplay() here because it will create objects at every frame.

                // TODO: place buildDisplay() in the loginScreenQueue so it is only done once
                // (line 137)

                // TODO: eventually we might want to refresh the list of available games at
                // intervals of time. Then buildDisplay() would be somewhere else?

            } else if (gui.currentBackground == GUI.Screen.ELFENLAND) {
                gui.window.draw(elfenlandImage, 0, 0);
            } else if (gui.currentBackground == GUI.Screen.ELFENGOLD) {
                gui.window.draw(elfengoldImage, 0, 0);
            }

            if (gui.currentBackground == GUI.Screen.ELFENLAND
                    || gui.currentBackground == GUI.Screen.ELFENGOLD) {
                // draw boots
                /*
                 * for (Player player : players) {
                 * gui.window.draw(player.getIcon(), player.getxPos(),
                 * player.getyPos());
                 * }
                 */
                // players.get(0).isTurn = true; // only player 1 can move
                while (moveBootQueue.hasNext()) {
                    moveBootQueue.handle();
                }
            }

            window.render();
            Thread.yield();
        }
    }

    /* TODO: Owen
     * @pre: pNames is a list of filenames of the boot images
     *
     * @return: List of images corresponding to the filenames
     */
    /* private static List<MinuetoImage> getBootImages(List<String> pNames) {
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

    /* TODO: does this go in Image - Owen/Dijian?
     * @pre: pImages is a list of MinuetoImages which are boots
     * 
     * @post: contents of pImages are changed to be centered at starting town on
     * game window, and rotated and sized properly
     */
    /*
    private static void configImages(List<MinuetoImage> pImages) {
        for (int i = 0; i < pImages.size(); i++) {
            pImages.set(i, pImages.get(i).rotate(-90));
            pImages.set(i, pImages.get(i).scale(.125, .125));
        }
    } */

    /** TODO: alex/dijian put a mute/unmute button
     * Play Music
     * 
     * @param soundFile sound file to play
     */
    static void playSound(String soundFile) {
        File f = new File("./" + soundFile);
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            // TODO: was this catch block intended to be empty?
        }
    }


}
