package serversrc;

import networksrc.Server;

public class ServerMain {

    static Server SERVER = Server.getInstance();
    static Registrator REGISTRATOR = Registrator.instance();

    public static void main(String[] args) {
        Thread serverThread = new Thread(() -> SERVER.start());
        serverThread.start();
    }
}
