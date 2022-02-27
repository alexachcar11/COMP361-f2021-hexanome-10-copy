package src;
public class ServerMain {

    static Server SERVER = new Server(4444);

    public static void main(String[] args) {
        Thread serverThread = new Thread(() -> SERVER.start());
        serverThread.start();
    }
}
