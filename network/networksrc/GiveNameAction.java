package networksrc;

public class GiveNameAction implements Action {

    private String name;

    public GiveNameAction(String name) {
        this.name = name;
    }

    @Override
    public boolean isValid() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        
    }

    public String getName() {
        return name;
    }
    
}
