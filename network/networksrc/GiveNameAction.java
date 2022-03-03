package networksrc;

public class GiveNameAction implements Action {

    private String name;

    public GiveNameAction(String name) {
        this.name = name;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        // empty on purpose
    }

    public String getName() {
        return name;
    }
    
}
