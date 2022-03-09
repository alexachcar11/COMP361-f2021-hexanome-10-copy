package clientsrc;

// minueto
import org.json.simple.JSONObject;
import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.MinuetoFileException;
import org.minueto.handlers.MinuetoKeyboard;
import org.minueto.handlers.MinuetoKeyboardHandler;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.*;
import org.minueto.window.MinuetoFrame;
import org.minueto.window.MinuetoWindow;

import networksrc.ChooseBootColorAction;
//import networksrc.ChooseBootColorAction;
import networksrc.GetAvailableColorsAction;
import networksrc.TestAction;

// import serversrc.Color;
// import serversrc.Mode;
// import serversrc.Player;
// import serversrc.TownGoldOption;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.ImmutableList;

public class ClientMain {

    // fields
    public static User currentUser;
    public static LobbyServiceGameSession currentSession;
    public static Game currentGame;
    public static Player currentPlayer;

    public static GUI gui;
    static MinuetoEventQueue entryScreenQueue, loginScreenQueue, moveBootQueue, lobbyScreenQueue, createGameQueue,
            elfenlandLobbyQueue, chooseBootQueue;
    static MinuetoFont fontArial20 = new MinuetoFont("Arial", 19, false, false);
    static List<Player> players; // not sure if this should be here
    // make images
    static MinuetoImage elfenlandImage;
    static MinuetoImage elfengoldImage;
    // TODO: fix this List<MinuetoImage> bootImages = getBootImages(bootFileNames);
    static MinuetoImage playScreenImage;
    static MinuetoImage loginScreenImage;
    static MinuetoImage whiteBoxImage;
    private static MinuetoImage lobbyBackground;
    static MinuetoImage createGameBackground;
    static MinuetoImage createGameBackgroundElfengold;
    static MinuetoImage elfenlandSelected;
    static MinuetoImage elfenGoldSelected;
    private static MinuetoImage chooseBootBackground;
    private static MinuetoImage redBoppel;
    private static MinuetoImage blueBoppel;
    private static MinuetoImage greenBoppel;
    private static MinuetoImage blackBoppel;
    private static MinuetoImage yellowBoppel;
    private static MinuetoImage purpleBoppel;
    private static MinuetoImage lobbyElfenlandBackground;
    private static MinuetoImage lobbyElfengoldBackground;
    private static MinuetoImage lobbyElfenlandCreatorBackground;
    private static MinuetoImage lobbyElfengoldCreatorBackground;
    static MinuetoImage readyGreen;
    static MinuetoImage readyWhite;
    static MinuetoImage startButton;
    static MinuetoImage greyStartButton;
    static MinuetoFont fontArial22Bold;
    static MinuetoRectangle modeDropdownRectangle;
    static MinuetoRectangle destinationTownDropdownRectangle;
    static MinuetoRectangle sizeDropdownRectangle;
    static MinuetoRectangle townGoldDropdownRectangle;
    static MinuetoRectangle witchDropdownRectangle;
    static MinuetoRectangle roundsDropdownRectangle;
    static MinuetoText modeElfenlandText;
    static MinuetoText modeElfengoldText;
    static MinuetoText destinationTownNoText;
    static MinuetoText destinationTownYesText;
    static MinuetoText rounds3Text;
    static MinuetoText rounds4Text;
    static MinuetoText size2Text;
    static MinuetoText size3Text;
    static MinuetoText size4Text;
    static MinuetoText size5Text;
    static MinuetoText size6Text;
    static MinuetoText townGoldNoText;
    static MinuetoText townGoldYesText;
    static MinuetoText townGoldYesRandText;
    static MinuetoText witchNoText;
    static MinuetoText witchYesText;
    static MinuetoRectangle nameTextField;
    static MinuetoImage soundOnButton;
    static MinuetoImage soundOffButton;

    public static final Registrator REGISTRATOR = Registrator.instance();
    public static final ActionManager ACTION_MANAGER = ActionManager.getInstance();

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

