package networksrc;

public class CreateNewGameACK implements Action{

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("CreateNewGameACK received");
    }
    
}
