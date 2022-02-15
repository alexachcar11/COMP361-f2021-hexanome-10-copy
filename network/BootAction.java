public class BootAction extends Action {

    private Town destination;
    private String sentByUser;

    public BootAction(Town pDestination, String pSender) {
        this.destination = pDestination;
        this.sentByUser = pSender;
    }

    /**
     * all methods below execute on server side
     */
    @Override
    public boolean isValid() {
        // route exists
        // token on road
        // player has required cards
        return false;
    }

    @Override
    public void execute() {
    }

}
