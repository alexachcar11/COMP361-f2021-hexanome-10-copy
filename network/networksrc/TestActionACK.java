package networksrc;

public class TestActionACK extends Action{

    private String receiverName;

    public TestActionACK(String receiverName) {
        this.receiverName = receiverName;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("TestActionACK received by: " +  receiverName);
    }

}
