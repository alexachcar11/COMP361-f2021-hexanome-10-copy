package networksrc;

public class TestAction implements Action {

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
        // send an ACK to the sender
        ActionManager ackManager = ActionManager.getInstance();
        TestActionACK actionToSend = new TestActionACK();
        ackManager.sendToSender(actionToSend, senderName);

    }
}
