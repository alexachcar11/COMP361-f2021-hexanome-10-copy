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
    private String name;

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
        try {
            aObjectOut.writeObject(new GiveNameAction(aUser.getName()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        /* while (true) {
            // input messages
            Action actionIn;
            try {
                actionIn = (Action) aObjectIn.readObject();
                if (actionIn.isValid()) {
                    actionIn.execute();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }  */
            /* // output messages
            if (!aUser.getActionQueue().isEmpty()) {
                try {
                    Action toSend = aUser.getActionQueue().poll();
                    aObjectOut.writeObject(toSend);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } */
        //}
    }

    public String getHost() {
        return aSocket.getInetAddress().getHostName();
    }
}
