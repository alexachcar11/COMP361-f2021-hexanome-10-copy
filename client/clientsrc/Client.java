package clientsrc;

import java.io.*;
import java.net.*;

import org.minueto.MinuetoEvent;
import org.minueto.MinuetoEventQueue;

import networksrc.Action;
import networksrc.GiveNameAction;
import networksrc.NetworkNode;
import clientsrc.ClientMain;

public class Client implements NetworkNode {
    private Socket aSocket;
    private ObjectOutputStream aObjectOut;
    private ObjectInputStream aObjectIn;
    private String name;
    private Thread listenThread;

    public Client(String pHost, int pPort, String userString) {
        try {
            aSocket = new Socket(pHost, pPort);
            System.out.println("Client is connected!");
            OutputStream aOut = aSocket.getOutputStream();
            InputStream aIn = aSocket.getInputStream();
            aObjectOut = new ObjectOutputStream(aOut);
            aObjectIn = new ObjectInputStream(aIn);
            System.out.println("Client created at host: " + pHost + ", and port: " + pPort);
            this.name = userString;
            this.listenThread = new Thread(() -> listenToServer());
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + pHost);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + pHost);
        }
    }

    /**
     * confirms communication with server via GiveNameAction
     * creates thread to listen for actions to execute
     */
    @Override
    public void start() {
        // notify the server of this client's username
        try {
            aObjectOut.writeObject(new GiveNameAction(name));
            System.out.println("Client create at address: " + this.aSocket.getInetAddress().getHostName());
            this.listenThread.start();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void listenToServer() {
        try {
            Action action = (Action) this.aObjectIn.readObject();
            if (action.isValid()) {
                action.execute();
            }
        } catch (IOException e) {
            System.err.println("Fatal Error: failed to receive communication from server. Terminating application...");
            e.printStackTrace();
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
     * 
     * @param newName new name associated with the client
     */
    public void setName(String newName) {
        // notify the server of this client's username
        try {
            aObjectOut.writeObject(new GiveNameAction(newName));
            System.out.println("gave name " + newName);
            this.name = newName;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
