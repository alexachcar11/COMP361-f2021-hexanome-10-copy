public class BootAction extends Action {

    private Player aPlayer;

    public BootAction(Player pPlayer) {
        aPlayer = pPlayer;
    }

    @Override
    public boolean isValid() {
        // route exists
        // token on road
        // player has required cards
        return false;
    }

    @Override
    public void execute() {
        aPlayer.draw();
    }

}
