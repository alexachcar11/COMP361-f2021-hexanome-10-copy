import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

public class Server implements NetworkNode {
    // listening socket
    private ServerSocket aSocket;
    // list of sockets communicating with clients
    private final List<ClientTuple> aClientSockets = new ArrayList<>();
    public static final String LOCATION = "elfenland.simui.com";
    public static final int PORT = 13645;

    Server(int pPort) {
        try {
            aSocket = new ServerSocket(pPort);// listening socket
            System.out.println("Server running on port " + pPort);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + pPort);
            e.printStackTrace();
            System.exit(-1);
        }
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
            if (actionIn.isValid()) {
                actionIn.execute();
            }
        } catch (IOException e) {
            String host = pTuple.socket().getInetAddress().getHostName();
            System.err.println("Couldn't get I/O for the connection to: " + host);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public int getPort() {
        return aSocket.getLocalPort();
    }
}

/**
 * contains server side information on communication with clients
 * the socket on the server that talks with the client
 * the input and output streams for communication with the client
 */
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