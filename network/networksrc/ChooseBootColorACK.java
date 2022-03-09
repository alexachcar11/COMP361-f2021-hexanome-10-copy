package networksrc;

import clientsrc.ClientMain;
import clientsrc.Color;

public class ChooseBootColorACK implements Action {

    private String color;

    public ChooseBootColorACK(String color) {
        this.color = color;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        // assign the color
        if (color.equals("BLUE")) {
            ClientMain.currentUser.setColor(Color.BLUE);
        } else if (color.equals("BLACK")) {
            ClientMain.currentUser.setColor(Color.BLACK);
        } else if (color.equals("RED")) {
            ClientMain.currentUser.setColor(Color.RED);
        } else if (color.equals("GREEN")) {
            ClientMain.currentUser.setColor(Color.GREEN);
        } else if (color.equals("YELLOW")) {
            ClientMain.currentUser.setColor(Color.YELLOW);
        } else if (color.equals("PURPLE")) {
            ClientMain.currentUser.setColor(Color.PURPLE);
        } 
        // display users
    }
    
}
