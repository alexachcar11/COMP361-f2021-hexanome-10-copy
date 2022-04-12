package clientsrc;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.*;

public class chooseToken {
    
    private ArrayList<TokenSprite> listOfAvailableTokens = new ArrayList<>(); 
    private ClientPlayer player;     

    public chooseToken(ClientPlayer p, ArrayList<TokenSprite> tokensToChooseFrom) throws MinuetoFileException { 

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

        // first token 
        String address1 = listOfAvailableTokens.get(0).getFileAddress();
        JLabel picOfToken1 = new JLabel(new ImageIcon(address1));
        JButton picButton1 = new JButton();
        picButton1.add(picOfToken1);

        // second token 
        String address2 = listOfAvailableTokens.get(0).getFileAddress();
        JLabel picOfToken2 = new JLabel(new ImageIcon(address2));
        JButton picButton2 = new JButton();
        picButton2.add(picOfToken2);
    
        // second token 
        String address3 = listOfAvailableTokens.get(0).getFileAddress();
        JLabel picOfToken3 = new JLabel(new ImageIcon(address3));
        JButton picButton3 = new JButton();
        picButton2.add(picOfToken3);

        // second token 
        String address4 = listOfAvailableTokens.get(0).getFileAddress();
        JLabel picOfToken4 = new JLabel(new ImageIcon(address4));
        JButton picButton4 = new JButton();
        picButton4.add(picOfToken4);

        // second token 
        String address5 = listOfAvailableTokens.get(0).getFileAddress();
        JLabel picOfToken5 = new JLabel(new ImageIcon(address5));
        JButton picButton5 = new JButton();
        picButton5.add(picOfToken5);


        ///////  add buttons to tokenOptions

        tokenOptions.add(picButton1);
        tokenOptions.add(picButton2);
        tokenOptions.add(picButton3);
        tokenOptions.add(picButton4);
        tokenOptions.add(picButton5);


        ////////

        chooseTokenPanel.add(Box.createVerticalStrut(30));
        chooseTokenPanel.add(headerTitle);
        chooseTokenPanel.add(Box.createVerticalStrut(10));
        chooseTokenPanel.add(tokenOptions);

        tokenPanelOverview.add(chooseTokenPanel);
        
        tokenPanelOverview.setLocation(300, 200);
        tokenPanelOverview.setSize(new Dimension(700, 300));

        tokenPanelOverview.setVisible(true);

        ///////// clicking on a token

        picButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent buttonClicked) {
                                
                // If the user clicks on the token, add the token the inventory of the player and close the window
                try {
                    player.addTokenString(listOfAvailableTokens.get(0).getTokenName());
                    tokenPanelOverview.dispose();
                } catch (MinuetoFileException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        picButton2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent buttonClicked) {
                                
                // If the user clicks on the token, add the token the inventory of the player and close the window
                try {
                    player.addTokenString(listOfAvailableTokens.get(1).getTokenName());
                    tokenPanelOverview.dispose();
                } catch (MinuetoFileException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        picButton3.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent buttonClicked) {
                                
                // If the user clicks on the token, add the token the inventory of the player and close the window
                try {
                    player.addTokenString(listOfAvailableTokens.get(2).getTokenName());
                    tokenPanelOverview.dispose();
                } catch (MinuetoFileException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        picButton4.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent buttonClicked) {
                                
                // If the user clicks on the token, add the token the inventory of the player and close the window
                try {
                    player.addTokenString(listOfAvailableTokens.get(3).getTokenName());
                    tokenPanelOverview.dispose();
                } catch (MinuetoFileException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        picButton5.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent buttonClicked) {
                                
                // If the user clicks on the token, add the token the inventory of the player and close the window
                try {
                    player.addTokenString(listOfAvailableTokens.get(4).getTokenName());
                    tokenPanelOverview.dispose();
                } catch (MinuetoFileException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        
    }

}
