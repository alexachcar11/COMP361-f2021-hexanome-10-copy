
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
import java.io.BufferedReader;
import java.io.File;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

// json
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class ClientMain {

    private static boolean userNameSel = false;
    private static boolean passWordSel = false;
    private static String userString = "";
    private static String passString = "";

    public static void main(String[] args) {

        File bootDir = new File("images/böppels-and-boots/"); // dir containing boot image files
        List<String> bootFileNames = new ArrayList<>();
        // add file names of boot images to the bootFiles list
        for (File file : bootDir.listFiles()) {
            if (file.getName().startsWith("boot-"))
                bootFileNames.add("images/böppels-and-boots/" + file.getName());
        }

        // make images
        MinuetoImage elfenlandImage;
        MinuetoImage elfengoldImage;
        List<MinuetoImage> bootImages = getBootImages(bootFileNames);
        MinuetoImage playScreenImage;
        MinuetoImage loginScreenImage;
        MinuetoImage whiteBoxImage;
        MinuetoRectangle lobbyBackground;

        configImages(bootImages);
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
        playSound("music/flute.mid");

        // create players
        List<Player> players = new ArrayList<>();
        // Player p1 = new Player(null, Color.YELLOW);
        // Player p2 = new Player(null, Color.BLACK);
        // players.add(p1);
        // players.add(p2);

        // create window that will contain our game
        MinuetoWindow window = new MinuetoFrame(1024, 768, true);
        GUI gui = new GUI(window, GUI.Screen.MENU);
        window.setMaxFrameRate(60);

        // make window visible
        gui.window.setVisible(true);

        // create entry screen mouse handler
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
                        getAvailableGames(); // i don't know what the difference is exactly. only know that session has
                                             // more info in the API
                        getAvailableSessions();
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }

                    // TODO: place buildDisplay() here
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
                        gui.currentBackground = GUI.Screen.ELFENGOLD;
                        try {
                            /*
                             * create OAuth2 token for login
                             */
                            URL url = new URL(
                                    "http://127.0.0.1:4242/oauth/token?grant_type=password&username=maex&password=abc123_ABC123");
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.setRequestMethod("POST");

                            /* Payload support */
                            con.setDoOutput(true);
                            DataOutputStream out = new DataOutputStream(con.getOutputStream());
                            out.writeBytes(
                                    "user_oauth_approval=true&_csrf=19beb2db-3807-4dd5-9f64-6c733462281b&authorize=true");
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

                            /*
                             * add a new user to the API
                             */
                            // URL userUrl = new URL(
                            // "http://127.0.0.1:4242/api/users/foobar?access_token=" + content.toString());
                            // HttpURLConnection userCon = (HttpURLConnection) userUrl.openConnection();
                            // userCon.setRequestMethod("PUT");
                            // userCon.setRequestProperty("Content-Type", "application/json");

                            // /* Payload support */
                            // userCon.setDoOutput(true);
                            // DataOutputStream userOut = new DataOutputStream(userCon.getOutputStream());
                            // userOut.writeBytes("{\n");
                            // // make the actual username
                            // userOut.writeBytes(" \"name\": \"" + userString + "\",\n");
                            // userOut.writeBytes(" \"password\": \"" + passString + "\",\n");
                            // userOut.writeBytes(" \"preferredColour\": \"01FFFF\",\n"); // need to update
                            // when we know
                            // // color
                            // userOut.writeBytes(" \"role\": \"ROLE_PLAYER\"\n");
                            // userOut.writeBytes("}");
                            // userOut.flush();
                            // userOut.close();

                            // int userStatus = userCon.getResponseCode();
                            // BufferedReader userIn = new BufferedReader(new
                            // InputStreamReader(userCon.getInputStream()));
                            // String userInputLine;
                            // StringBuffer userContent = new StringBuffer();
                            // while ((userInputLine = userIn.readLine()) != null) {
                            // userContent.append(userInputLine);
                            // }
                            // userIn.close();
                            // con.disconnect();
                            // System.out.println("Response status: " + userStatus);
                            // System.out.println(userContent.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Error: failed to login a user.");
                        }
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

        // create move boot mouse handler
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
                // intervals of time. Then buildDisplay() would be somwhere else?

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

    /*
     * @pre: pNames is a list of filenames of the boot images
     * 
     * @return: List of images corresponding to the filenames
     */
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

    /*
     * @pre: pImages is a list of MinuetoImages which are boots
     * 
     * @post: contents of pImages are changed to be centered at starting town on
     * game window, and rotated and sized properly
     */
    private static void configImages(List<MinuetoImage> pImages) {
        for (int i = 0; i < pImages.size(); i++) {
            pImages.set(i, pImages.get(i).rotate(-90));
            pImages.set(i, pImages.get(i).scale(.125, .125));
        }
    }

    /**
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

    public static ArrayList<LobbyServiceGameSession> getAvailableSessions() throws IOException, ParseException {
        URL url = new URL("http://127.0.0.1:4242/api/sessions");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        // TESTING CODE START
        System.out.println("Response status: " + status);
        System.out.println(content.toString());
        // TESTING CODE END

        // convert GET output to JSON
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(String.valueOf(content));
        JSONObject jsonObject = (JSONObject) obj;

        ArrayList<LobbyServiceGameSession> availableSessions = new ArrayList<>();

        try {
            // i dont know if this works because I have no way to test it yet (can't create
            // a game)
            JSONObject sessions = (JSONObject) jsonObject.get("sessions");
            sessions.keySet().forEach(sessionID -> {
                JSONObject sessionJSON = (JSONObject) sessions.get(sessionID);

                String creator = (String) sessionJSON.get("creator");
                boolean launched = (boolean) sessionJSON.get("launched");
                String saveGameID = (String) sessionJSON.get("savegameid");

                // Object gameParameters = sessionJSON.get("gameParameters");

                String playerListInStringForm = (String) sessionJSON.get("players");
                playerListInStringForm = playerListInStringForm.replace("[", "");
                playerListInStringForm = playerListInStringForm.replace("]", "");
                String[] playerListInArrayForm = playerListInStringForm.split(",");
                ArrayList<String> playerNames = (ArrayList<String>) Arrays.asList(playerListInArrayForm);

                availableSessions.add(new LobbyServiceGameSession(launched, playerNames, saveGameID));

            });
        } catch (NullPointerException e) {
            // there are no available sessions
            System.out.println("there are no sessions");
        }
        return availableSessions;
    }

    /**
     * Returns the list of available games on the Lobby Service
     * 
     * @return arrayList of LobbyServiceGame objects
     * @throws IOException
     * @throws ParseException
     */
    public static ArrayList<LobbyServiceGame> getAvailableGames() throws IOException, ParseException {
        URL url = new URL("http://127.0.0.1:4242/api/gameservices");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        // TESTING CODE START
        System.out.println("Response status: " + status);
        System.out.println(content.toString());
        // TESTING CODE END

        // convert GET output to JSON
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(String.valueOf(content));
        JSONArray jsonArray = (JSONArray) obj;

        // make a list of available games
        ArrayList<LobbyServiceGame> availableGames = new ArrayList<>(jsonArray.size());

        // add unloaded games (i.e. LobbyServiceGame)
        for (Object lsGameJson : jsonArray) {
            JSONObject lsGame = (JSONObject) lsGameJson;

            // get game name
            String name = (String) lsGame.get("name");

            // get game-service json
            JSONObject gameJson = getLSGameInfo(name);

            // get game-service info from the json
            String displayName = (String) gameJson.get("displayName");
            String location = (String) gameJson.get("location");
            int numberOfPlayers = (int) gameJson.get("numberOfPlayers");

            // create LobbyServiceGame and add it to availableGames list
            availableGames.add(new LobbyServiceGame(displayName, location, numberOfPlayers));
        }

        return availableGames;
    }

    /**
     * Returns details on a previously registered Game-Service
     * 
     * @return JSONObject representing the game-service
     * @throws IOException
     */
    public static JSONObject getLSGameInfo(String name) throws IOException, ParseException {
        URL url = new URL("curl -X GET http://127.0.0.1:4242/api/gameservices/" + name);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        // TESTING CODE START
        System.out.println("Response status: " + status);
        System.out.println(content.toString());
        // TESTING CODE END

        // convert GET output to JSON
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(String.valueOf(content));

        return (JSONObject) obj;
    }
}
