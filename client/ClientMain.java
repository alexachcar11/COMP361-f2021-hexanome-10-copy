
// minueto
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.MinuetoFileException;
import org.minueto.handlers.MinuetoKeyboard;
import org.minueto.handlers.MinuetoKeyboardHandler;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.*;
import org.minueto.window.MinuetoFrame;
import org.minueto.window.MinuetoWindow;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.common.collect.ImmutableList;

public class ClientMain {

    // fields
    public static User currentUser;
    public static LobbyServiceGameSession currentSession;

    GUI gui;
    MinuetoEventQueue entryScreenQueue, loginScreenQueue, moveBootQueue, lobbyScreenQueue, createGameQueue,
            elfenlandLobbyQueue;
    MinuetoFont fontArial20 = new MinuetoFont("Arial", 19, false, false);
    List<Player> players; // not sure if this should be here
    // make images
    MinuetoImage elfenlandImage;
    MinuetoImage elfengoldImage;
    // TODO: fix this List<MinuetoImage> bootImages = getBootImages(bootFileNames);
    MinuetoImage playScreenImage;
    MinuetoImage loginScreenImage;
    MinuetoImage whiteBoxImage;
    private static MinuetoImage lobbyBackground;
    MinuetoImage lobbyPrevBackground;
    MinuetoImage lobbyNextBackground;
    MinuetoImage lobbyPrevNextBackground;
    MinuetoImage createGameBackground;
    MinuetoImage elfenlandSelected;
    MinuetoImage elfenGoldSelected;
    MinuetoImage lobbyElfenlandBackground;
    MinuetoImage lobbyElfengoldBackground;
    MinuetoImage readyGreen;
    MinuetoImage readyWhite;
    MinuetoImage startButton;
    MinuetoFont fontArial22Bold;
    MinuetoRectangle modeDropdownRectangle;
    MinuetoRectangle destinationTownDropdownRectangle;
    MinuetoRectangle roundsDropdownRectangle;
    MinuetoText modeElfenlandText;
    MinuetoText modeElfengoldText;
    MinuetoText destinationTownNoText;
    MinuetoText destinationTownYesText;
    MinuetoText destinationTownYesRandText;
    MinuetoText rounds3Text;
    MinuetoText rounds4Text;
    MinuetoText witchNoText;
    MinuetoText witchYesText;
    MinuetoRectangle nameTextField;
    MinuetoRectangle numberOfPlayersTextField;
    MinuetoImage soundOnButton;
    MinuetoImage soundOffButton;

    public static final Registrator REGISTRATOR = Registrator.instance();

    // TODO: place this somewhere else configImages(bootImages);
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

