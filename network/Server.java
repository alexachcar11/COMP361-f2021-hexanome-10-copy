import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

public class Server implements NetworkNode {
    // listening socket
    private ServerSocket aSocket;
    // list of sockets communicating with clients
    private final List<ClientTuple> aClientSockets = new ArrayList<>();
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

    // create a thread to do this, in ServerMain
    @Override
    public void start() {
        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = aSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed: 4444");
                e.printStackTrace();
            }
            if (clientSocket != null) {
                final ClientTuple tuple = new ClientTuple(clientSocket);
                aClientSockets.add(tuple); // allows use in inner class
                Thread clientThread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        listenToClient(tuple);
                    }
                });
                clientThread.start();
            }
        }
    }

    private void listenToClient(ClientTuple pTuple) {
        try {
            Action actionIn = (Action) pTuple.input().readObject();
        } catch (IOException e) {
            String host = pTuple.socket().getInetAddress().getHostName();
            System.err.println("Couldn't get I/O for the connection to: " + host);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}

class ClientTuple {
    private Socket aSocket;
    private ObjectInputStream aInputStream;
    private ObjectOutputStream aOutputStream;

    ClientTuple(Socket pSocket) {
        aSocket = pSocket;
        try {
            aInputStream = new ObjectInputStream(aSocket.getInputStream());
            aOutputStream = new ObjectOutputStream(aSocket.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    Socket socket() {
        return aSocket;
    }

    ObjectInputStream input() {
        return aInputStream;
    }

    ObjectOutputStream output() {
        return aOutputStream;
    }
}