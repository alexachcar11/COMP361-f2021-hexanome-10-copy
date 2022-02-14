public class TestAction extends Action {

    private String test = "The network is working!!!";

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println(test);
    }

}
