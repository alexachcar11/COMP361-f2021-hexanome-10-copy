package networksrc;

import java.io.*;
import java.net.*;

import clientsrc.User;

//import clientsrc.Player;

public class Client implements NetworkNode {
    private Socket aSocket;
    private ObjectOutputStream aObjectOut;
    private ObjectInputStream aObjectIn;
    private User aUser;

    public Client(String pHost, int pPort, User pUser) {
        try {
            aSocket = new Socket(pHost, pPort);
            System.out.println("Client is connected!");
            OutputStream aOut = aSocket.getOutputStream();
            InputStream aIn = aSocket.getInputStream();
            aObjectOut = new ObjectOutputStream(aOut);
            aObjectIn = new ObjectInputStream(aIn);
            System.out.println("Client created at host: " + pHost + ", and port: " + pPort);
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + pHost);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + pHost);
        }
        this.aUser = pUser;
    }

    @Override
    public void start() {
        // notify the server of this client's username
        try {
            aObjectOut.writeObject(new GiveNameAction(aUser.getName()));
            System.out.println("gave name" + aUser.getName());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Retrieve the host of the Client (i.e. their machine)
     * @return Socket's address
     */
    public String getHost() {
        return aSocket.getInetAddress().getHostName();
    }

    /**
     * Retrieve the ObjectOutputStream of the Client. This is where we writeObject(ActionToSend)
     * @return aObjectOut
     */
    public ObjectOutputStream getObjectOutputStream() {
        return aObjectOut;
    }

    /**
     * Retrieve the ObjectInputStream of the Client. This is where we readObject(ActionReceived)
     * @return aObjectIn
     */
    public ObjectInputStream getObjectInputStream() {
        return aObjectIn;
    }
}
