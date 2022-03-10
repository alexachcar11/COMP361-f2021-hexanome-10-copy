package networksrc;

import java.io.*;
import java.net.*;
import java.util.*;

import serversrc.ServerUser;

import java.lang.Thread;

public class Server implements NetworkNode {
    // listening socket
    private ServerSocket aSocket;
    // list of sockets communicating with clients
    private final List<ClientTuple> aClientSockets = new ArrayList<>();
    public static final String LOCATION = "elfenland.simui.com";
    public static final int PORT = 13645;
    private static Server INSTANCE = new Server(PORT);

    private Server(int pPort) {
        try {
            aSocket = new ServerSocket(pPort);// listening socket
            System.out.println("Server running on port " + pPort);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + pPort);
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static Server getInstance() {
        return INSTANCE;
    }

    // create a thread to do this, in ServerMain
    @Override
    public void start() {
        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = aSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed: 13645");
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
        while (true) {
            try {
                Action actionIn = (Action) pTuple.input().readObject();
                if (actionIn.getClass().equals(GiveNameAction.class)) {
                    GiveNameAction giveNameAction = (GiveNameAction) actionIn;
                    pTuple.setName(giveNameAction.getName());
                } else {
                    if (actionIn.isValid()) {
                        actionIn.execute();
                    }
                }
            } catch (IOException e) {
                String host = pTuple.socket().getInetAddress().getHostName();
                System.err
                        .println("Couldn't get I/O for the connection to: " + host + ", or they disconnected.");
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public int getPort() {
        return aSocket.getLocalPort();
    }

    public ClientTuple getClientTupleByUsername(String username) {
        for (ClientTuple clientTuple : aClientSockets) {
            if (clientTuple.getUsername().equals(username)) {
                return clientTuple;
            }
        }
        return null;
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
    private String username;

    ClientTuple(Socket pSocket) {
        this.aSocket = pSocket;
        try {
            aOutputStream = new ObjectOutputStream(aSocket.getOutputStream());
            aInputStream = new ObjectInputStream(aSocket.getInputStream());
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

    String getUsername() {
        return username;
    }

    void setName(String name) {
        System.out.println("setting name" + name);
        this.username = name;
        System.out.println("creating a ServerUser for " + name);
        new ServerUser(name);
    }
}