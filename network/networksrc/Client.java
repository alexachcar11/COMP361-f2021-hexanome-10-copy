package networksrc;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;

public class Client implements NetworkNode {
    private Socket aSocket;
    private ObjectOutputStream aObjectOut;
    private ObjectInputStream aObjectIn;
    // private User aUser;
    private boolean running;
    // queue for actions to be executed by the client
    private final Queue<Action> actionInQueue;
    // thread used to execute incoming actions
    private final Thread executionThread;
    // thread for listening to server and adding actions to queue
    private final Thread listenThread;
    private String name;

    public Client(String pHost, int pPort, String username) {
        try {
            aSocket = new Socket(pHost, pPort);
            System.out.println("Client is connected!");
            OutputStream aOut = aSocket.getOutputStream();
            InputStream aIn = aSocket.getInputStream();
            aObjectOut = new ObjectOutputStream(aOut);
            aObjectIn = new ObjectInputStream(aIn);
            System.out.println("Client created at host: " + pHost + ", and port: " + pPort);
            this.name = username;
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + pHost);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + pHost);
        }
        this.running = true;
        // this.aUser = pUser;
        this.actionInQueue = new LinkedList<>();
        this.listenThread = new Thread(() -> this.listenToServer());
        this.executionThread = new Thread(() -> {
            while (this.running) {
                if (!actionInQueue.isEmpty()) {
                    Action toExecute = actionInQueue.poll();
                    if (toExecute.isValid()) {
                        toExecute.execute();
                    }
                }
            }
        });

    }

    /**
     * confirms communication with server via GiveNameAction
     * creates thread to listen for actions to execute
     */
    @Override
    public void start() {
        
        this.executionThread.start();
        this.listenThread.start();
        // notify the server of this client's username
        try {
            aObjectOut.writeObject(new GiveNameAction(name));
            System.out.println("gave name" + name);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * busy waits for actions to be received in socket
     * adds received actions to queue
     * alternatively, could assign a thread to each action
     */
    private void listenToServer() {
        while (running) {
            Action actionIn = null;
            try {
                actionIn = (Action) aObjectIn.readObject();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
            if (actionIn != null) {
                this.actionInQueue.add(actionIn);
            }
        }
    }

    /**
     * Retrieve the host of the Client (i.e. their machine)
     * 
     * @return Socket's address
     */
    public String getHost() {
        return aSocket.getInetAddress().getHostName();
    }

    /**
     * Retrieve the ObjectOutputStream of the Client. This is where we
     * writeObject(ActionToSend)
     * 
     * @return aObjectOut
     */
    public ObjectOutputStream getObjectOutputStream() {
        return aObjectOut;
    }

    /**
     * Retrieve the ObjectInputStream of the Client. This is where we
     * readObject(ActionReceived)
     * 
     * @return aObjectIn
     */
    public ObjectInputStream getObjectInputStream() {
        return aObjectIn;
    }

    /**
     * Sets a new name of this client.
     * The server is notified.
     * @param newName new name associated with the client
     */
    public void setName(String newName) {
        // notify the server of this client's username
        try {
            aObjectOut.writeObject(new GiveNameAction(newName));
            System.out.println("gave name" + newName);
            this.name = newName;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
