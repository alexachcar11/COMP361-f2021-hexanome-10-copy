package networksrc;

import clientsrc.ClientMain;

public class WinnerACK implements Action{

    private String winnerName;

    public WinnerACK(String name){
        this.winnerName = name;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        ClientMain.diaplayWinnerByString(this.winnerName);
    }
    
}
