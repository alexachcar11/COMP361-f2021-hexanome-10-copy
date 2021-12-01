import java.io.*;
import java.net.*;

public class Client {
    private Socket aSocket;

    public Client(String pHost, int pPort) {
        try {
            aSocket = new Socket(pHost, pPort);
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + pHost);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + pHost);
        }
    }

}
