package networksrc;

public class TestActionACK implements Action{

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("TestActionACK received.");
        // client does this
    }

}
