
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
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Stack;

// json
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class ClientMain {

    // for login screen queue
    private static boolean userNameSel = false;
    private static boolean passWordSel = false;
    private static String userString = "";
    private static String passString = "";
    private static JSONObject token;

    // for mute button
    private static boolean soundOn = true;
    private static Clip loadedClip;
    private static long clipPos;
    private static boolean soundStarted = false;

    // for create game queue
    private static boolean nameSel = false;
    private static boolean numberPlayerSel = false;
    private static String nameString = "";
    private static String numberPlayerString = "";

    public static void main(String[] args) {

        /*
         * in the Boot class
         * File bootDir = new File("images/böppels-and-boots/"); // dir containing boot
         * image files
         * 
         * List<String> bootFileNames = new ArrayList<>();
         * // add file names of boot images to the bootFiles list
         * for (File file : bootDir.listFiles()) {
         * if (file.getName().startsWith("boot-"))
         * bootFileNames.add("images/böppels-and-boots/" + file.getName());
         * }
         */

        /*
         * create OAuth2 token
         * after this only need to refresh token
         */
        try {
            token = createAccessToken();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.err.println("Error: could not create access token.");
        }

        // make images
        MinuetoImage elfenlandImage;
        MinuetoImage elfengoldImage;
        // TODO: fix this List<MinuetoImage> bootImages = getBootImages(bootFileNames);
        MinuetoImage playScreenImage;
        MinuetoImage loginScreenImage;
        MinuetoImage whiteBoxImage;
        MinuetoImage lobbyBackground;
        MinuetoImage lobbyPrevBackground;
        MinuetoImage lobbyNextBackground;
        MinuetoImage lobbyPrevNextBackground;
        MinuetoImage createGameBackground;
        MinuetoRectangle nameTextField;
        MinuetoRectangle numberOfPlayersTextField;
        MinuetoImage soundOnButton;
        MinuetoImage soundOffButton;

        // TODO: place this somewhere else configImages(bootImages);

        try {
            elfengoldImage = new MinuetoImageFile("images/elfengold.png");
            elfenlandImage = new MinuetoImageFile("images/elfenland.png");
            playScreenImage = new MinuetoImageFile("images/play.png");
            loginScreenImage = new MinuetoImageFile("images/login.png");
            whiteBoxImage = new MinuetoRectangle(470, 50, MinuetoColor.WHITE, true);
            lobbyBackground = new MinuetoImageFile("images/open-lobbies.png");
            lobbyPrevBackground = new MinuetoImageFile("images/open-lobbies-prev.png");
            lobbyNextBackground = new MinuetoImageFile("images/open-lobbies-next.png");
            lobbyPrevNextBackground = new MinuetoImageFile("images/open-lobbies-prev-next.png");
            createGameBackground = new MinuetoImageFile("images/create-game.png");
            nameTextField = new MinuetoRectangle(520, 60, MinuetoColor.WHITE, true);
            numberOfPlayersTextField = new MinuetoRectangle(90, 50, MinuetoColor.WHITE, true);
            soundOnButton = new MinuetoImageFile("images/SoundImages/muted.png");
            soundOffButton = new MinuetoImageFile("images/SoundImages/unmuted.png");
        } catch (MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }

        // Play Music
        if (soundStarted == false) {
            playSound("music/flute.mid"); // TODO: add a mute/unmute button - Dijian and Alex
            soundStarted = true;
        }

        // create players TODO: remove this
        // List<Player> players = new ArrayList<>();
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

                // click on mute/unmute
                if (x > 1000 && y > 740) {
                    if (soundOn == true) {
                        soundOn = false;
                        pauseSound();
                        gui.window.draw(soundOffButton, 1000, 745);

                    } else {
                        soundOn = true;
                        resumeSound();
                        gui.window.draw(soundOnButton, 1000, 745);
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
        }, entryScreenQueue);

        // create login screen keyboard handler
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
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                        // if we in this catch block then there are no available game services or
                        // sessions
                        // TODO: for additional functionality, we can put a message that says "there are
                        // no game lobbies, please create one or use the refresh button"
                    }

                    gui.currentBackground = GUI.Screen.LOBBY;

                } else if (i == MinuetoKeyboard.KEY_SHIFT) {
                    shift = true;
                }
                // delete a char
                else if (i == MinuetoKeyboard.KEY_DELETE) {
                    if (userNameSel && userString.length() > 0) {
                        userString = userString.substring(0, userString.length() - 1);
                    } else if (passWordSel && passString.length() > 0) {
                        passString = passString.substring(0, passString.length() - 1);
                    } else {
                        return;
                    }
                }
                // TAB used to switch boxes
                else if (i == 9 && userNameSel) {
                    userNameSel = false;
                    passWordSel = true;
                }
                // uppercase or not a letter
                else if (shift || i < 65 || i > 90) {
                    if (userNameSel) {
                        userString = userString + (char) i;
                    } else if (passWordSel) {
                        passString = passString + (char) i;
                    }
                }
                // lowercase letters
                else {
                    if (userNameSel) {
                        userString = userString + (char) (i + 32);
                    } else if (passWordSel) {
                        passString = passString + (char) (i + 32);
                    }
                }
                // cover the last entry, draw username
                loginScreenImage.draw(whiteBoxImage, 160, 350);
                MinuetoImage username = new MinuetoText(userString, fontArial20, MinuetoColor.BLACK);
                loginScreenImage.draw(username, 200, 360);

                // cover the last entry, draw password
                loginScreenImage.draw(whiteBoxImage, 160, 440);
                MinuetoImage password = new MinuetoText(passString, fontArial20, MinuetoColor.BLACK);
                loginScreenImage.draw(password, 200, 450);

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
                        try {
                            if (getUser(userString) != null) {
                                // login existing user
                            } else {
                                createNewUser(userString, passString);
                            }

                        } catch (IOException | ParseException e) {
                            e.printStackTrace();
                            System.err.println("Error: could not register/login user");
                        }
                        gui.currentBackground = GUI.Screen.LOBBY;
                    }
                }

                // click on mute/unmute
                if (x > 1000 && y > 740) {
                    if (soundOn == true) {
                        soundOn = false;
                        pauseSound();
                        gui.window.draw(soundOffButton, 1000, 745);

                    } else {
                        soundOn = true;
                        resumeSound();
                        gui.window.draw(soundOnButton, 1000, 745);

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

                if (x > 1000 && y > 740) {
                    if (soundOn == true) {
                        soundOn = false;
                        pauseSound();
                        gui.window.draw(soundOffButton, 1000, 745);

                    } else {
                        soundOn = true;
                        resumeSound();
                        gui.window.draw(soundOnButton, 1000, 745);
                    }
                }

                // if we click on a town, move boot for the player (player.moveBoot(x,y)) and
                // cycle to the next player

                // for (int i = 0; i < players.size(); i++) {

                // while (ServerGame.notClickingOnATown(x, y)) {
                // for (Town t : ServerGame.getTowns()) {

                // if (t.minX < x && t.minY < y && t.maxX > x && t.maxY > y) {

                // // set a variable to keep track of the town at that location
                // Town townAtLoc = t;
                // // move the boot to that town
                // Player p = players.get(i);

                // p.moveBoot(t);

                // // draw the boot at a location
                // // depending on the number of players in the lobby, designate an arrangement
                // for
                // // possible boot
                // // slots around each city, will be populated by the specific boot each time

                // // gui.window.draw( BOOT, LOCATION1, LOCATION2);
                // }
                // }
                // }
                // }

            }

            @Override
            public void handleMouseRelease(int x, int y, int button) {
            }

            @Override
            public void handleMouseMove(int x, int y) {

            }
        }, moveBootQueue);

        // lobby screen mouse handler
        MinuetoEventQueue lobbyScreenQueue = new MinuetoEventQueue();
        gui.window.registerMouseHandler(new MinuetoMouseHandler() {
            @Override
            public void handleMousePress(int x, int y, int button) {
                if (x >= 30 && x <= 440 && y >= 680 && y <= 750) {
                    // click on Create New Game button
                    gui.currentBackground = GUI.Screen.CREATELOBBY;
                } else if (x >= 920 && x <= 990 && y >= 675 && y <= 745) {
                    // click on the Refresh button

                } else if (x > 1000 && y > 740) {
                    // click on mute/unmute button
                    if (soundOn == true) {
                        soundOn = false;
                        pauseSound();
                        gui.window.draw(soundOffButton, 1000, 745);

                    } else {
                        soundOn = true;
                        resumeSound();
                        gui.window.draw(soundOnButton, 1000, 745);
                    }
                }
            }

            @Override
            public void handleMouseRelease(int x, int y, int button) {
                // do nothing
            }

            @Override
            public void handleMouseMove(int x, int y) {
                // do nothing
            }
        }, lobbyScreenQueue);

        // create game screen keyboard handler
        Stack<String> createGameStack = new Stack<>();
        MinuetoEventQueue createGameQueue = new MinuetoEventQueue();
        gui.window.registerKeyboardHandler(new MinuetoKeyboardHandler() {
            private boolean shift = false;
            private MinuetoFont fontArial20 = new MinuetoFont("Arial", 19, false, false);

            @Override
            public void handleKeyPress(int i) {
                if (i == MinuetoKeyboard.KEY_SHIFT) {
                    shift = true;
                } else if (i == MinuetoKeyboard.KEY_DELETE) {
                    if (nameSel && nameString.length() > 0) {
                        nameString = nameString.substring(0, nameString.length() - 1);
                    } else if (numberPlayerSel && numberPlayerString.length() > 0) {
                        numberPlayerString = numberPlayerString.substring(0, numberPlayerString.length() - 1);
                    } else {
                        return;
                    }
                } else if (shift || i < 65 || i > 90) {
                    createGameStack.push("" + (char) i); // uppercase
                } else {
                    createGameStack.push("" + (char) (i + 32)); // lowercase
                }

                if (nameSel) {
                    // type inside the textbox for name of the lobby
                    while (!createGameStack.empty()) {
                        nameString = nameString + createGameStack.pop();
                    }

                    MinuetoImage name = new MinuetoText(nameString, fontArial20, MinuetoColor.BLACK);
                    createGameBackground.draw(name, 205, 250);
                    createGameStack.clear();
                } else if (numberPlayerSel) {
                    // type inside the textbox for number of players
                    while (!createGameStack.empty()) {
                        numberPlayerString = numberPlayerString + createGameStack.pop();
                    }
                    MinuetoImage numberPlayers = new MinuetoText(numberPlayerString, fontArial20, MinuetoColor.BLACK);
                    createGameBackground.draw(numberPlayers, 484, 462);
                    createGameStack.clear();
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
        }, createGameQueue);

        // create game screen mouse handler
        gui.window.registerMouseHandler(new MinuetoMouseHandler() {
            @Override
            public void handleMousePress(int x, int y, int button) {
                if (x >= 305 && x <= 715 && y <= 640 && y >= 570) {
                    // click on the Create New Game button
                    // TODO: check if name/size are empty and put an error message
                    // TODO: send a createGame message to the LS
                    gui.currentBackground = GUI.Screen.ELFENLAND; // TODO: change this line to show the game launch
                                                                  // screen
                } else if (x >= 330 && x <= 695 && y <= 725 && y >= 665) {
                    // click on the Return to Open Lobbies button
                    gui.currentBackground = GUI.Screen.LOBBY;
                } else if (x >= 195 && x <= 715 && y >= 235 && y <= 295) {
                    // click on the Name text field
                    nameSel = true;
                    numberPlayerSel = false;
                    // cover the last entry
                    createGameBackground.draw(nameTextField, 195, 235);
                } else if (x >= 475 && x <= 565 && y >= 445 && y <= 495) {
                    // click on the Size text field
                    nameSel = false;
                    numberPlayerSel = true;
                } else {
                    nameSel = false;
                    numberPlayerSel = false;
                    // cover the last entry
                    createGameBackground.draw(numberOfPlayersTextField, 475, 445);
                }

                if (x > 1000 && y > 740) {
                    // click on mute/unmute button
                    if (soundOn == true) {
                        soundOn = false;
                        pauseSound();
                        gui.window.draw(soundOffButton, 1000, 745);

                    } else {
                        soundOn = true;
                        resumeSound();
                        gui.window.draw(soundOnButton, 1000, 745);
                    }
                }
            }

            @Override
            public void handleMouseRelease(int x, int y, int button) {
                // do nothing
            }

            @Override
            public void handleMouseMove(int x, int y) {
                // do nothing
            }
        }, createGameQueue);

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
                gui.window.draw(lobbyBackground, 0, 0);
                while (lobbyScreenQueue.hasNext()) {
                    lobbyScreenQueue.handle();
                }

            } else if (gui.currentBackground == GUI.Screen.LOBBYPREV) {
                gui.window.draw(lobbyPrevBackground, 0, 0);

            } else if (gui.currentBackground == GUI.Screen.LOBBYNEXT) {
                gui.window.draw(lobbyNextBackground, 0, 0);

            } else if (gui.currentBackground == GUI.Screen.LOBBYPREVNEXT) {
                gui.window.draw(lobbyPrevNextBackground, 0, 0);

            } else if (gui.currentBackground == GUI.Screen.CREATELOBBY) {
                gui.window.draw(createGameBackground, 0, 0);
                while (createGameQueue.hasNext()) {
                    createGameQueue.handle();
                }

            } else if (gui.currentBackground == GUI.Screen.ELFENLAND) {
                gui.window.draw(elfenlandImage, 0, 0);

            } else if (gui.currentBackground == GUI.Screen.ELFENGOLD) {
                gui.window.draw(elfengoldImage, 0, 0);
            }

            if (gui.currentBackground == GUI.Screen.ELFENLAND || gui.currentBackground == GUI.Screen.ELFENGOLD) {
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

            // Add a button in the bottom right to pause the music
            if (soundOn == true) {
                gui.window.draw(soundOffButton, 1000, 745);
            } else {
                gui.window.draw(soundOnButton, 1000, 745);
            }

            window.render();
            Thread.yield();
        }

        // swing gui

    }

    /*
     * TODO: Owen
     * 
     * @pre: pNames is a list of filenames of the boot images
     *
     * @return: List of images corresponding to the filenames
     */
    /*
     * private static List<MinuetoImage> getBootImages(List<String> pNames) {
     * List<MinuetoImage> toReturn = new ArrayList<>();
     * for (String name : pNames) {
     * try {
     * toReturn.add(new MinuetoImageFile(name)); // name is null, gives exception
     * } catch (MinuetoFileException e) {
     * System.out.println("Could not load image file " + name);
     * e.printStackTrace();
     * }
     * }
     * return toReturn;
     * }
     * 
     * /* TODO: does this go in Image - Owen/Dijian?
     * 
     * @pre: pImages is a list of MinuetoImages which are boots
     * 
     * @post: contents of pImages are changed to be centered at starting town on
     * game window, and rotated and sized properly
     */
    /*
     * private static void configImages(List<MinuetoImage> pImages) {
     * for (int i = 0; i < pImages.size(); i++) {
     * pImages.set(i, pImages.get(i).rotate(-90));
     * pImages.set(i, pImages.get(i).scale(.125, .125));
     * }
     * }
     */

    /**
     * TODO: alex/dijian put a mute/unmute button
     * Play Music
     * 
     * @param soundFile sound file to play
     */
    static void playSound(String soundFile) {
        File f = new File("./" + soundFile);
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            loadedClip = clip;
            loadedClip.open(audioIn);
            loadedClip.start();
        } catch (Exception e) {
            throw new Error("Unable to play sound file");
        }
    }

    /*
     * Records the duration of the clip elapsed and pauses the audio file
     */

    static void pauseSound() {
        clipPos = loadedClip.getMicrosecondPosition();
        loadedClip.stop();
    }

    /*
     * Resumes the sound from a recorded clipPos
     */
    static void resumeSound() {
        // loadedClip.setMicrosecondPosition(clipPos);
        loadedClip.start();
    }

    private static JSONObject createAccessToken() throws IOException, ParseException {
        URL url = new URL("http://127.0.0.1:4242/oauth/token?grant_type=password&username=maex&password=abc123_ABC123");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");

        // Encode the token scope
        String encoded = Base64.getEncoder()
                .encodeToString(("bgp-client-name:bgp-client-pw").getBytes(StandardCharsets.UTF_8)); // Java 8
        con.setRequestProperty("Authorization", "Basic " + encoded);
        /* Payload support */
        con.setDoOutput(true);

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        System.out.println("Response status: " + status);
        System.out.println(content.toString());

        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(content.toString());
    }

    // refreshes the access token
    private static JSONObject refreshAccessToken() throws IOException, ParseException {
        URL url = new URL(
                "http://127.0.0.1:4242/oauth/token?grant_type=refresh_token&refresh_token="
                        + token.get("refresh_token"));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");

        // Encode the token scope
        String encoded = Base64.getEncoder()
                .encodeToString(("bgp-client-name:bgp-client-pw").getBytes(StandardCharsets.UTF_8)); // Java 8
        con.setRequestProperty("Authorization", "Basic " + encoded);

        /* Payload support */
        con.setDoOutput(true);

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        System.out.println("Response status: " + status);
        System.out.println(content.toString());

        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(content.toString());
    }

    private static JSONObject createNewUser(String userName, String passWord) throws IOException, ParseException {
        /*
         * add a new user to the API
         */
        URL userUrl = new URL(
                "http://127.0.0.1:4242/api/users/foobar?access_token=" + token.get("access_token"));
        HttpURLConnection userCon = (HttpURLConnection) userUrl.openConnection();
        userCon.setRequestMethod("PUT");
        userCon.setRequestProperty("Content-Type", "application/json");

        // /* Payload support */
        userCon.setDoOutput(true);
        DataOutputStream userOut = new DataOutputStream(userCon.getOutputStream());
        userOut.writeBytes("{\n");
        // make the actual username
        userOut.writeBytes(" \"name\": \"" + userName + "\",\n");
        userOut.writeBytes(" \"password\": \"" + passWord + "\",\n");
        // need to update color when we know it
        userOut.writeBytes(" \"preferredColour\": \"01FFFF\",\n");

        userOut.writeBytes(" \"role\": \"ROLE_PLAYER\"\n");
        userOut.writeBytes("}");
        userOut.flush();
        userOut.close();

        int userStatus = userCon.getResponseCode();
        BufferedReader userIn = new BufferedReader(new InputStreamReader(userCon.getInputStream()));
        String userInputLine;
        StringBuffer userContent = new StringBuffer();
        while ((userInputLine = userIn.readLine()) != null) {
            userContent.append(userInputLine);
        }
        userIn.close();
        userCon.disconnect();
        System.out.println("Response status: " + userStatus);
        System.out.println(userContent.toString());

        JSONParser parser = new JSONParser();
        JSONObject user = (JSONObject) parser.parse(userContent.toString());
        return user;
    }

    // get user from API by username
    private static JSONObject getUser(String userName) throws IOException, ParseException {
        URL url = new URL("http://127.0.0.1:4242/api/users/" + userName + "?access_token=" + token.get("access_token"));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        /* Payload support */
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes("user_oauth_approval=true&_csrf=19beb2db-3807-4dd5-9f64-6c733462281b&authorize=true");
        out.flush();
        out.close();

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        System.out.println("Response status: " + status);
        System.out.println(content.toString());

        // user does not exist
        if (status == 400) {
            return null;
        }
        JSONParser parser = new JSONParser();
        JSONObject user = (JSONObject) parser.parse(content.toString());
        return user;
    }
}
