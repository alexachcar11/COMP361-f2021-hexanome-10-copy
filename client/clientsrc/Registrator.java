package clientsrc;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import networksrc.CreateNewGameAction;
import networksrc.GetGameInfoACK;
import networksrc.GetGameInfoAction;
import networksrc.LaunchGameAction;
// import serversrc.Mode;
// import serversrc.TownGoldOption;
import unirest.shaded.com.google.gson.Gson;

/*
* Singleton
*/

public class Registrator {

    private JSONObject currentTokenJSON;
    private static final Registrator INSTANCE = new Registrator();
    private static final String encoded = Base64.getEncoder()
            .encodeToString(("bgp-client-name:bgp-client-pw").getBytes(StandardCharsets.UTF_8)); // Java 8
    private static final JSONParser parser = new JSONParser();
    private static final Gson GSON = new Gson();

    private Registrator() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    currentTokenJSON = createToken("maex", "abc123_ABC123");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }, 0, 1790 * 1000);
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    currentTokenJSON = refreshToken(currentTokenJSON);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }, 590 * 1000, 590 * 1000);
    }

    public static Registrator instance() {
        return INSTANCE;
    }

    /**
     * Creates a new access token for an admin.
     * 
     * @throws ParseException
     */
    public JSONObject createToken(String username, String password) throws ParseException {

        username = username.replace("+", "%2B");
        password = password.replace("+", "%2B");

        HttpResponse<String> jsonResponse = Unirest
                .post("http://elfenland.simui.com:4242/oauth/token?grant_type=password&username="
                        + username
                        + "&password=" + password)
                .header("Authorization", "Basic " + encoded)
                .asString();

        if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": could not create access token.");
        }

        JSONObject token = (JSONObject) parser.parse(jsonResponse.getBody());

        System.out.println(token.toString());

        return token;
    }

    /**
     * Refreshes the access token and set currentAccessToken to this new token
     * 
     * @throws ParseException
     * @return JSONObject containing the body of a post request creating an access
     *         token
     */
    public JSONObject refreshToken(JSONObject existingToken) throws ParseException {

        String refreshToken = (String) existingToken.get("refresh_token");
        refreshToken = refreshToken.replace("+", "%2B");

        HttpResponse<String> jsonResponse = Unirest
                .post("http://elfenland.simui.com:4242/oauth/token?grant_type=refresh_token&refresh_token="
                        + refreshToken)
                .header("Authorization", "Basic " + encoded)
                .asString();

        if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": cannot refresh token.");
        }
        return (JSONObject) parser.parse(jsonResponse.getBody());
    }

    /**
     * Revoke token
     */
    public static void revokeToken() {
        // TODO
    }

    /**
     * Takes currentTokenJSON and returns its string value
     * 
     * @return current token in String format
     */
    public String getToken() {
        return (String) this.currentTokenJSON.get("access_token");
    }

    /**
     * 
     * Create a new user on the LS and create a new User instance.
     * 
     * @param userName username of the user
     * @param passWord password of the user
     * @return User instance that is created upon API request success
     */
    public User createNewUser(String userName, String passWord) {
        /*
         * add a new user to the API
         */
        Map<String, Object> fields = new HashMap<>();
        fields.put("name", userName);
        fields.put("password", passWord);
        fields.put("preferredColour", "01FFFF");
        fields.put("role", "ROLE_PLAYER");

        String encoded = Base64.getEncoder()
                .encodeToString(("bgp-client-name:bgp-client-pw").getBytes(StandardCharsets.UTF_8)); // Java 8

        userName = userName.replace("+", "%2B");
        passWord = passWord.replace("+", "%2B");

        HttpResponse<String> jsonResponse = Unirest
                .put("http://elfenland.simui.com:4242/api/users/" + userName + "?access_token="
                        + this.getToken().replace("+", "%2B"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + encoded)
                .body(GSON.toJson(fields)).asString();
        if (jsonResponse.getStatus() != 200) {
            System.out.println(jsonResponse.getStatus());
            throw new IllegalArgumentException("Cannot create user: " + userName);
        } else {
            System.out.println("BODY" + jsonResponse.getBody());
            // System.out.println(token);
            return new User(userName, passWord);
        }
    }

    // get user from API by username
    public JSONObject getUser(String userName) throws ParseException {

        userName = userName.replace("+", "%2B");

        HttpResponse<String> jsonResponse = Unirest
                .get("http://elfenland.simui.com:4242/api/users/" + userName + "?access_token="
                        + this.getToken().replace("+", "%2B"))
                .header("Authorization", "Basic " + encoded)
                .asString();
        if (jsonResponse.getStatus() != 200) {
            throw new IllegalArgumentException("Unable to retrieve user: " + userName);
        }

        return (JSONObject) parser.parse(jsonResponse.getBody());
    }

    public JSONArray getAllUsers() throws ParseException {

        HttpResponse<String> jsonResponse = Unirest
                .get("http://elfenland.simui.com:4242/api/users?access_token=" + this.getToken().replace("+", "%2B"))
                .header("Authorization", "Basic " + encoded).asString();

        if (jsonResponse.getStatus() != 200) {
            throw new RuntimeException("Error" + jsonResponse.getStatus() + ": unable to retrieve users.");
        }

        return (JSONArray) parser.parse(jsonResponse.getBody());
    }

    /**
     * Operation: Elfen::createNewGame(numberOfPlayers: int, numGameRounds: int,
     * mode: Mode, witchEnabled: boolean, destinationTownEnabled: boolean)
     * Scope: Game;
     * New: newGame: Game;
     * Messages: User:: {gameCreationFailed_e; newGameState}
     * Post: Sends a new game state to the user upon success. in case the game is
     * not successfully created, the operation outputs an “gameCreationFailed_e”
     * message to the user.
     * 
     * @param displayName            name of the game
     * @param numberOfPlayers        exact number of players required to play this
     *                               game. it must be between 2 and 6
     * @param numberOfRounds         number of rounds this game will have
     * @param mode                   elfenland or elfengold
     * @param witchEnabled           true if the witch can be used, false otherwise
     * @param destinationTownEnabled true if players will have a destination town,
     *                               false otherwise
     */
    public LobbyServiceGameSession createGame(String displayName, int numberOfPlayers, int numberOfRounds, Mode mode,
                                       boolean witchEnabled, boolean destinationTownEnabled, TownGoldOption townGoldOption) throws ParseException {

        HttpResponse<String> jsonToken = Unirest
                .post("http://elfenland.simui.com:4242/oauth/token?grant_type=password&username=service&password=abc123_ABC123")
                .header("Authorization", "Basic " + encoded)
                .asString();

        JSONObject token = (JSONObject) parser.parse(jsonToken.getBody());
        System.out.println("service" + token);

        String name = displayName.replace(" ", "");
        String location = "http://elfenland.simui.com:4243/" + name;

        Map<String, Object> fields = new HashMap<>();
        fields.put("location", location);
        fields.put("maxSessionPlayers", numberOfPlayers);
        fields.put("minSessionPlayers", numberOfPlayers);
        fields.put("name", name);
        fields.put("displayName", displayName);
        fields.put("webSupport", "false");

        String tokenString = (String) token.get("access_token");
        tokenString = tokenString.replace("+", "%2B");

        // lobby service location url
        String lobbyServiceURL = "http://elfenland.simui.com:4242/api/gameservices/" + name + "?access_token="
                + tokenString;
        System.out.println(lobbyServiceURL);

        // build request
        HttpResponse<String> jsonResponse = Unirest
                .put(lobbyServiceURL)
                .header("Authorization", "Basic " + encoded)
                .header("Content-Type", "application/json")
                .body(GSON.toJson(fields)).asString();

        LobbyServiceGameSession newSessionCreated = null;

        // verify response
        if (jsonResponse.getStatus() == 400) {
            System.out.println("Game " + displayName + " already exists.");
        } else if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": could not register game service");
            // send gameCreationConfirmed(Game null) to the User
            // gameCreationConfirmed(null);
        } else {
            // create a new Game object
            Game newGame = new Game(numberOfPlayers, numberOfRounds, destinationTownEnabled, witchEnabled,
                    mode, townGoldOption);
            // set as currentgame
            ClientMain.currentGame = newGame;
            // create a new LobbyServiceGame object
            LobbyServiceGame newLSGame = new LobbyServiceGame(name, displayName, location, numberOfPlayers, newGame);

            try {
                newSessionCreated = Registrator.instance().createGameSession(newLSGame, ClientMain.currentUser, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                newLSGame.setActiveSession(newSessionCreated);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ClientMain.currentSession = newSessionCreated;
            ClientMain.currentSession.addUser(ClientMain.currentUser);
        }
        return newSessionCreated;
    }

    /**
     * 
     * Creates a game session on the Lobby Service and creates a new
     * LobbyServiceGameSession instance.
     * 
     * @param gameService ice game service associated with the new game session
     * @param creator     us r that wants to create the session
     * @param saveGameID  save game id - empty if not wanted
     * @return LobbyServiceGameSession instance that was created
     */

    public LobbyServiceGameSession createGameSession(LobbyServiceGame gameService, User creator, String saveGameID)
            throws Exception {
        // API request
        Map<String, Object> fields = new HashMap<>();
        fields.put("creator", creator.getName());
        fields.put("game", gameService.getName());
        fields.put("savegame", saveGameID);

        // user token
        String token = creator.getToken().replace("+", "%2B");
        System.out.println(token);

        // build request
        HttpResponse<String> jsonResponse = Unirest
                .post("http://elfenland.simui.com:4242/api/sessions/?access_token="
                        + token)
                .header("Content-Type", "application/json")
                .body(new Gson().toJson(fields)).asString();

        System.out.println("PRINTING CREATEGAMESESSION" + jsonResponse.getBody());

        // verify response
        if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": could not create new game session");
            throw new Exception("Error");
        } else {
            // get the session ID
            String id = jsonResponse.getBody();
            System.out.println("SUCCESS! " + id);
            // create the new LobbyServiceGame instance
            LobbyServiceGameSession lsgs = new LobbyServiceGameSession("", creator, gameService, id);
            // send this info to the server
            LobbyServiceGame gs = lsgs.getGameService();
            Game g = gs.getGame();
            String senderName = creator.getName();
            int numberOfPlayers = g.getNumberOfPlayers();
            int gameRoundsLimit = g.getNumberOfRounds();
            boolean destinationTownEnabled = g.isDestinationTownEnabled();
            boolean witchEnabled = g.isWitchEnabled();
            TownGoldOption townGoldOption = g.getTownGoldOption();
            String stringTown = null;
            if (townGoldOption.equals(TownGoldOption.NO)) {
                stringTown = "no";
            } else if (townGoldOption.equals(TownGoldOption.YESDEFAULT)) {
                stringTown = "yes-default";
            } else if (townGoldOption.equals(TownGoldOption.YESRANDOM)) {
                stringTown = "yes-random";
            }
            Mode mode = g.getMode();
            String stringMode = null;
            if (mode.equals(Mode.ELFENLAND)) {
                stringMode = "elfenland";
            } else if (mode.equals(Mode.ELFENGOLD)) {
                stringMode = "elfengold"; 
            }
            CreateNewGameAction createNewGameAction = new CreateNewGameAction(senderName, id, numberOfPlayers, gameRoundsLimit, destinationTownEnabled, witchEnabled, stringMode, stringTown);
            ClientMain.ACTION_MANAGER.sendActionAndGetReply(createNewGameAction);
            return lsgs;
        }
    }

    public void joinGame(LobbyServiceGameSession gameSessionToJoin, User userJoining) throws Exception {
        // user token
        String token = userJoining.getToken().replace("+", "%2B");
        System.out.println(token);

        // build request
        HttpResponse<String> jsonResponse = Unirest
                .put("http://elfenland.simui.com:4242/api/sessions/" + gameSessionToJoin.getSessionID() + "/players/"
                        + userJoining.getName() + "?access_token="
                        + token)
                .asString();

        System.out.println(jsonResponse.getBody());

        // verify response
        if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": could not join game");
            throw new Exception("Error" + jsonResponse.getStatus() + ": could not join game");
        } else {
            System.out.println("successful join on LS side");
            // ask the server for game info
            GetGameInfoACK info = (GetGameInfoACK) ClientMain.ACTION_MANAGER.sendActionAndGetReply(new GetGameInfoAction(userJoining.getName(), gameSessionToJoin.getSessionID()));
            int numberOfPlayers = info.getNumberOfPlayers();
            int numberOfRounds = info.getNumberOfRounds();
            boolean destinationTownEnabled = info.isDestinationTownEnabled();
            boolean witchEnabled = info.isWitchEnabled();
            Mode mode = info.getMode();
            TownGoldOption townGoldOption = info.getTownGoldOption();
            // create a new Game object
            Game newGame = new Game(numberOfPlayers, numberOfRounds, destinationTownEnabled, witchEnabled, mode, townGoldOption);
            gameSessionToJoin.setGame(newGame);
            // set a currentGame
            ClientMain.currentGame = newGame;
        }
    }

    public void leaveGame(LobbyServiceGameSession sessionToLeave, User userLeaving) {
        // user token
        String token = userLeaving.getToken().replace("+", "%2B");
        System.out.println(token);

        // build request
        HttpResponse<String> jsonResponse = Unirest
                .delete("http://elfenland.simui.com:4242/api/sessions/" + sessionToLeave.getSessionID() + "/players/"
                        + userLeaving.getName() + "?access_token="
                        + token)
                .asString();

        System.out.println(jsonResponse.getBody());

        // verify response
        if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": could not leave game");
        } else {
            sessionToLeave.removeUser(userLeaving);
            // TODO: notify all users that a player has left
        }
    }

    public void deleteSession(LobbyServiceGameSession sessionToDelete, User userAskingToDelete) {
        // user token
        String token = userAskingToDelete.getToken().replace("+", "%2B");
        System.out.println(token);

        // build request
        HttpResponse<String> jsonResponse = Unirest
                .delete("http://elfenland.simui.com:4242/api/sessions/" + sessionToDelete.getSessionID()
                        + "?access_token="
                        + token)
                .asString();

        System.out.println(jsonResponse.getBody());

        // verify response
        if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": could not delete game session");
        } else {
            System.out.println("deleted successfully");
        }
    }

    public void launchSession(LobbyServiceGameSession sessionToLaunch, User userAskingToLaunch) {
        // user token
        String token = userAskingToLaunch.getToken().replace("+", "%2B");
        System.out.println(token);

        // build request
        HttpResponse<String> jsonResponse = Unirest
                .post("http://elfenland.simui.com:4242/api/sessions/" + sessionToLaunch.getSessionID()
                        + "?access_token="
                        + token)
                .asString();

        System.out.println(jsonResponse.getBody());

        // verify response
        if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": could not launch game session");
        } else {
            System.out.println("launched successfully on the LS");
            // send to the server
            ClientMain.ACTION_MANAGER.sendActionAndGetReply(new LaunchGameAction(userAskingToLaunch.getName(), sessionToLaunch.getSessionID()));
        }
    }

    /**
     * Helper function for availableGames(). Returns an arraylist of
     * LobbyServiceGame objects available on the LobbyService
     *
     * @return arrayList of LobbyServiceGame objects
     * @throws IOException
     * @throws ParseException
     */
    public static ArrayList<LobbyServiceGame> getAvailableGames() throws IOException, ParseException {
        URL url = new URL("http://elfenland.simui.com:4242/api/gameservices");
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
        System.out.println(content);
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
            int numberOfPlayers = (int) (long) gameJson.get("maxSessionPlayers");
            LobbyServiceGame existingGame = LobbyServiceGame.getLobbyServiceGame(name);
            if (existingGame == null) {
                // there is no such game yet
                // create LobbyServiceGame and add it to availableGames list
                availableGames.add(new LobbyServiceGame(name, displayName, location, numberOfPlayers));
            } else {
                availableGames.add(existingGame);
            }

        }

        return availableGames;
    }

    /**
     * Helper function for getAvailableGames(). Returns details on a previously
     * registered Game-Service.
     *
     * @return JSONObject representing the game-service
     * @throws IOException
     */
    public static JSONObject getLSGameInfo(String name) throws IOException, ParseException {
        URL url = new URL("http://elfenland.simui.com:4242/api/gameservices/" + name);
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

    /**
     * Helper function for availableGames(). It gets and returns all available game
     * session on the Lobby Service
     *
     * @return ArrayList<LobbyServiceGameSession> representing all available
     *         sessions on the Lobby Service
     * @throws IOException
     * @throws ParseException
     */
    public static ArrayList<LobbyServiceGameSession> getAvailableSessions() throws ParseException {
        HttpResponse<String> jsonResponse = Unirest
                .get("http://elfenland.simui.com:4242/api/sessions")
                .asString();
        if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": could not get active sessions.");
        }

        JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse.getBody());

        ArrayList<LobbyServiceGameSession> availableSessions = new ArrayList<>();

        try {
            JSONObject sessions = (JSONObject) jsonObject.get("sessions");
            sessions.keySet().forEach(sessionID -> {
                JSONObject sessionJSON = (JSONObject) sessions.get(sessionID);

                // create a game service object
                JSONObject gameParameters = (JSONObject) sessionJSON.get("gameParameters");
                String gameName = (String) gameParameters.get("name");
                String gameDisplayName = (String) gameParameters.get("displayName");
                String gameLocation = (String) gameParameters.get("location");
                int gameNumberOfPlayers = (int) (long) gameParameters.get("maxSessionPlayers");
                LobbyServiceGame existingGame = LobbyServiceGame.getLobbyServiceGame(gameName);
                LobbyServiceGame relatedGame;
                if (existingGame == null) {
                    // game does not exist so create a new one
                    relatedGame = new LobbyServiceGame(gameName, gameDisplayName, gameLocation, gameNumberOfPlayers);
                } else {
                    // game already exists
                    relatedGame = existingGame;
                }

                // session info
                LobbyServiceGameSession newSession;
                if (relatedGame.getActiveSession() == null) {
                    // create a game session object
                    String saveGameID = (String) sessionJSON.get("savegameid");
                    String creatorName = (String) sessionJSON.get("creator");
                    boolean launched = (boolean) sessionJSON.get("launched");
                    User creatorUser = new User(creatorName);
                    newSession = new LobbyServiceGameSession(saveGameID, creatorUser, relatedGame, (String) sessionID);
                    ArrayList<String> listOfUsers = (ArrayList<String>) sessionJSON.get("players");
                    for (String userName : listOfUsers) {
                        if (!userName.equals(creatorName)) {
                            User newUser = new User(userName);
                            newSession.addUser(newUser);
                        }
                    }
                    newSession.setLaunched(launched);
                    // set as the game's active session
                    try {
                        relatedGame.setActiveSession(newSession);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // we are here if the game already has a session object.
                    newSession = relatedGame.getActiveSession();
                }

                availableSessions.add(newSession);

            });
        } catch (NullPointerException e) {
            // there are no available sessions
        }
        return availableSessions;
    }
}
