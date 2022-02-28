package networksrc;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TestAction extends ServerAction {

    private String senderName;

    public TestAction(String senderName) {
        this.senderName = senderName;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        // server has received the message
        System.out.println("The network is working!!!");
        // client receives a confirmation
        try {
            // get the senderName's socket
            Server serverInstance = Server.getInstance();
            ClientTuple clientTupleToNotify = serverInstance.getClientTupleByUsername(senderName);
            // get the socket's output stream
            ObjectOutputStream objectOutputStream = clientTupleToNotify.output();
            objectOutputStream.writeObject(new TestActionACK(senderName));
        } catch (IOException e) {
            System.err.println("IOException in TestAction.execute().");
        }
        
    }
}
