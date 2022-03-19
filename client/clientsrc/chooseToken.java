package clientsrc;

import java.util.ArrayList;

import javax.swing.*;

import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

import java.awt.*;

public class chooseToken {
    
    private ArrayList<TokenImage> listOfAvailableTokens = new ArrayList<>(); 
    private Player player; 

    public chooseToken(Player p, ArrayList<TokenImage> tokensToChooseFrom) throws MinuetoFileException { 

        player = p;
        listOfAvailableTokens = tokensToChooseFrom;

        openInterface();
    }

    public void openInterface() throws MinuetoFileException { 
        JPanel chooseTokenPanel = new JPanel(); 
        chooseTokenPanel.setLayout(new BoxLayout(chooseTokenPanel, BoxLayout.Y_AXIS));

        JPanel headerTitle = new JPanel();
        headerTitle.setLayout(new BoxLayout(headerTitle, BoxLayout.Y_AXIS));

        JPanel tokenOptions = new JPanel();
        tokenOptions.setLayout(new BoxLayout(tokenOptions, BoxLayout.X_AXIS));

        JFrame tokenPanelOverview = new JFrame("Pick a Token");

        JLabel pickTokenText = new JLabel();
        pickTokenText.setText("Please pick one of the following options!:");
        
        headerTitle.add(pickTokenText);

        // ADD IMAGES FOR THE DIFFERENT TOKEN OPTIONS 
        for(TokenImage tok : listOfAvailableTokens) { 

            // obtain the address of the token 
            String address = tok.getFileAdress();
            JLabel picOfToken = new JLabel(new ImageIcon(address));
            tokenOptions.add(picOfToken);

        }

        chooseTokenPanel.add(Box.createVerticalStrut(30));
        chooseTokenPanel.add(headerTitle);
        chooseTokenPanel.add(Box.createVerticalStrut(10));
        chooseTokenPanel.add(tokenOptions);

        tokenPanelOverview.add(chooseTokenPanel);
        
        tokenPanelOverview.setLocation(300, 200);
        tokenPanelOverview.setSize(new Dimension(700, 300));

        tokenPanelOverview.setVisible(true);
    }

}
