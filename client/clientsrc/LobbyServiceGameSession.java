package clientsrc;

import java.util.ArrayList;

import networksrc.PlayerHasJoinedAction;
import networksrc.UpdateUsersAction;

// represents one active game session
public class LobbyServiceGameSession {

    // fields
    private String displayName;
    private boolean launched;
    private ArrayList<User> users = new ArrayList<>();
    private String saveGameID;
    private String creator;
    private String sessionID;
    private Game game;
    private int numberOfPlayersCurrently;
    private static ArrayList<LobbyServiceGameSession> allSessions = new ArrayList<>();

    /**
     * CONSTRUCTOR
     * 
     * @param saveGameID save game id or "" if there is none
     * @param game       associated game
     * @param creator    creator of the session
     * @param sessionID  session ID as seen on LS
     * @param name       display name of the session on LS
     */
    public LobbyServiceGameSession(String saveGameID, Game game, User creator, String sessionID, String name) {
        this.launched = false;
        this.saveGameID = saveGameID;
        this.creator = creator.getName();
        this.sessionID = sessionID;
        this.game = game;
        this.displayName = name;
        allSessions.add(this);
    }

    /**
     * Retrieves a LobbyServiceGameSession by ID
     * 
     * @param sessionID sessionID to search
     * @return LobbyServiceGameSession or null if not found
     */
    public static LobbyServiceGameSession getSessionByName(String name) {
        for (LobbyServiceGameSession s : allSessions) {
            if (s.getDisplayName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Retrieves all available sessions (i.e. unlaunched)
     * 
     * @return ArrayList<LobbyServiceGameSession> that are not launched
     */
    public static ArrayList<LobbyServiceGameSession> getAvailableSession() {
        ArrayList<LobbyServiceGameSession> list = new ArrayList<>();
        for (LobbyServiceGameSession s : allSessions) {
            if (!s.isLaunched()) {
                list.add(s);
            }
        }
        return list;
    }

    /**
     * GETTER: returns the value of launched
     * 
     * @return true if the session has been launched, false otherwise
     */
    public boolean isLaunched() {
        return launched;
    }

    /**
     * GETTER: returns the list of users that have joined the session
     * 
     * @return ArrayList<User>
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    public void updateUsers() {
        String senderName = ClientMain.currentUser.getName();
        ClientMain.ACTION_MANAGER.sendAction(new UpdateUsersAction(senderName, sessionID));
    }

    /**
     * Indicates if the username is in this game session.
     * 
     * @param userName user to check
     * @return true if the username is in the session, false otherwise
     */
    public boolean hasUserName(String userName) {
        boolean hasUser = false;
        for (User u : users) {
            if (userName.equals(u.getName())) {
                hasUser = true;
                break;
            }
        }
        return hasUser;
    }

    /**
     * GETTER: retrieves the display name of the session
     * 
     * @return displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * GETTER: returns the number of users that have currently joined the game
     * 
     * @return size of ArrayList<User>
     */
    public int getNumberOfUsersCurrently() {
        return numberOfPlayersCurrently;
    }

    /**
     * Set the current number of players to n
     * 
     * @param n total number of players currently registered in this session
     */
    public void setCurrentNumberOfPlayers(int n) {
        this.numberOfPlayersCurrently = n;
    }

    /**
     * GETTER: returns the save game id
     * 
     * @return save game id. "" if the game has no save id yet
     */
    public String getSaveGameID() {
        return saveGameID;
    }

    /**
     * GETTER: Retunrs the username of the creator of the game
     * 
     * @return usersname of user that is the creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Returns the session ID that is on the LS
     * 
     * @return session ID
     */
    public String getSessionID() {
        return sessionID;
    }

    /**
     * Add a User to the list of users in this session
     * 
     * @param user user that is being added
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Remove a User from the list of users in this session
     * 
     * @param user user to remove
     */
    public void removeUser(User user) {
        this.users.remove(user);
        user.toggleReady();
    }

    /**
     * Set the launch value to true
     */
    public void launch() {
        this.launched = true;
    }

    public void setLaunched(boolean launched) {
        this.launched = launched;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Checks whether this session can be launched by the LS (LS won't give an
     * error).
     * A session is launchable when enough users have joined and all theses users
     * are ready.
     * 
     * @return true if the session is launchable (LS won't give an error), false
     *         otherwise
     */
    public boolean isLaunchable() {
        // check there are enough users

        return users.size() == game.getNumberOfPlayers();

        /*
         * // check there are enough users
         * if (users.size() != gameService.getNumberOfUsers()) {
         * return false;
         * }
         * 
         * // check all users are ready
         * boolean allReady = true;
         * for (User u : this.users) {
         * if (!u.isReady()) {
         * allReady = false;
         * break;
         * }
         * }
         * return allReady;
         */
    }

    /**
     * The Registrator will make this user join this game session
     */
    public LobbyServiceGameSession join(Color pColor) throws Exception {
        ClientMain.currentSession = this;
        // pColor into string format
        String colorStr = null;
        if (pColor.equals(Color.BLACK)) {
            colorStr = "BLACK";
        } else if (pColor.equals(Color.BLUE)) {
            colorStr = "BLUE";
        } else if (pColor.equals(Color.YELLOW)) {
            colorStr = "YELLOW";
        } else if (pColor.equals(Color.PURPLE)) {
            colorStr = "PURPLE";
        } else if (pColor.equals(Color.RED)) {
            colorStr = "RED";
        } else if (pColor.equals(Color.GREEN)) {
            colorStr = "GREEN";
        }
        ClientMain.ACTION_MANAGER
                .sendAction(new PlayerHasJoinedAction(ClientMain.currentUser.getName(), sessionID, colorStr));
        return this;
    }
}