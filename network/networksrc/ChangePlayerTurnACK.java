package networksrc;

import clientsrc.ClientMain;

public class ChangePlayerTurnACK implements Action {

    boolean boo;
    public ChangePlayerTurnACK(boolean b){
        this.boo = b;
    }
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        if (this.boo){
            System.out.println("It's your turn");
        }
        else{
            System.out.println("No longer your turn");
        }
        ClientMain.currentPlayer.setTurn(this.boo);
    }
    
}
