package networksrc;

import java.util.ArrayList;

import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;

public class GetAvailableColorsACK implements Action{

    private ArrayList<String> availableColors;

    public GetAvailableColorsACK(ArrayList<String> availableColors) {
        this.availableColors = availableColors;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("GetAvailableColorsACK received");
        
        // display available colors
        try {
            ClientMain.displayColors(availableColors);
        } catch (MinuetoFileException e) {
            e.printStackTrace();
        }
    }
    
}