    static MinuetoMouseHandler entryScreenMouseHandler = new MinuetoMouseHandler() {
        @Override
        public void handleMousePress(int x, int y, int button) {
            // click on Play
            if (x <= 665 && x >= 360 && y >= 345 && y <= 445) {
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
    static MinuetoMouseHandler loginScreenMouseHandler = new MinuetoMouseHandler() {

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

                            // send a test action
                            ACTION_MANAGER.sendActionAndGetReply(new TestAction(currentUser.getName()));
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
    static MinuetoKeyboardHandler loginScreenKeyboardHandler = new MinuetoKeyboardHandler() {
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

    static MinuetoMouseHandler moveBootMouseHandler = new MinuetoMouseHandler() {
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
            /*
             * for (int i = 0; i < players.size(); i++) {
             * 
             * while (ServerGame.notClickingOnATown(x, y)) {
             * for (Town t : ServerGame.getTowns()) {
             * 
             * if (t.minX < x && t.minY < y && t.maxX > x && t.maxY > y) {
             * 
             * // set a variable to keep track of the town at that location
             * Town townAtLoc = t;
             * // move the boot to that town
             * Player p = players.get(i);
             * 
             * p.moveBoot(t);
             * 
             * // draw the boot at a location
             * // depending on the number of players in the lobby, designate an arrangement
             * for
             * // possible boot
             * // slots around each city, will be populated by the specific boot each time
             * 
             * // gui.window.draw( BOOT, LOCATION1, LOCATION2);
             * }
             * }
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
    };
    static MinuetoMouseHandler lobbyMouseHandler = new MinuetoMouseHandler() {
        @Override
        public void handleMousePress(int x, int y, int button) {

            System.out.println("x:" + x + " y:" + y);

            if (x >= 30 && x <= 440 && y >= 680 && y <= 750) {
                // click on Create New Game button
                gui.currentBackground = GUI.Screen.CREATELOBBY;
            } else if (x >= 920 && x <= 990 && y >= 675 && y <= 745) {
                // click on the Refresh button
                displayAvailableGames();
            } else {
                // click on a Join button
                for (AbstractMap.SimpleEntry<ImmutableList, LobbyServiceGameSession> coords : joinButtonCoordinates) {
                    int maxX = (int) coords.getKey().get(0);
                    int minX = (int) coords.getKey().get(1);
                    int maxY = (int) coords.getKey().get(2);
                    int minY = (int) coords.getKey().get(3);

                    if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                        gameToJoin = coords.getValue();
                        try {
                            // get available boot colors
                            ACTION_MANAGER.sendActionAndGetReply(
                                    new GetAvailableColorsAction(currentUser.getName(), gameToJoin.getSessionID()));
                            currentSession = gameToJoin;
                            gui.currentBackground = GUI.Screen.CHOOSEBOOT;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

    /**
     * This handles the keyboard input on the game creation page (the name input
     * field)
     */
    static MinuetoKeyboardHandler gameScreenKeyboardHandler = new MinuetoKeyboardHandler() {
        private boolean shift = false;

        @Override
        public void handleKeyPress(int i) {
            if (i == MinuetoKeyboard.KEY_SHIFT) {
                // press shift
                shift = true;
            } else if (i == MinuetoKeyboard.KEY_DELETE) {
                // press delete
                if (nameSel && nameString.length() > 0) {
                    nameString = nameString.substring(0, nameString.length() - 1);
                }
            } else if (shift || i < 65 || i > 90) {
                // uppercase letters
                if (nameSel) {
                    nameString = nameString + (char) i;
                }
            } else {
                // lowercase letters
                if (nameSel) {
                    nameString = nameString + (char) (i + 32);
                }
            }

            // cover the last entry, draw name
            createGameBackground.draw(nameTextField, 168, 101);
            MinuetoImage name = new MinuetoText(nameString, fontArial20, MinuetoColor.BLACK);
            createGameBackground.draw(name, 178, 108);
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
    static MinuetoMouseHandler gameScreenMouseHandler = new MinuetoMouseHandler() {
        @Override
        public void handleMousePress(int x, int y, int button) {
            System.out.println("This is x:" + x + "This is y:" + y);

            // new dropdowns
            // adjust lobbyservicegame to include new parameters

            if (x >= 168 && x <= 581 && y >= 102 && y <= 145) {
                // click on the Name text field - enable text input
                nameSel = true;
                // close the dropdowns
                sizeDropdownActive = false;
                destinationDropdownActive = false;
                modeDropdownActive = false;
                roundsDropdownActive = false;
                townGoldDropdownActive = false;
                witchDropdownActive = false;
            } else {
                // disable text input
                nameSel = false;
                if (x >= 533 & x <= 580 && y >= 176 && y <= 216) {
                    // click on Mode dropdown
                    modeDropdownActive = !modeDropdownActive;
                    // close the dropdowns
                    sizeDropdownActive = false;
                    destinationDropdownActive = false;
                    roundsDropdownActive = false;
                    townGoldDropdownActive = false;
                    witchDropdownActive = false;
                } else if (modeDropdownActive) {
                    // click on an option in the mode dropdown
                    if (x >= 270 && x <= 575 && y >= 223 && y <= 260) {
                        // click on Elfenland
                        modeSel = Mode.ELFENLAND;
                        // the number of rounds in elfenland is 3 or 4
                        if (numRoundsSel != 4) {
                            numRoundsSel = 3;
                        }
                        // show the elfenland create game options
                        createGameBackground.draw(elfenlandSelected, 0, 0);
                        // cover the last entry, draw name
                        createGameBackground.draw(nameTextField, 168, 101);
                        MinuetoText name = new MinuetoText(nameString, fontArial20, MinuetoColor.BLACK);
                        createGameBackground.draw(name, 178, 120);
                    } else if (x >= 270 && x <= 575 && y >= 270 && y <= 298) {
                        // click on Elfengold
                        modeSel = Mode.ELFENGOLD;
                        // the number of rounds in elfengold is 6
                        numRoundsSel = 6;
                        // show the elfengold create game options
                        createGameBackground.draw(createGameBackgroundElfengold, 0, 0);
                        // cover the last entry, draw name
                        createGameBackground.draw(nameTextField, 168, 101);
                        MinuetoText name = new MinuetoText(nameString, fontArial20, MinuetoColor.BLACK);
                        createGameBackground.draw(name, 178, 120);
                    }
                    modeDropdownActive = !modeDropdownActive;
                } else if (x >= 186 && x <= 230 && y >= 232 && y <= 278) {
                    // click on Size dropdown
                    sizeDropdownActive = !sizeDropdownActive;
                    // close the dropdowns
                    destinationDropdownActive = false;
                    modeDropdownActive = false;
                    roundsDropdownActive = false;
                    townGoldDropdownActive = false;
                    witchDropdownActive = false;
                } else if (sizeDropdownActive) {
                    // click on an option in the size dropdown
                    if (x >= 144 && x <= 230 && y >= 286 && y <= 328) {
                        // click on 2
                        numberPlayers = 2;
                    } else if (x >= 144 && x <= 230 && y >= 339 && y <= 374) {
                        // click on 3
                        numberPlayers = 3;
                    } else if (x >= 144 && x <= 230 && y >= 381 && y <= 413) {
                        // click on 4
                        numberPlayers = 4;
                    } else if (x >= 144 && x <= 230 && y >= 420 && y <= 458) {
                        // click on 5
                        numberPlayers = 5;
                    } else if (x >= 144 && x <= 230 && y >= 460 && y <= 500) {
                        // click on 6
                        numberPlayers = 6;
                    }
                    sizeDropdownActive = false;
                } else if (x >= 651 && x <= 700 && y >= 307 && y <= 347) {
                    // click on Destination Towns dropdown
                    destinationDropdownActive = !destinationDropdownActive;
                    // close the dropdowns
                    sizeDropdownActive = false;
                    modeDropdownActive = false;
                    roundsDropdownActive = false;
                    townGoldDropdownActive = false;
                    witchDropdownActive = false;
                } else if (destinationDropdownActive) {
                    // click on an option in the destination dropdown
                    if (x >= 373 && x <= 700 && y >= 347 && y <= 393) {
                        // click on No
                        destinationTownSel = false;
                    } else if (x >= 373 && x <= 700 && y >= 395 && y <= 436) {
                        // click on Yes
                        destinationTownSel = true;
                    }
                    destinationDropdownActive = false;
                } else if (modeSel.equals(Mode.ELFENLAND) && x >= 458 && x <= 511 && y >= 378 && y <= 420) {
                    // click on Rounds dropdown (for elfenland only)
                    roundsDropdownActive = !roundsDropdownActive;
                    // close the dropdowns
                    sizeDropdownActive = false;
                    destinationDropdownActive = false;
                    modeDropdownActive = false;
                    townGoldDropdownActive = false;
                    witchDropdownActive = false;
                } else if (roundsDropdownActive) {
                    // click on an option in the rounds dropdown
                    if (x >= 373 && x <= 513 && y >= 422 && y <= 468) {
                        // click on 3
                        numRoundsSel = 3;
                    } else if (x >= 373 && x <= 513 && y >= 468 && y <= 504) {
                        // click on 4
                        numRoundsSel = 4;
                    }
                    roundsDropdownActive = false;
                } else if (modeSel.equals(Mode.ELFENGOLD) && x >= 649 && x <= 700 && y >= 377 && y <= 419) {
                    // click on Town Gold Values dropdown (for elfengold only)
                    townGoldDropdownActive = !townGoldDropdownActive;
                    // close the dropdowns
                    sizeDropdownActive = false;
                    destinationDropdownActive = false;
                    modeDropdownActive = false;
                    roundsDropdownActive = false;
                    witchDropdownActive = false;
                } else if (townGoldDropdownActive) {
                    // click on an option in the town gold dropdown
                    if (x >= 376 && x <= 700 && y >= 423 && y <= 466) {
                        // click on No
                        townGoldOption = TownGoldOption.NO;
                    } else if (x >= 376 && x <= 700 && y >= 446 && y <= 510) {
                        // click on Yes: default
                        townGoldOption = TownGoldOption.YESDEFAULT;
                    } else if (x >= 376 && x <= 700 && y >= 510 && y <= 546) {
                        // click on Yes: random
                        townGoldOption = TownGoldOption.YESRANDOM;
                    }
                    townGoldDropdownActive = false;
                } else if (modeSel.equals(Mode.ELFENGOLD) && x >= 230 && x <= 281 && y >= 442 && y <= 490) {
                    // click on Witch dropdown (for elfengold only)
                    witchDropdownActive = !witchDropdownActive;
                    // close the dropdowns
                    sizeDropdownActive = false;
                    destinationDropdownActive = false;
                    modeDropdownActive = false;
                    roundsDropdownActive = false;
                    townGoldDropdownActive = false;
                } else if (witchDropdownActive) {
                    // click on an option in the witch dropdown
                    if (x >= 169 && x <= 513 && y >= 492 && y <= 535) {
                        // click on No
                        witchSel = false;
                    } else if (x >= 169 && x <= 513 && y >= 535 && y <= 580) {
                        // click on Yes
                        witchSel = true;
                    }
                    witchDropdownActive = false;
                } else if (x >= 310 && x <= 715 && y <= 675 && y >= 605) {
                    // close the dropdowns
                    sizeDropdownActive = false;
                    destinationDropdownActive = false;
                    modeDropdownActive = false;
                    roundsDropdownActive = false;
                    townGoldDropdownActive = false;
                    witchDropdownActive = false;
                    // click on the Create New Game button

                    // CHECK INPUTS HERE
                    boolean canCreate = true;
                    if (nameString.equals("")) {
                        // show error message when the name is empty
                        MinuetoText nameIsEmpty = new MinuetoText("Please enter a name.", fontArial22Bold,
                                MinuetoColor.RED);
                        createGameBackground.draw(nameIsEmpty, 178, 120);
                        canCreate = false;
                    }
                    // END OF CHECK INPUTS

                    // CREATE NEW GAME HERE
                    if (canCreate) { // if nameString is not empty, create a new game

                        try {
                            if (modeSel.equals(Mode.ELFENLAND)) {
                                // create an elfenland game
                                gameToJoin = REGISTRATOR.createGame(nameString, numberPlayers, numRoundsSel,
                                        Mode.ELFENLAND, false, destinationTownSel, TownGoldOption.NO);
                                if (gameToJoin == null) {
                                    // show error message because the game already exists
                                    MinuetoText nameIsTaken = new MinuetoText("Name already taken.", fontArial22Bold,
                                            MinuetoColor.RED);
                                    createGameBackground.draw(nameIsTaken, 178, 120);
                                } else {
                                    // get available boot colors
                                    ACTION_MANAGER.sendActionAndGetReply(new GetAvailableColorsAction(
                                            currentUser.getName(), currentSession.getSessionID()));
                                    gui.currentBackground = GUI.Screen.CHOOSEBOOT;
                                }
                            } else if (modeSel.equals(Mode.ELFENGOLD)) {
                                // create an elfengold game
                                gameToJoin = REGISTRATOR.createGame(nameString, numberPlayers, 6, Mode.ELFENGOLD,
                                        witchSel, destinationTownSel, townGoldOption);
                                if (gameToJoin == null) {
                                    // show error message because the game already exists
                                    MinuetoText nameIsTaken = new MinuetoText("Name already taken.", fontArial22Bold,
                                            MinuetoColor.RED);
                                    createGameBackground.draw(nameIsTaken, 178, 120);
                                } else {
                                    // get available boot colors
                                    ACTION_MANAGER.sendActionAndGetReply(new GetAvailableColorsAction(
                                            currentUser.getName(), currentSession.getSessionID()));
                                    gui.currentBackground = GUI.Screen.CHOOSEBOOT;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (x >= 335 && x <= 690 && y <= 760 && y >= 695) {
                    // click on the Return to Open Lobbies button
                    displayAvailableGames();
                    gui.currentBackground = GUI.Screen.LOBBY;

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

    static MinuetoMouseHandler chooseBootMouseHandler = new MinuetoMouseHandler() {

        @Override
        public void handleMousePress(int x, int y, int button) {
            System.out.println("x: " + x + " y: " + y);
            if (x >= 320 && x <= 705 && y >= 665 && y <= 740) {
                // Click on Confirm

                // check that a color was chosen
                if (colorChosen == null) {
                    // print error message
                    MinuetoText errorText = new MinuetoText("Please select a color.", fontArial22Bold,
                            MinuetoColor.RED);
                    chooseBootBackground.draw(errorText, 378, 526);
                } else {

                    // TODO: add elfengold options

                    if (currentUser.getName().equals(currentSession.getCreator())) {
                        // creator
                        try {
                            // send action to the server
                            String senderName = currentUser.getName();
                            String color = colorChosen.name();
                            String gameID = currentSession.getSessionID();
                            ACTION_MANAGER.sendActionAndGetReply(new ChooseBootColorAction(senderName, color, gameID));
                            // display users
                            displayUsers();
                            System.out.println("displaying users as a creator");
                        } catch (MinuetoFileException e) {
                            e.printStackTrace();
                        }

                        Game game = currentSession.getGame();
                        Mode currentMode = game.getMode();
                        // switch backgrounds depending on the game mode
                        if (currentMode.equals(Mode.ELFENLAND)) {
                            gui.currentBackground = GUI.Screen.LOBBYELFENLANDCREATOR;
                            gui.window.draw(lobbyElfenlandCreatorBackground, 0, 0);
                            gui.window.draw(greyStartButton, 822, 580);
                            gui.window.render();
                            // wait for enough players to join
                            ACTION_MANAGER.waitForPlayersAsCreator();
                            // we arrive here if the session is launchable: then display the launch button
                            lobbyElfenlandCreatorBackground.draw(startButton, 822, 580);
                        } else if (currentMode.equals(Mode.ELFENGOLD)) {
                            gui.currentBackground = GUI.Screen.LOBBYELFENGOLDCREATOR;
                            gui.window.draw(lobbyElfengoldCreatorBackground, 0, 0);
                            gui.window.draw(greyStartButton, 822, 580);
                            gui.window.render();
                            // wait for enough players to join
                            ACTION_MANAGER.waitForPlayersAsCreator();
                            // we arrive here if the session is launchable: then display the launch button
                            lobbyElfengoldCreatorBackground.draw(startButton, 822, 580);
                        }

                    } else {
                        // not the creator

                        try {
                            // join the game
                            gameToJoin.join(colorChosen);
                            currentSession = gameToJoin.getActiveSession();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // change backgrounds
                        gui.currentBackground = GUI.Screen.LOBBYELFENLAND;
                        gui.window.draw(lobbyElfenlandBackground, 0, 0);
                        gui.window.render();

                        // wait for other players (i.e wait for the game to launch)
                        ACTION_MANAGER.waitForPlayers();
                    }

                }
            } else {
                // Click on a Color
                for (AbstractMap.SimpleEntry<ImmutableList, Color> coords : colorButtonCoordinates) {
                    int maxX = (int) coords.getKey().get(0);
                    int minX = (int) coords.getKey().get(1);
                    int maxY = (int) coords.getKey().get(2);
                    int minY = (int) coords.getKey().get(3);

                    if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                        // select the color
                        colorChosen = coords.getValue();
                        System.out.println("chose: " + colorChosen);
                        // display a boppel on the confirm button
                        if (colorChosen.equals(Color.BLACK)) {
                            chooseBootBackground.draw(blackBoppel, 640, 691);
                        } else if (colorChosen.equals(Color.BLUE)) {
                            chooseBootBackground.draw(blueBoppel, 640, 691);
                        } else if (colorChosen.equals(Color.YELLOW)) {
                            chooseBootBackground.draw(yellowBoppel, 640, 691);
                        } else if (colorChosen.equals(Color.GREEN)) {
                            chooseBootBackground.draw(greenBoppel, 640, 691);
                        } else if (colorChosen.equals(Color.PURPLE)) {
                            chooseBootBackground.draw(purpleBoppel, 640, 691);
                        } else if (colorChosen.equals(Color.RED)) {
                            chooseBootBackground.draw(redBoppel, 640, 691);
                        }
                    }
                }
            }
        }

        @Override
        public void handleMouseMove(int x, int y) {
            // TODO Auto-generated method stub

        }

        @Override
        public void handleMouseRelease(int x, int y, int button) {
            // TODO Auto-generated method stub

        }

    };

    static MinuetoMouseHandler elfenLandLobbyMouseHandler = new MinuetoMouseHandler() {
        @Override
        public void handleMousePress(int x, int y, int button) {
            System.out.println("x: " + x + "y: " + y);

            if (!currentUser.getName().equals(currentSession.getCreator())) {
                /*
                 * // for users that are not the creator
                 * if (x >= 825 && x <= 1000 && y >= 675 && y <= 735) {
                 * // click on Leave
                 * REGISTRATOR.leaveGame(currentSession, currentUser);
                 * // return to lobby screen
                 * displayAvailableGames();
                 * gui.currentBackground = GUI.Screen.LOBBY;
                 * } else if (x >= 822 & x <= 998 && y <= 655 && y >= 585) {
                 * // click on Ready button: only works if you are not ready, else nothing
                 * happens
                 * if (!currentUser.isReady()) {
                 * // set user to ready
                 * currentUser.toggleReady();
                 * // draw the green ready image
                 * lobbyElfenlandBackground.draw(readyGreen, 823, 581);
                 * // TODO: display Ready next to the player's name
                 * // TODO: notify all players that this player is ready
                 * }
                 * }
                 */
            } else {
                // for the creator
                if (currentSession.isLaunchable() && x >= 825 && x <= 1000 && y >= 580 && y <= 735) {
                    // click on Launch button -> launch the session
                    REGISTRATOR.launchSession(currentSession, currentUser);
                }
            }

            if (x >= 710 && x <= 800 && y >= 700 && y <= 735) {
                // click on Send Message button
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

    private static boolean nameSel = false;
    private static String nameString = "";
    private static int numberPlayers = 6;
    private static Mode modeSel = Mode.ELFENLAND;
    private static boolean destinationTownSel = false;
    private static int numRoundsSel = 3;
    private static TownGoldOption townGoldOption = TownGoldOption.NO;
    private static boolean witchSel = false;
    private static boolean modeDropdownActive = false;
    private static boolean sizeDropdownActive = false;
    private static boolean destinationDropdownActive = false;
    private static boolean roundsDropdownActive = false;
    private static boolean witchDropdownActive = false;
    private static boolean townGoldDropdownActive = false;
    private static LobbyServiceGameSession gameToJoin;

    // create window that will contain our game - stays in Main (or not lol)
    public static final MinuetoWindow WINDOW = new MinuetoFrame(1024, 768, true);;

    // for lobbyMouseHandler
    private static ArrayList<AbstractMap.SimpleEntry<ImmutableList, LobbyServiceGameSession>> joinButtonCoordinates = new ArrayList<>();

    // for chooseBootMouseHandler
    private static ArrayList<AbstractMap.SimpleEntry<ImmutableList, Color>> colorButtonCoordinates = new ArrayList<>();
    private static Color colorChosen;

    // ******************************************MAIN CODE STARTS
    // HERE********************************************
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

        try {
            elfengoldImage = new MinuetoImageFile("images/elfengold.png");
            elfenlandImage = new MinuetoImageFile("images/elfenland.png");
            playScreenImage = new MinuetoImageFile("images/play.png");
            loginScreenImage = new MinuetoImageFile("images/login.png");
            whiteBoxImage = new MinuetoRectangle(470, 50, MinuetoColor.WHITE, true);
            // choose boot
            chooseBootBackground = new MinuetoImageFile("images/choose-boot-screen.png");
            redBoppel = new MinuetoImageFile("images/böppels-and-boots/böppel-red.png");
            blueBoppel = new MinuetoImageFile("images/böppels-and-boots/böppel-blue.png");
            greenBoppel = new MinuetoImageFile("images/böppels-and-boots/böppel-green.png");
            blackBoppel = new MinuetoImageFile("images/böppels-and-boots/böppel-black.png");
            yellowBoppel = new MinuetoImageFile("images/böppels-and-boots/böppel-yellow.png");
            purpleBoppel = new MinuetoImageFile("images/böppels-and-boots/böppel-purple.png");
            // lobby
            lobbyBackground = new MinuetoImageFile("images/open-lobbies.png");
            lobbyElfenlandBackground = new MinuetoImageFile("images/game-lobby-elfenland.png");
            lobbyElfengoldBackground = new MinuetoImageFile("images/game-lobby-elfengold.png");
            lobbyElfenlandCreatorBackground = new MinuetoImageFile("images/game-lobby-elfenland-creator.png");
            lobbyElfengoldCreatorBackground = new MinuetoImageFile("images/game-lobby-elfengold-creator.png");
            readyGreen = new MinuetoImageFile("images/ready-button-green.png");
            readyWhite = new MinuetoImageFile("images/ready-button-white.png");
            startButton = new MinuetoImageFile("images/blue-launch-button.png");
            greyStartButton = new MinuetoImageFile("images/grey-launch-button.png");
            // Create Game
            createGameBackground = new MinuetoImageFile("images/create-game-elfenland.png");
            createGameBackgroundElfengold = new MinuetoImageFile("images/create-game-elfengold.png");
            nameTextField = new MinuetoRectangle(412, 44, MinuetoColor.WHITE, true);
            elfenlandSelected = new MinuetoImageFile("images/create-game-elfenland.png");
            elfenGoldSelected = new MinuetoImageFile("images/create-game-elfengold.png");
            // Create Game dropdowns
            fontArial22Bold = new MinuetoFont("Arial", 22, true, false);
            modeDropdownRectangle = new MinuetoRectangle(313, 84, MinuetoColor.WHITE, true);
            sizeDropdownRectangle = new MinuetoRectangle(91, 225, MinuetoColor.WHITE, true);
            destinationTownDropdownRectangle = new MinuetoRectangle(324, 88, MinuetoColor.WHITE, true);
            roundsDropdownRectangle = new MinuetoRectangle(137, 86, MinuetoColor.WHITE, true);
            townGoldDropdownRectangle = new MinuetoRectangle(326, 129, MinuetoColor.WHITE, true);
            witchDropdownRectangle = new MinuetoRectangle(114, 92, MinuetoColor.WHITE, true);
            modeElfenlandText = new MinuetoText("Elfenland", fontArial22Bold, MinuetoColor.BLACK);
            modeElfengoldText = new MinuetoText("Elfengold", fontArial22Bold, MinuetoColor.BLACK);
            destinationTownNoText = new MinuetoText("No", fontArial22Bold, MinuetoColor.BLACK);
            destinationTownYesText = new MinuetoText("Yes", fontArial22Bold, MinuetoColor.BLACK);
            rounds3Text = new MinuetoText("3", fontArial22Bold, MinuetoColor.BLACK);
            rounds4Text = new MinuetoText("4", fontArial22Bold, MinuetoColor.BLACK);
            size2Text = new MinuetoText("2", fontArial22Bold, MinuetoColor.BLACK);
            size3Text = new MinuetoText("3", fontArial22Bold, MinuetoColor.BLACK);
            size4Text = new MinuetoText("4", fontArial22Bold, MinuetoColor.BLACK);
            size5Text = new MinuetoText("5", fontArial22Bold, MinuetoColor.BLACK);
            size6Text = new MinuetoText("6", fontArial22Bold, MinuetoColor.BLACK);
            townGoldNoText = new MinuetoText("No", fontArial22Bold, MinuetoColor.BLACK);
            townGoldYesText = new MinuetoText("Yes: default values", fontArial22Bold, MinuetoColor.BLACK);
            townGoldYesRandText = new MinuetoText("Yes: random values", fontArial22Bold, MinuetoColor.BLACK);
            witchNoText = new MinuetoText("No", fontArial22Bold, MinuetoColor.BLACK);
            witchYesText = new MinuetoText("Yes", fontArial22Bold, MinuetoColor.BLACK);
            // mute button
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

        gui = new GUI(WINDOW, GUI.Screen.MENU);
        WINDOW.setMaxFrameRate(60);

        // make window visible
        gui.window.setVisible(true);

        // create entry screen mouse handler
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

        // mouse handler for choose boot
        chooseBootQueue = new MinuetoEventQueue();
        gui.window.registerMouseHandler(chooseBootMouseHandler, chooseBootQueue);

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

            } else if (gui.currentBackground == GUI.Screen.CREATELOBBY) {
                gui.window.draw(createGameBackground, 0, 0);
                while (createGameQueue.hasNext()) {
                    createGameQueue.handle();
                }

                // display current Mode
                if (modeSel.equals(Mode.ELFENLAND)) {
                    gui.window.draw(modeElfenlandText, 285, 180);
                    // display current rounds option
                    if (numRoundsSel == 3) {
                        gui.window.draw(rounds3Text, 384, 388);
                    } else if (numRoundsSel == 4) {
                        gui.window.draw(rounds4Text, 384, 388);
                    }
                } else if (modeSel.equals(Mode.ELFENGOLD)) {
                    gui.window.draw(modeElfengoldText, 285, 180);
                    // display current Town Gold option
                    if (townGoldOption.equals(TownGoldOption.NO)) {
                        gui.window.draw(townGoldNoText, 388, 389);
                    } else if (townGoldOption.equals(TownGoldOption.YESDEFAULT)) {
                        gui.window.draw(townGoldYesText, 388, 389);
                    } else if (townGoldOption.equals(TownGoldOption.YESRANDOM)) {
                        gui.window.draw(townGoldYesRandText, 388, 389);
                    }
                    // display current Witch option
                    if (witchSel) {
                        gui.window.draw(witchYesText, 178, 458);
                    } else {
                        gui.window.draw(witchNoText, 178, 458);
                    }
                }

                // display current size
                if (numberPlayers == 2) {
                    gui.window.draw(size2Text, 152, 240);
                } else if (numberPlayers == 3) {
                    gui.window.draw(size3Text, 152, 240);
                } else if (numberPlayers == 4) {
                    gui.window.draw(size4Text, 152, 240);
                } else if (numberPlayers == 5) {
                    gui.window.draw(size5Text, 152, 240);
                } else if (numberPlayers == 6) {
                    gui.window.draw(size6Text, 152, 240);
                }

                // display current destination option
                if (destinationTownSel) {
                    gui.window.draw(destinationTownYesText, 389, 310);
                } else {
                    gui.window.draw(destinationTownNoText, 389, 310);
                }

                // display dropdowns
                if (modeDropdownActive) {
                    gui.window.draw(modeDropdownRectangle, 268, 215);
                    gui.window.draw(modeElfenlandText, 285, 231);
                    gui.window.draw(modeElfengoldText, 285, 273);
                } else if (sizeDropdownActive) {
                    gui.window.draw(sizeDropdownRectangle, 142, 275);
                    gui.window.draw(size2Text, 152, 300);
                    gui.window.draw(size3Text, 152, 342);
                    gui.window.draw(size4Text, 152, 384);
                    gui.window.draw(size5Text, 152, 426);
                    gui.window.draw(size6Text, 152, 468);
                } else if (destinationDropdownActive) {
                    gui.window.draw(destinationTownDropdownRectangle, 376, 347);
                    gui.window.draw(destinationTownNoText, 389, 359);
                    gui.window.draw(destinationTownYesText, 389, 400);
                } else if (roundsDropdownActive) {
                    gui.window.draw(roundsDropdownRectangle, 375, 418);
                    gui.window.draw(rounds3Text, 384, 435);
                    gui.window.draw(rounds4Text, 384, 477);
                } else if (townGoldDropdownActive) {
                    gui.window.draw(townGoldDropdownRectangle, 375, 418);
                    gui.window.draw(townGoldNoText, 388, 433);
                    gui.window.draw(townGoldYesText, 388, 475);
                    gui.window.draw(townGoldYesRandText, 388, 517);
                } else if (witchDropdownActive) {
                    gui.window.draw(witchDropdownRectangle, 168, 487);
                    gui.window.draw(witchNoText, 178, 500);
                    gui.window.draw(witchYesText, 178, 542);
                }

            } else if (gui.currentBackground == GUI.Screen.CHOOSEBOOT) {
                gui.window.draw(chooseBootBackground, 0, 0);
                while (chooseBootQueue.hasNext()) {
                    chooseBootQueue.handle();
                }

            } else if (gui.currentBackground == GUI.Screen.LOBBYELFENLAND) {
                gui.window.draw(lobbyElfenlandBackground, 0, 0);
                while (elfenlandLobbyQueue.hasNext()) {
                    elfenlandLobbyQueue.handle();
                }

            } else if (gui.currentBackground == GUI.Screen.LOBBYELFENGOLD) {
                gui.window.draw(lobbyElfengoldBackground, 0, 0);
                while (elfenlandLobbyQueue.hasNext()) {
                    elfenlandLobbyQueue.handle();
                }
            } else if (gui.currentBackground == GUI.Screen.LOBBYELFENLANDCREATOR) {
                gui.window.draw(lobbyElfenlandCreatorBackground, 0, 0);
                while (elfenlandLobbyQueue.hasNext()) {
                    elfenlandLobbyQueue.handle();
                }
            } else if (gui.currentBackground == GUI.Screen.LOBBYELFENGOLDCREATOR) {
                gui.window.draw(lobbyElfengoldCreatorBackground, 0, 0);
                while (elfenlandLobbyQueue.hasNext()) {
                    elfenlandLobbyQueue.handle();
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
            if (soundOn) {
                gui.window.draw(soundOffButton, 1000, 745);
            } else {
                gui.window.draw(soundOnButton, 1000, 745);
            }

            WINDOW.render();
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
            loadedClip = AudioSystem.getClip();
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

    public static void displayColors(ArrayList<String> colors) throws MinuetoFileException {
        int counter = 0; // how many colors are displayed so far

        System.out.println(colors.toString());

        for (String colorString : colors) {
            // get the image + enum
            MinuetoImage boot;
            Color c;
            if (colorString.equals("BLUE")) {
                boot = new MinuetoImageFile("images/choose-boot-blue.png");
                c = Color.BLUE;
            } else if (colorString.equals("BLACK")) {
                boot = new MinuetoImageFile("images/choose-boot-black.png");
                c = Color.BLACK;
            } else if (colorString.equals("RED")) {
                boot = new MinuetoImageFile("images/choose-boot-red.png");
                c = Color.RED;
            } else if (colorString.equals("YELLOW")) {
                boot = new MinuetoImageFile("images/choose-boot-yellow.png");
                c = Color.YELLOW;
            } else if (colorString.equals("GREEN")) {
                boot = new MinuetoImageFile("images/choose-boot-green.png");
                c = Color.GREEN;
            } else if (colorString.equals("PURPLE")) {
                boot = new MinuetoImageFile("images/choose-boot-purple.png");
                c = Color.PURPLE;
            } else {
                // just for the compiler
                boot = null;
                c = null;
            }

            // display the boot
            chooseBootBackground.draw(boot, 75 + counter * 150, 300);

            // keep track of the button location
            Integer maxX = 150 + (counter * 150);
            Integer minX = 75 + (counter * 150);
            Integer maxY = 410;
            Integer minY = 300;
            ImmutableList<Integer> listOfCoordinates = ImmutableList.of(maxX, minX, maxY, minY);
            AbstractMap.SimpleEntry<ImmutableList, Color> entry = new AbstractMap.SimpleEntry<>(
                    listOfCoordinates, c);
            colorButtonCoordinates.add(entry);

            counter++;
        }
    }

    public static void displayOriginalBoard() {
        // display background depending on the mode
        LobbyServiceGame gameService = currentSession.getGameService();
        Game game = gameService.getGame();
        Mode currentMode = game.getMode();
        if (currentMode.equals(Mode.ELFENLAND)) {
            gui.currentBackground = GUI.Screen.ELFENLAND;
        } else if (currentMode.equals(Mode.ELFENGOLD)) {
            gui.currentBackground = GUI.Screen.ELFENGOLD;
        }
    }

    public static void displayUsers() throws MinuetoFileException {
        MinuetoFont font = new MinuetoFont("Arial", 22, true, false);
        ArrayList<User> users = currentSession.getUsers();

        MinuetoImage background = null;
        Game game = currentSession.getGame();
        Mode currentMode = game.getMode();
        if (currentMode.equals(Mode.ELFENLAND)) {
            if (currentUser.getName().equals(currentSession.getCreator())) {
                background = lobbyElfenlandCreatorBackground;
            } else {
                background = lobbyElfenlandBackground;
            }
        } else if (currentMode.equals(Mode.ELFENGOLD)) {
            if (currentUser.getName().equals(currentSession.getCreator())) {
                background = lobbyElfengoldCreatorBackground;
            } else {
                background = lobbyElfengoldBackground;
            }
        }

        int counter = 0; // how many users are displayed so far

        for (User u : users) {
            String name = u.getName();
            if (name.equals(currentUser.getName())) {
                name = name + " (you)";
            }
            // truncate names that are too long
            if (name.length() > 15) {
                if (name.equals(currentUser.getName())) {
                    name = name.substring(0, 15) + "..." + " (you)";
                } else {
                    name = name.substring(0, 15) + "...";
                }

            }

            Color color = u.getColor();
            boolean ready = u.isReady();

            MinuetoText uName = new MinuetoText(name, font, MinuetoColor.BLACK);

            MinuetoImage uColor = null;
            MinuetoText uColorText = null;
            if (color == null) {
                uColorText = new MinuetoText("?", font, MinuetoColor.BLACK);
            } else {
                if (color.equals(Color.BLACK)) {
                    uColor = blackBoppel;
                } else if (color.equals(Color.RED)) {
                    uColor = redBoppel;
                } else if (color.equals(Color.BLUE)) {
                    uColor = blueBoppel;
                } else if (color.equals(Color.GREEN)) {
                    uColor = greenBoppel;
                } else if (color.equals(Color.YELLOW)) {
                    uColor = yellowBoppel;
                } else if (color.equals(Color.PURPLE)) {
                    uColor = purpleBoppel;
                }
            }

            MinuetoText uReady;
            if (ready) {
                uReady = new MinuetoText("Ready", font, MinuetoColor.GREEN);
            } else {
                uReady = new MinuetoText("Not ready", font, MinuetoColor.BLACK);
            }

            background.draw(uName, 45, 240 + counter * 50);
            if (uColor == null) {
                background.draw(uColorText, 290, 240 + counter * 50);
            } else {
                background.draw(uColor, 290, 240 + counter * 50);
            }
            background.draw(uReady, 475, 240 + counter * 50);

            counter++;
        }

        gui.window.draw(background, 0, 0);
    }

    public static void displayAvailableGames() {
        MinuetoFont font = new MinuetoFont("Arial", 22, true, false);
        try {
            ArrayList<LobbyServiceGame> availableGamesList = Registrator.getAvailableGames();
            ArrayList<LobbyServiceGameSession> availableSessionsList = Registrator.getAvailableSessions();

            int nbAvailableGameServices = availableGamesList.size();
            int nbAvailableGameSessions = availableSessionsList.size();

            if (nbAvailableGameSessions == 0 && nbAvailableGameServices == 0) {
                MinuetoText noneAvailableText = new MinuetoText(
                        "There are no games yet. Please refresh or create a new game.", font, MinuetoColor.BLACK);
                lobbyBackground.draw(noneAvailableText, 200, 340);
            }

            // display next button
            if (nbAvailableGameServices + nbAvailableGameSessions > 9) {
                MinuetoImage nextButton = new MinuetoImageFile("images/next-button.png");
                lobbyBackground.draw(nextButton, 700, 676);
            }

            int totalCounter = 0; // how many games are displayed so far
            int pageCounter = 0; // how many games are displayed on one page so far

            // display available game sessions (i.e. games with a creator)
            for (LobbyServiceGameSession gs : availableSessionsList) {
                if (!gs.isLaunched()) { // only show unlaunched sessions
                    String gsName = gs.getGameService().getDisplayName();
                    String gsCreator = gs.getCreator();
                    String gsCurrentPlayerNumber = String.valueOf(gs.getNumberOfUsersCurrently());
                    String gsMaxPlayerNumber = String.valueOf(gs.getGameService().getNumberOfUsers());

                    MinuetoText displayName = new MinuetoText(gsName, font, MinuetoColor.BLACK);
                    MinuetoText creator = new MinuetoText(gsCreator, font, MinuetoColor.BLACK);
                    MinuetoText size = new MinuetoText(gsCurrentPlayerNumber + "/" + gsMaxPlayerNumber, font,
                            MinuetoColor.BLACK);
                    MinuetoRectangle joinButton = new MinuetoRectangle(100, 35, MinuetoColor.WHITE, true);
                    MinuetoText joinText = new MinuetoText("JOIN", font, MinuetoColor.BLACK);

                    lobbyBackground.draw(displayName, 65, 215 + (pageCounter * 50));
                    lobbyBackground.draw(creator, 350, 215 + (pageCounter * 50));
                    lobbyBackground.draw(size, 655, 215 + (pageCounter * 50));
                    lobbyBackground.draw(joinButton, 835, 210 + (pageCounter * 50));
                    lobbyBackground.draw(joinText, 855, 215 + (pageCounter * 50));

                    // keep track of the button location
                    Integer maxX = 935;
                    Integer minX = 835;
                    Integer maxY = 245 + (pageCounter * 50);
                    Integer minY = 210 + (pageCounter * 50);
                    ImmutableList<Integer> listOfCoordinates = ImmutableList.of(maxX, minX, maxY, minY);
                    AbstractMap.SimpleEntry<ImmutableList, LobbyServiceGameSession> entry = new AbstractMap.SimpleEntry<>(
                            listOfCoordinates, gs);
                    joinButtonCoordinates.add(entry);

                    pageCounter++;
                    totalCounter++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void recievePhaseOne(String playerID, ArrayList<String> cardArray){
        for (Player p: players){
            if (p.getName().equalsIgnoreCase(playerID)){
                p.addCardStringArray(cardArray);
            }
        }
    }
}
