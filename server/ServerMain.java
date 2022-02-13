public class ServerMain {

    static Server SERVER = new Server(4444);
    public static final String LOCATION = "elfenland.simui.com";

    public static void main(String[] args) {
        Thread serverThread = new Thread(new Runnable() {

            @Override
            public void run() {
                SERVER.start();
            }

        });
        serverThread.start();
    }
}
