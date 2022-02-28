package networksrc;

import java.io.*;
import java.net.*;

import clientsrc.User;

//import clientsrc.Player;

public class Client implements NetworkNode {
    private Socket aSocket;
    private ObjectOutputStream aObjectOut;
    private ObjectInputStream aObjectIn;
    private User user;
    private String name;

    public Client(String pHost, int pPort, String name) {
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
        this.name = name;
    }

    @Override
    public void start() {
        try {
            aObjectOut.writeObject(new GiveNameAction(name));
            aObjectOut.writeObject(new TestAction(name));
            Action actionIn;
            try {
                actionIn = (Action) aObjectIn.readObject();
                if (actionIn.isValid()) {
                    actionIn.execute();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHost() {
        return aSocket.getInetAddress().getHostName();
    }

    

    /* public void setPlayer(Player pPlayer) {
        aPlayer = pPlayer;
    } */
}
