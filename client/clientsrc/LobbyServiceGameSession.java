package clientsrc;
import java.util.ArrayList;

// represents one active game session
public class LobbyServiceGameSession implements Joinable{

    // fields
    private boolean launched;
    private ArrayList<User> users = new ArrayList<>();
    private String saveGameID;
    private String creator;
    private LobbyServiceGame gameService;
    private String sessionID;

    /**
     * CONSTRUCTOR: creates a new LobbyServiceGameSession instance
     * @param saveGameID savegameID to load. If there is none, put ""
     * @param creator User of the creator of the game
     * @param gameService gameservice for which this session belongs to
     * @param sessionID sessionID on the LS
     */
    public LobbyServiceGameSession(String saveGameID, User creator, LobbyServiceGame gameService, String sessionID) {
        this.launched = false;
        this.saveGameID = saveGameID;
        this.creator = creator.getName();
        this.users.add(creator);
        this.gameService = gameService;
        this.sessionID = sessionID;
    }

    /**
     * GETTER: returns the value of launched
     * @return true if the session has been launched, false otherwise
     */
    public boolean isLaunched() {
        return launched;
    }

    /**
     * GETTER: returns the list of users that have joined the session
     * @return ArrayList<User>
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Indicates if the username is in this game session.
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
     * GETTER: returns the number of users that have currently joined the game
     * @return size of ArrayList<User>
     */
    public int getNumberOfUsersCurrently() {
        return users.size();
    }

    /**
     * GETTER: returns the save game id
     * @return save game id. "" if the game has no save id yet
     */
    public String getSaveGameID() {
        return saveGameID;
    }

    /**
     * GETTER: Retunrs the username of the creator of the game
     * @return usersname of user that is the creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * GETTER: returns the LobbyServiceGame associated with this session.
     * @return the LobbyServiceGame from which this session was started
     */
    public LobbyServiceGame getGameService() {
        return gameService;
    }

    /**
     * Returns the session ID that is on the LS
     * @return session ID
     */
    public String getSessionID() {
        return sessionID;
    }

    /**
     * Add a User to the list of users in this session
     * @param user user that is being added
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Remove a User from the list of users in this session
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

    /**
     * Checks whether this session can be launched by the LS (LS won't give an error).
     * A session is launchable when enough users have joined and all theses users are ready.
     * @return true if the session is launchable (LS won't give an error), false otherwise
     */
    public boolean isLaunchable() {
        // check there are enough users
        if (users.size() != gameService.getNumberOfUsers()) {
            return false;
        }

        // check all users are ready
        boolean allReady = true;
        for (User u : this.users) {
            if (!u.isReady()) {
                allReady = false;
                break;
            }
        }
        return allReady;
    }

    /**
     * The Registrator will make this user join this game session
     */
    @Override
    public LobbyServiceGameSession join() throws Exception {
        Registrator.instance().joinGame(this, ClientMain.currentUser);
        return this;
    }

    /**
     * Returns this session
     * @return this session
     */
    @Override
    public LobbyServiceGameSession getActiveSession() {
        return this;
    }
}