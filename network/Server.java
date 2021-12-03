import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

public class Server implements NetworkNode {
    // listening socket
    private ServerSocket aSocket;
    // list of sockets communicating with clients
    private final List<Socket> aClientSockets = new ArrayList<>();
    // singleton for server
    private static final Server SERVER = new Server(4444);

    private Server(int pPort) {
        try {
            aSocket = new ServerSocket(pPort);// listening socket
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444");
            System.exit(-1);
        }
    }

    // returns singleton object
    public static Server instance() {
        return SERVER;
    }

    // create a thread to do this
    public void start() {
        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = aSocket.accept();
            } catch (IOException e) {
                System.out.println("Accept failed: 4444");
            }
            if (clientSocket != null) {
                aClientSockets.add(clientSocket);
                final Socket socket = clientSocket; // allows use in inner class
                Thread clientThread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        listenToClient(socket);
                    }
                });
                clientThread.start();
            }
        }
    }

    private void listenToClient(Socket pClientSocket) {
        try {
            ObjectInputStream input = new ObjectInputStream(pClientSocket.getInputStream());
        } catch (IOException e) {
            String host = pClientSocket.getInetAddress().getHostName();
            System.err.println("Couldn't get I/O for the connection to: " + host);
        }
        // TODO: finish
    }
}