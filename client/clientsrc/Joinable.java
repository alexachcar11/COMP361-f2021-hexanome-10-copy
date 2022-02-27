package clientsrc;
public interface Joinable {



    LobbyServiceGameSession join() throws Exception;

    LobbyServiceGameSession getActiveSession();
}