    MinuetoMouseHandler entryScreenMouseHandler = new MinuetoMouseHandler() {
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
                if (soundOn) {
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
    };
    MinuetoMouseHandler loginScreenMouseHandler = new MinuetoMouseHandler() {

        @Override
        public void handleMousePress(int x, int y, int button) {

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

                else {
                    try {
                        boolean userFound = false;
                        for (Object user : REGISTRATOR.getAllUsers()) {
                            if (((JSONObject) user).get("name").equals(userString)) {
                                userFound = true;
                            }
                        }
                        if (userFound) {
                            // user exists, login
                            System.out.println("User exists");
                            currentUser = new User(userString, passString);
                            // http://127.0.0.1:4242/oauth/username?access_token=37S8hhdMCdXupIatPm82xJpXXas=);
                        } else {
                            // user doesn't exist. create and login
                            User newUser = REGISTRATOR.createNewUser(userString, passString);
                            System.out.println("New User");
                            currentUser = newUser;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Error: failed to login a user.");
                    }
                    // change screen after login
                    try {
                        REGISTRATOR.createNewGame("testgame", 6, 3, Mode.ELFENLAND, false, false);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    displayAvailableGames();
                    gui.currentBackground = GUI.Screen.LOBBY;
                }
            }

            // click on mute/unmute
            if (x > 1000 && y > 740) {
                if (soundOn) {
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
    };
    MinuetoKeyboardHandler loginScreenKeyboardHandler = new MinuetoKeyboardHandler() {
        private boolean shift = false;

        @Override
        public void handleKeyPress(int i) {
            // press on enter key takes you to the next screen
            if (i == MinuetoKeyboard.KEY_ENTER) {
                // TODO: owen add login verification here

                // switch to lobby screen if login ok
                displayAvailableGames();
                gui.currentBackground = GUI.Screen.LOBBY;
            }
            // TAB used to switch boxes
            else if (i == 9 && userNameSel) {
                userNameSel = false;
                passWordSel = true;
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
    };

    MinuetoMouseHandler moveBootMouseHandler = new MinuetoMouseHandler() {
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
                if (soundOn) {
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

            for (int i = 0; i < players.size(); i++) {

                while (ServerGame.notClickingOnATown(x, y)) {
                    for (Town t : ServerGame.getTowns()) {

                        if (t.minX < x && t.minY < y && t.maxX > x && t.maxY > y) {

                            // set a variable to keep track of the town at that location
                            Town townAtLoc = t;
                            // move the boot to that town
                            Player p = players.get(i);

                            p.moveBoot(t);

                            // draw the boot at a location
                            // depending on the number of players in the lobby, designate an arrangement for
                            // possible boot
                            // slots around each city, will be populated by the specific boot each time

                            // gui.window.draw( BOOT, LOCATION1, LOCATION2);
                        }
                    }
                }
            }

        }

        @Override
        public void handleMouseRelease(int x, int y, int button) {
        }

        @Override
        public void handleMouseMove(int x, int y) {

        }
    };
    MinuetoMouseHandler lobbyMouseHandler = new MinuetoMouseHandler() {
        @Override
        public void handleMousePress(int x, int y, int button) {

            System.out.println("x:" + x + " y:" + y);

            if (x >= 30 && x <= 440 && y >= 680 && y <= 750) {
                // click on Create New Game button
                gui.currentBackground = GUI.Screen.CREATELOBBY;
            } else if (x >= 920 && x <= 990 && y >= 675 && y <= 745) {
                // click on the Refresh button
                // TODO: test this when there is a difference in between refreshes
                displayAvailableGames();
            } else {
                // click on a Join button
                for (AbstractMap.SimpleEntry<ImmutableList, Joinable> coords : joinButtonCoordinates) {
                    int maxX = (int) coords.getKey().get(0);
                    int minX = (int) coords.getKey().get(1);
                    int maxY = (int) coords.getKey().get(2);
                    int minY = (int) coords.getKey().get(3);

                    if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                        Joinable gameToJoin = coords.getValue();
                        try {
                            gameToJoin.join();
                            currentSession = gameToJoin.getActiveSession();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        gui.currentBackground = GUI.Screen.LOBBYELFENLAND; // TODO: fix this
                    }
                }
            }

            if (x > 1000 && y > 740) {
                // click on mute/unmute button
                if (soundOn) {
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
    };
    MinuetoKeyboardHandler gameScreenKeyboardHandler = new MinuetoKeyboardHandler() {
        private boolean shift = false;

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
            } else if (shift || i < 65 || i > 90) { // uppercase
                                                    // or
                                                    // not
                                                    // a
                                                    // letter
                if (nameSel) {
                    nameString = nameString + (char) i;
                } else if (numberPlayerSel) {
                    numberPlayerString = numberPlayerString + (char) i;
                }
            } else { // lowercase
                     // letters
                if (nameSel) {
                    nameString = nameString + (char) (i + 32);
                } else if (numberPlayerSel) {
                    numberPlayerString = numberPlayerString + (char) (i + 32);
                }
            }

            // cover the last entry, draw name
            createGameBackground.draw(nameTextField, 195, 235);
            MinuetoImage name = new MinuetoText(nameString, fontArial20, MinuetoColor.BLACK);
            createGameBackground.draw(name, 205, 250);

            // cover the last entry, draw number of players
            createGameBackground.draw(numberOfPlayersTextField, 475, 445);
            MinuetoImage numberPlayers = new MinuetoText(numberPlayerString, fontArial20, MinuetoColor.BLACK);
            createGameBackground.draw(numberPlayers, 485, 460);
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
    };
    MinuetoMouseHandler gameScreenMouseHandler = new MinuetoMouseHandler() {
        @Override
        public void handleMousePress(int x, int y, int button) {
            System.out.println("This is x:" + x + "This is y:" + y);
            if (x >= 195 && x <= 715 && y >= 235 && y <= 295) {
                // click on the Name text field
                nameSel = true;
                numberPlayerSel = false;
            } else if (x >= 475 && x <= 565 && y >= 445 && y <= 495) {
                // click on the Size text field
                nameSel = false;
                numberPlayerSel = true;
            } else {
                nameSel = false;
                numberPlayerSel = false;
                if (x >= 305 && x <= 715 && y <= 640 && y >= 570) {
                    // click on the Create New Game button
                    // TODO: check if name/size are empty and put an error message
                    // TODO: send a createGame message to the LS
                    if (elfenlandSel) {
                        gui.currentBackground = GUI.Screen.LOBBYELFENLAND;
                    } else if (elfenGoldSel) {
                        gui.currentBackground = GUI.Screen.LOBBYELFENGOLD;
                    } else {
                        // TODO: show error message ("Please select a game mode.")
                        System.out.println("none selected");
                    }
                } else if (x >= 330 && x <= 695 && y <= 725 && y >= 665) {
                    // click on the Return to Open Lobbies button
                    displayAvailableGames();
                    gui.currentBackground = GUI.Screen.LOBBY;
                } else if (x >= 290 && x <= 535 && y <= 400 && y >= 340) { // 245x60
                    // click on Elfenland
                    createGameBackground.draw(elfenlandSelected, 0, 0);
                    elfenlandSel = true;
                    elfenGoldSel = false;
                    // cover the last entry, draw name - temporary bug fix
                    createGameBackground.draw(nameTextField, 195, 235);
                    MinuetoImage name = new MinuetoText(nameString, fontArial20, MinuetoColor.BLACK);
                    createGameBackground.draw(name, 205, 250);
                    // cover the last entry, draw number of players - temporary bug fix
                    createGameBackground.draw(numberOfPlayersTextField, 475, 445);
                    MinuetoImage numberPlayers = new MinuetoText(numberPlayerString, fontArial20, MinuetoColor.BLACK);
                    createGameBackground.draw(numberPlayers, 485, 460);
                } else if (x >= 550 && x <= 810 && y <= 400 && y >= 338) { // 260x62
                    // click on Elfengold
                    createGameBackground.draw(elfenGoldSelected, 0, 0);
                    elfenlandSel = false;
                    elfenGoldSel = true;
                    // cover the last entry, draw name - temporary bug fix
                    createGameBackground.draw(nameTextField, 195, 235);
                    MinuetoImage name = new MinuetoText(nameString, fontArial20, MinuetoColor.BLACK);
                    createGameBackground.draw(name, 205, 250);
                    // cover the last entry, draw number of players - temporary bug fix
                    createGameBackground.draw(numberOfPlayersTextField, 475, 445);
                    MinuetoImage numberPlayers = new MinuetoText(numberPlayerString, fontArial20, MinuetoColor.BLACK);
                    createGameBackground.draw(numberPlayers, 485, 460);
                } else if (x > 1000 && y > 740) {
                    // click on mute/unmute button
                    if (soundOn) {
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

        }

        @Override
        public void handleMouseRelease(int x, int y, int button) {
            // do nothing
        }

        @Override
        public void handleMouseMove(int x, int y) {
            // do nothing
        }
    };

    MinuetoMouseHandler elfenLandLobbyMouseHandler = new MinuetoMouseHandler() {
        private boolean ready = false;

        @Override
        public void handleMousePress(int x, int y, int button) {
            System.out.println("x: " + x + "y: " + y);

            if (x >= 825 && x <= 1000 && y >= 675 && y <= 735) {
                // click on Leave button
                REGISTRATOR.leaveGame(currentSession, currentUser);
                //return to lobby screen
                displayAvailableGames();
                gui.currentBackground = GUI.Screen.LOBBY;
            } else if (x >= 822 & x <= 998 && y <= 655 && y >= 585) {
                // click on Ready? button
                ready = !ready;
                if (ready) {
                    lobbyElfenlandBackground.draw(readyGreen, 823, 581);
                    // TODO: display Ready next to the player's name
                    // TODO: notify all players that this player is ready
                    // TODO: change to startButton when all players are ready and this player is the
                    // game creator
                } else {
                    lobbyElfenlandBackground.draw(readyWhite, 823, 581);
                    // TODO: display Not Ready next to player's name
                    // TODO: notify all players that this player is not ready
                    // TODO: stop displaying the Start button to the host
                }
            } else if (x >= 945 && x <= 990 && y >= 180 && y <= 215) { // x: 763-990 y: 178-220
                // click on Mode button
                modeDropdownActive = !modeDropdownActive;
                destinationDropdownActive = false;
                roundsDropdownActive = false;
            } else if (x >= 935 && x <= 985 && y >= 360 && y <= 400) { // x:
                                                                       // 684-986
                                                                       // y:
                                                                       // 358-399
                // click on Destination Town button
                destinationDropdownActive = !destinationDropdownActive;
                modeDropdownActive = false;
                roundsDropdownActive = false;
            } else if (x >= 932 && x <= 985 && y >= 410 && y <= 450) { // x:
                                                                       // 797-985
                                                                       // y:
                                                                       // 411-450
                // click on Rounds button
                roundsDropdownActive = !roundsDropdownActive;
                modeDropdownActive = false;
                destinationDropdownActive = false;
            } else if (x >= 710 && x <= 800 && y >= 700 && y <= 735) {
                // click on Send Message button
            }

            // clicking an option from the dropdowns
            if (modeDropdownActive) {

            } else if (destinationDropdownActive) {

            } else if (roundsDropdownActive) {

            }

            if (x > 1000 && y > 740) {
                // click on mute/unmute button
                if (soundOn) {
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
    };

    // for login screen queue
    private static boolean userNameSel = false;
    private static boolean passWordSel = false;
    private static String userString = "";
    private static String passString = "";

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
    private static boolean elfenlandSel = false;
    private static boolean elfenGoldSel = false;

    // for game lobby
    private static boolean modeDropdownActive = false;
    private static boolean destinationDropdownActive = false;
    private static boolean roundsDropdownActive = false;
    private static boolean witchDropdownActive = false;

    // for lobbyMouseHandler
    private static ArrayList<AbstractMap.SimpleEntry<ImmutableList, Joinable>> joinButtonCoordinates = new ArrayList<>();

    // ******************************************MAIN CODE STARTS
    // HERE********************************************
    public static void main(String[] args) {
        new ClientMain();
    }

    public ClientMain() {
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
            elfenlandSelected = new MinuetoImageFile("images/create-game-elfenland.png");
            elfenGoldSelected = new MinuetoImageFile("images/create-game-elfengold.png");
            lobbyElfenlandBackground = new MinuetoImageFile("images/game-lobby-elfenland.png");
            lobbyElfengoldBackground = new MinuetoImageFile("images/game-lobby-elfengold.png");
            readyGreen = new MinuetoImageFile("images/ready-button-green.png");
            readyWhite = new MinuetoImageFile("images/ready-button-white.png");
            startButton = new MinuetoImageFile("images/start-button.png");
            fontArial22Bold = new MinuetoFont("Arial", 22, true, false);
            modeDropdownRectangle = new MinuetoRectangle(229, 42, MinuetoColor.WHITE, true);
            destinationTownDropdownRectangle = new MinuetoRectangle(300, 41, MinuetoColor.WHITE, true);
            roundsDropdownRectangle = new MinuetoRectangle(186, 39, MinuetoColor.WHITE, true);
            modeElfenlandText = new MinuetoText("Elfenland", fontArial22Bold, MinuetoColor.BLACK);
            modeElfengoldText = new MinuetoText("Elfengold", fontArial22Bold, MinuetoColor.BLACK);
            destinationTownNoText = new MinuetoText("No", fontArial22Bold, MinuetoColor.BLACK);
            destinationTownYesText = new MinuetoText("Yes: not random", fontArial22Bold, MinuetoColor.BLACK);
            destinationTownYesRandText = new MinuetoText("Yes: random", fontArial22Bold, MinuetoColor.BLACK);
            rounds3Text = new MinuetoText("3", fontArial22Bold, MinuetoColor.BLACK);
            rounds4Text = new MinuetoText("4", fontArial22Bold, MinuetoColor.BLACK);
            soundOnButton = new MinuetoImageFile("images/SoundImages/muted.png");
            soundOffButton = new MinuetoImageFile("images/SoundImages/unmuted.png");
        } catch (MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }

        // Play Music
        if (!soundStarted) {
            playSound("music/flute.mid");
            soundStarted = true;
        }

        // create players TODO: remove this
        List<Player> players = new ArrayList<>();
        // Player p1 = new Player(null, Color.YELLOW);
        // Player p2 = new Player(null, Color.BLACK);
        // players.add(p1);
        // players.add(p2);

        // create window that will contain our game - stays in Main
        MinuetoWindow window = new MinuetoFrame(1024, 768, true);
        gui = new GUI(window, GUI.Screen.MENU);
        window.setMaxFrameRate(60);

        // make window visible
        gui.window.setVisible(true);

        // create entry screen mouse handler TODO: where does this go (Lilia / Owen)
        entryScreenQueue = new MinuetoEventQueue();
        gui.window.registerMouseHandler(entryScreenMouseHandler, entryScreenQueue);

        // create login screen keyboard handler
        loginScreenQueue = new MinuetoEventQueue();
        gui.window.registerKeyboardHandler(loginScreenKeyboardHandler, loginScreenQueue);
        gui.window.registerMouseHandler(loginScreenMouseHandler, loginScreenQueue);

        // TODO: move this to where it belongs
        moveBootQueue = new MinuetoEventQueue();
        gui.window.registerMouseHandler(moveBootMouseHandler, moveBootQueue);

        // lobby screen mouse handler
        lobbyScreenQueue = new MinuetoEventQueue();
        gui.window.registerMouseHandler(lobbyMouseHandler, lobbyScreenQueue);

        // create game screen keyboard handler
        createGameQueue = new MinuetoEventQueue();
        gui.window.registerKeyboardHandler(gameScreenKeyboardHandler, createGameQueue);

        // create game screen mouse handler
        gui.window.registerMouseHandler(gameScreenMouseHandler, createGameQueue);

        // mouse handler for elfenland lobby
        elfenlandLobbyQueue = new MinuetoEventQueue();
        gui.window.registerMouseHandler(elfenLandLobbyMouseHandler, elfenlandLobbyQueue);

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

            } else if (gui.currentBackground == GUI.Screen.LOBBYELFENLAND) {
                gui.window.draw(lobbyElfenlandBackground, 0, 0);

                /*
                 * MinuetoText elfenlandText = new MinuetoText("Elfenland", fontArial22Bold,
                 * MinuetoColor.BLACK);
                 * lobbyElfenlandBackground.draw(elfenlandText, 775, 185);
                 * String size = "3"; // TODO: change this to the actual variable
                 * MinuetoText sizeText = new MinuetoText(size, fontArial22Bold,
                 * MinuetoColor.BLACK);
                 * lobbyElfenlandBackground.draw(sizeText, 767, 255);
                 * boolean destinationTown = false; // TODO change this to actual value ( enum
                 * with 3 options )
                 * MinuetoText destinationTownText;
                 * if (destinationTown) {
                 * destinationTownText = new MinuetoText("No", fontArial22Bold,
                 * MinuetoColor.BLACK);
                 * } else {
                 * destinationTownText = new MinuetoText("Yes", fontArial22Bold,
                 * MinuetoColor.BLACK);
                 * }
                 * lobbyElfenlandBackground.draw(destinationTownText, 699, 365);
                 * String rounds = "3";
                 * MinuetoText round = new MinuetoText(rounds, fontArial22Bold,
                 * MinuetoColor.BLACK);
                 * lobbyElfenlandBackground.draw(round, 820, 420);
                 */
                while (elfenlandLobbyQueue.hasNext()) {
                    elfenlandLobbyQueue.handle();
                }

                // display dropdowns
                if (modeDropdownActive) {
                    gui.window.draw(modeDropdownRectangle, 762, 217);
                    // lobbyElfenlandBackground.draw(modeDropdownRectangle, 762, 217);
                } else if (destinationDropdownActive) {
                    gui.window.draw(destinationTownDropdownRectangle, 684, 397);
                    // lobbyElfenlandBackground.draw(destinationTownDropdownRectangle, 684, 397);
                } else if (roundsDropdownActive) {
                    gui.window.draw(roundsDropdownRectangle, 797, 450);
                    // lobbyElfenlandBackground.draw(roundsDropdownRectangle, 797, 450);
                }

            } else if (gui.currentBackground == GUI.Screen.LOBBYELFENGOLD) {
                gui.window.draw(lobbyElfengoldBackground, 0, 0);

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
            if (soundOn) {
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

    public static void displayAvailableGames() {
        // TODO: can I handle the try/catch elsewhere? (lilia)
        MinuetoFont font = new MinuetoFont("Arial", 22, true, false);
        try {
            ArrayList<LobbyServiceGame> availableGamesList = REGISTRATOR.getAvailableGames();
            ArrayList<LobbyServiceGameSession> availableSessionsList = REGISTRATOR.getAvailableSessions();

            if (availableSessionsList.size() == 0 && availableGamesList.size() == 0) {
                MinuetoText noneAvailableText = new MinuetoText(
                        "There are no games yet. Please refresh or create a new game.", font, MinuetoColor.BLACK);
                lobbyBackground.draw(noneAvailableText, 200, 340);
            }

            int counter = 0; // how many games are displayed so far

            // display available game services (i.e. games with no creator)
            for (LobbyServiceGame g : availableGamesList) {
                String gDisplayName = g.getDisplayName();
                String gMaxPlayers = String.valueOf(g.getMaxNumberOfPlayers());

                MinuetoText displayName = new MinuetoText(gDisplayName, font, MinuetoColor.BLACK);
                MinuetoText creator = new MinuetoText("No creator", font, MinuetoColor.BLACK);
                MinuetoText size = new MinuetoText("0/" + gMaxPlayers, font, MinuetoColor.BLACK);
                MinuetoRectangle joinButton = new MinuetoRectangle(100, 35, MinuetoColor.WHITE, true);
                MinuetoText joinText = new MinuetoText("JOIN", font, MinuetoColor.BLACK);

                lobbyBackground.draw(displayName, 65, 215 + (counter * 50));
                lobbyBackground.draw(creator, 350, 215 + (counter * 50));
                lobbyBackground.draw(size, 655, 215 + (counter * 50));
                lobbyBackground.draw(joinButton, 835, 210 + (counter * 50));
                lobbyBackground.draw(joinText, 855, 215 + (counter * 50));

                // keep track of the button location
                Integer maxX = 935;
                Integer minX = 835;
                Integer maxY = 245 + (counter * 50);
                Integer minY = 210 + (counter * 50);
                ImmutableList<Integer> listOfCoordinates = ImmutableList.of(maxX, minX, maxY, minY);
                AbstractMap.SimpleEntry<ImmutableList, Joinable> entry = new AbstractMap.SimpleEntry<ImmutableList, Joinable>(
                        listOfCoordinates, g);
                joinButtonCoordinates.add(entry);

                counter++;
            }

            // display available game sessions (i.e. games with a creator)
            for (LobbyServiceGameSession gs : availableSessionsList) {
                if (!gs.isLaunched()) { // only show unlaunched sessions
                    String gsName = gs.getGameService().getDisplayName();
                    String gsCreator = gs.getCreator();
                    String gsCurrentPlayerNumber = String.valueOf(gs.getNumberOfPlayersCurrently());
                    String gsMaxPlayerNumber = String.valueOf(gs.getGameService().getMaxNumberOfPlayers());

                    MinuetoText displayName = new MinuetoText(gsName, font, MinuetoColor.BLACK);
                    MinuetoText creator = new MinuetoText(gsCreator, font, MinuetoColor.BLACK);
                    MinuetoText size = new MinuetoText(gsCurrentPlayerNumber + "/" + gsMaxPlayerNumber, font,
                            MinuetoColor.BLACK);
                    MinuetoRectangle joinButton = new MinuetoRectangle(100, 35, MinuetoColor.WHITE, true);
                    MinuetoText joinText = new MinuetoText("JOIN", font, MinuetoColor.BLACK);

                    lobbyBackground.draw(displayName, 65, 215 + (counter * 50));
                    lobbyBackground.draw(creator, 350, 215 + (counter * 50));
                    lobbyBackground.draw(size, 655, 215 + (counter * 50));
                    lobbyBackground.draw(joinButton, 835, 210 + (counter * 50));
                    lobbyBackground.draw(joinText, 855, 215 + (counter * 50));

                    // keep track of the button location
                    Integer maxX = 935;
                    Integer minX = 835;
                    Integer maxY = 245 + (counter * 50);
                    Integer minY = 210 + (counter * 50);
                    ImmutableList<Integer> listOfCoordinates = ImmutableList.of(maxX, minX, maxY, minY);
                    AbstractMap.SimpleEntry<ImmutableList, Joinable> entry = new AbstractMap.SimpleEntry<ImmutableList, Joinable>(
                            listOfCoordinates, gs);
                    joinButtonCoordinates.add(entry);

                    counter++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
