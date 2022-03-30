package networksrc;

public class TestAction implements Action {

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        // server has received the message
        System.out.println("The network is working!!!");
    }
}
