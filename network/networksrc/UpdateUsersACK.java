package networksrc;

import java.util.ArrayList;

import clientsrc.ClientMain;
import clientsrc.Color;
import clientsrc.User;

public class UpdateUsersACK implements Action{

    private ArrayList<String> newUsers;

    public UpdateUsersACK(ArrayList<String> newUsers) {
        this.newUsers = newUsers;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        for (int i=0; i<newUsers.size(); i+=2) {
            String name = newUsers.get(i);
            String color = newUsers.get(i+1);
            User user = new User(name);
            if (color.equals("BLACK")) {
                user.setColor(Color.BLACK);
            } else if (color.equals("BLUE")) {
                user.setColor(Color.BLUE);
            } else if (color.equals("RED")) {
                user.setColor(Color.RED);
            } else if (color.equals("GREEN")) {
                user.setColor(Color.BLUE);
            } else if (color.equals("PURPLE")) {
                user.setColor(Color.PURPLE);
            } else if (color.equals("YELLOW")) {
                user.setColor(Color.YELLOW);
            }
            ClientMain.currentSession.addUser(user);
        }
    }
    
}
