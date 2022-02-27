package src;
public interface Joinable {



    LobbyServiceGameSession join() throws Exception;

    LobbyServiceGameSession getActiveSession();
}
