import java.io.*;
import java.net.*;
import java.lang.*;

public class Client implements NetworkNode {
    private Socket aSocket;
    private ObjectOutputStream aObjectOut;
    private ObjectInputStream aObjectIn;
    private Player aPlayer;

    public Client(String pHost, int pPort) {
        try {
            aSocket = new Socket(pHost, pPort);
            OutputStream aOut = aSocket.getOutputStream();
            InputStream aIn = aSocket.getInputStream();
            aObjectOut = new ObjectOutputStream(aOut);
            aObjectIn = new ObjectInputStream(aIn);
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + pHost);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + pHost);
        }
    }

    @Override
    public void start() {
        try {
            aObjectOut.writeObject(aPlayer.getBootAction());
            Action actionIn = (Action) aObjectIn.readObject();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getHost() {
        return aSocket.getInetAddress().getHostName();
    }

    public void setPlayer(Player pPlayer) {
        aPlayer = pPlayer;
    }
}