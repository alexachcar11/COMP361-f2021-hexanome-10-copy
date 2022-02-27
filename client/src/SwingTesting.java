import javax.swing.*;
import java.awt.*;
import java.awt.Color;

public class SwingTesting {

    public static void main(String[] args) {
        // SWING GUI TESTING STARTS HERE

        // create frame (window)
        JFrame frame = new JFrame("ElfenLand");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




        // buttons
        JButton button1 = new JButton("Button 1");
        //JButton button2 = new JButton(new ImageIcon(ImageIO.read(new File("images/böppels-and-boots/boot-black.png"))));
        JButton button2 = new JButton(new ImageIcon("images/böppels-and-boots/boot-black.png"));
        button2.setOpaque(false);
        button2.setBorderPainted(false);
        button2.setContentAreaFilled(false);
        JButton b=new JButton("click");//creating instance of JButton
        b.setBounds(130,100,100, 40);//x axis, y axis, width, height
        JLabel l1=new JLabel("First Label");
        l1.setBounds(50,50, 100,30);
        JLabel l2=new JLabel("Second Label");
        l2.setBounds(50,100, 100,30);

        // header
        JPanel headerPanel = new JPanel(new GridLayout(1,4,0,0));
        JLabel lobbyNameHeaderLabel = new JLabel("Name");
        JLabel modeHeaderLabel = new JLabel("Mode");
        JLabel sizeHeaderLabel = new JLabel("Size");
        JLabel availableHeaderLabel = new JLabel("Available");
        headerPanel.add(lobbyNameHeaderLabel);
        headerPanel.add(modeHeaderLabel);
        headerPanel.add(sizeHeaderLabel);
        headerPanel.add(availableHeaderLabel);


        // one game
        JPanel oneGamePanel = new JPanel(new GridLayout(1, 4, 0, 0));
        JLabel lobbyNameLabel = new JLabel("Game 1");
        JLabel modeLabel = new JLabel("Elfenland");
        JLabel sizeLabel = new JLabel("1/6");
        JButton joinButton = new JButton("Join");
        oneGamePanel.add(lobbyNameLabel);
        oneGamePanel.add(modeLabel);
        oneGamePanel.add(sizeLabel);
        oneGamePanel.add(joinButton);

        // available games
        JPanel availableGamesPanel = new JPanel(new BorderLayout(0, 10));
        JPanel gamesPanel = new JPanel(new GridLayout(3,4,0,10));
        availableGamesPanel.add(headerPanel, BorderLayout.NORTH);
        availableGamesPanel.add(gamesPanel, BorderLayout.CENTER);
        gamesPanel.add(oneGamePanel);
        gamesPanel.add(button2);

        // navigation
        JPanel navigationPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JButton nextButton = new JButton("Next");
        JButton prevButton = new JButton("Previous");
        navigationPanel.add(prevButton);
        navigationPanel.add(nextButton);
        availableGamesPanel.add(navigationPanel, BorderLayout.SOUTH);

        // create game
        //JPanel createGamePanel = new JPanel(new GridLayout(3,1,0,10));
        JPanel createGamePanel = new JPanel();

        // creating objects of textfield
        TextField t1, t2;
        // instantiating the textfield objects
        // setting the location of those objects in the frame
        t1 = new TextField();
        t1.setBounds(50, 100, 200, 30);
        t2 = new TextField();
        t2.setBounds(50, 150, 200, 30);
        // adding the components to frame
        createGamePanel.add(t1);
        createGamePanel.add(t2);

        // tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(50,100,925,600);
        tabbedPane.add("Join an Existing Game",availableGamesPanel);
        tabbedPane.add("Create a New Game",createGamePanel);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(tabbedPane);

        frame.add(layeredPane);


        // frame
        frame.setSize(1024,768); // set the size of the frame (window)
        frame.setLayout(null);
        frame.setVisible(true); //making the frame (window) visible

        // SWING GUI TESTING ENDS HERE

    }

}
