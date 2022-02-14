public class ServerMain {

    static Server SERVER = new Server(4444);

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
