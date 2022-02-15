import java.io.*;
import java.net.*;

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
            System.out.println("Client created at host: " + pHost + ", and port: " + pPort);
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + pHost);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + pHost);
        }
    }

    @Override
    public void start() {
        /**
         * while (true)
         * {
         * if (! aPlayer.getActionStack().empty()){
         * try {
         * Action toSend = aPlayer.getActionStack().pop();
         * aObjectOut.writeObject();
         * 
         * } catch (IOException e) {
         * e.printStackTrace();
         * }
         * }
         * }
         */
        try {
            aObjectOut.writeObject(new TestAction());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHost() {
        return aSocket.getInetAddress().getHostName();
    }
}
