package serversrc;

import networksrc.Server;

public class ServerMain {

    public static Server SERVER = Server.getInstance();
    public static Registrator REGISTRATOR = Registrator.instance();

    public static void main(String[] args) {
        SERVER.start();
    }
}
