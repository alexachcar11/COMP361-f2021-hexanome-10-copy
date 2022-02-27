package networksrc;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TestAction extends Action {

    private Socket socket;

    private String test = "The network is working!!!";

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        // server has received the message
        System.out.println(test);
        // client receives a confirmation
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new TestActionConfirmation());
        } catch (IOException e) {
            System.err.println("IOException in TestAction.execute().");
        }
        
    }

    public class TestActionConfirmation extends Action{

        @Override
        public boolean isValid() {
            return false;
        }

        @Override
        public void execute() {
            System.out.println("TestActionConfirmation is working!");
        }

    }

}
