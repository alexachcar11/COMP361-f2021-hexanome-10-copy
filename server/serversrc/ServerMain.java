package serversrc;

import networksrc.Server;

public class ServerMain {

    static Server SERVER = Server.getInstance();

    public static void main(String[] args) {
        SERVER.start();
    }
}
