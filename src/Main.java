// minueto
import org.minueto.*;
import org.minueto.handlers.*;
import org.minueto.image.*;
import org.minueto.window.*;

// music
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// other
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Stack;


public class Main {

    public static void main(String[] args) {

        File bootDir = new File("böppels-and-boots/"); // dir containing boot image files
        List<String> bootFileNames = new ArrayList<>();
        // add file names of boot images to the bootFiles list
        for (File file : bootDir.listFiles()) {
            if (file.getName().startsWith("boot-"))
                bootFileNames.add("böppels-and-boots/" + file.getName());
        }

        // make images
        MinuetoImage elfenlandImage;
        MinuetoImage elfengoldImage;
        List<MinuetoImage> bootImages = getBootImages(bootFileNames);
        MinuetoImage playScreenImage;
        MinuetoImage loginScreenImage;
        MinuetoImage whiteBoxImage;

        configImages(bootImages);
        try {
            elfengoldImage = new MinuetoImageFile("images/elfengold.png");
            elfenlandImage = new MinuetoImageFile("images/elfenland.png");
            playScreenImage = new MinuetoImageFile("images/play.png");
            loginScreenImage = new MinuetoImageFile("images/login.png");
            whiteBoxImage = new MinuetoRectangle(470, 50, MinuetoColor.WHITE, true);
        } catch (MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }

        // Play Music
        playSound("flute.mid");

        // create players
        List<Player> players = new ArrayList<>();
        Player p1 = new Player(bootImages.get(1), 600 + 20 * (0 % 4), 300 + 20 * (0 / 4));
        Player p2 = new Player(bootImages.get(0), 600 + 20 * (1 % 4), 300 + 20 * (1 / 4));
        players.add(p1);
        players.add(p2);

        // create window that will contain our game
        MinuetoWindow window = new MinuetoFrame(1024, 768, true);
        GameWindow gameWindow = new GameWindow(window, GameWindow.Screen.ENTRY);
        window.setMaxFrameRate(60);

        // make window visible
        gameWindow.window.setVisible(true);

        // stack for a word
        Stack<String> writtenWord = new Stack<>();

        // create entry screen mouse handler
        MinuetoEventQueue entryScreenQueue = new MinuetoEventQueue();
        gameWindow.window.registerMouseHandler(new MinuetoMouseHandler() {
            @Override
            public void handleMousePress(int x, int y, int button) {
                // click on Play
                if (x <= 665 && x >= 360 && y >= 345 && y <= 445) {
                    gameWindow.currentlyShowing = GameWindow.Screen.LOGIN;
                }

                // click on Quit
                if (x <= 665 && x >= 360 && y >= 530 && y <= 640) {
                    System.exit(0);
                }
            }

            @Override
            public void handleMouseRelease(int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void handleMouseMove(int i, int i1) {
                // Do nothing
            }
        }, entryScreenQueue);

        // create login screen keyboard and mouse handler
        MinuetoEventQueue loginScreenQueue = new MinuetoEventQueue();

        gameWindow.window.registerKeyboardHandler(new MinuetoKeyboardHandler() {
            private boolean shift = false;

            @Override
            public void handleKeyPress(int i) {
                // press on enter key takes you to the next screen
                if (i == MinuetoKeyboard.KEY_ENTER) {
                    gameWindow.currentlyShowing = GameWindow.Screen.ELFENGOLD;
                } else if (i == MinuetoKeyboard.KEY_SHIFT) {
                    shift = true;
                } else if (i == MinuetoKeyboard.KEY_DELETE) {
                    writtenWord.pop();
                } else if (shift) {
                    writtenWord.push("" + (char) i); // uppercase
                } else {
                    writtenWord.push("" + (char) (i + 32)); // lowercase
                }
            }

            @Override
            public void handleKeyRelease(int i) {
                if (i == MinuetoKeyboard.KEY_SHIFT) {
                    shift = false;
                }
            }

            @Override
            public void handleKeyType(char c) {
                // do nothing
            }
        }, loginScreenQueue);

        gameWindow.window.registerMouseHandler(new MinuetoMouseHandler() {

            private Boolean usernameFilled = false;
            private Boolean passwordFilled = false;

            @Override
            public void handleMousePress(int x, int y, int button) {

                MinuetoFont fontArial20 = new MinuetoFont("Arial", 19, false, false);

                // CLICK INSIDE THE USERNAME BOX
                if (x <= 630 && x >= 160 && y >= 350 && y <= 400) {

                    // cover the last entry
                    loginScreenImage.draw(whiteBoxImage, 160, 350);

                    // type inside the textbox for username
                    String userString = "";
                    while (!writtenWord.empty()) {
                        userString = writtenWord.pop() + userString;
                    }

                    MinuetoImage username = new MinuetoText(userString, fontArial20, MinuetoColor.BLACK);
                    loginScreenImage.draw(username, 200, 360);
                    usernameFilled = true;
                    writtenWord.clear();
                }

                // CLICK INSIDE THE PASSWORD BOX
                if (x <= 630 && x >= 160 && y >= 440 && y <= 495) {

                    // cover the last entry
                    loginScreenImage.draw(whiteBoxImage, 160, 440);

                    // type inside the textbox for password
                    String passString = "";
                    while (!writtenWord.empty()) {
                        passString = writtenWord.pop() + passString;
                    }
                    MinuetoImage password = new MinuetoText(passString, fontArial20, MinuetoColor.BLACK);
                    loginScreenImage.draw(password, 200, 450);
                    passwordFilled = true;
                    writtenWord.clear();
                }

                // CLICK ON THE LOGIN BOX AREA
                if (x <= 235 && x >= 165 && y >= 525 && y <= 550) {

                    // switch the game to playing ElfenGold - can be changed to either
                    if(usernameFilled && passwordFilled) {
                        gameWindow.currentlyShowing = GameWindow.Screen.ELFENGOLD;
                    }

                    else if(usernameFilled  && !passwordFilled) {
                        // no password
                        String passFail = "Please enter a password";
                        MinuetoImage passwordFailed = new MinuetoText(passFail, fontArial20, MinuetoColor.RED);
                        loginScreenImage.draw(passwordFailed, 200, 450);

                    }
                    else if(!usernameFilled && passwordFilled) {
                        // no username
                        String usernameFail = "Please enter a username";
                        MinuetoImage usernameFailed = new MinuetoText(usernameFail, fontArial20, MinuetoColor.RED);
                        loginScreenImage.draw(usernameFailed, 200, 360);

                    }
                    else {
                        String passFail = "Please enter a password";
                        MinuetoImage passwordFailed = new MinuetoText(passFail, fontArial20, MinuetoColor.RED);
                        loginScreenImage.draw(passwordFailed, 200, 450);

                        String usernameFail = "Please enter a username";
                        MinuetoImage usernameFailed = new MinuetoText(usernameFail, fontArial20, MinuetoColor.RED);
                        loginScreenImage.draw(usernameFailed, 200, 360);

                    }
                }

            }

            @Override
            public void handleMouseRelease(int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void handleMouseMove(int i, int i1) {
                // Do nothing
            }
        }, loginScreenQueue);

        // create move boot mouse handler
        MinuetoEventQueue moveBootQueue = new MinuetoEventQueue();
        gameWindow.window.registerMouseHandler(new MinuetoMouseHandler() {
            int ind = 0;    // index of players
            @Override
            public void handleMousePress(int x, int y, int button) {
                // for left click : move boot
                if (button == 1) {
                    players.get(ind).moveBoot(x, y);
                }
                // for right click : change next player
                else if (button == 3) {
                    ind++;
                    if (ind == players.size()) { ind = 0; } // reset index if we reached last player
                }

                /*for (int i = 0; i < players.size(); i++) {
                    // check for player's turn and if button is left click
                    if (players.get(i).isTurn && button == 1) {
                        players.get(i).moveBoot(x, y);
                        break;
                    }
                    // if press right mouse button, change to next player
                    if (button == 3) {
                        // set isTurn to false for current player
                        players.get(i).setTurn(false);

                        // if we reached last player, go back to first player
                        if (i == players.size()-1) {
                            players.get(0).setTurn(true);
                        }
                        else {
                            // change isTurn to true for next player
                            players.get(i + 1).setTurn(true);
                        }
                        break;
                    }
                }*/
            }

            @Override
            public void handleMouseRelease(int x, int y, int button) {
            }

            @Override
            public void handleMouseMove(int x, int y) {

            }
        }, moveBootQueue);

        // draw on the window
        while (true) {
            if (gameWindow.currentlyShowing == GameWindow.Screen.ENTRY) {
                gameWindow.window.draw(playScreenImage, 0, 0);
                while (entryScreenQueue.hasNext()) {
                    entryScreenQueue.handle();
                }
            } else if (gameWindow.currentlyShowing == GameWindow.Screen.LOGIN) {
                gameWindow.window.draw(loginScreenImage, 0, 0);
                while (loginScreenQueue.hasNext()) {
                    loginScreenQueue.handle();
                }

            } else if (gameWindow.currentlyShowing == GameWindow.Screen.ELFENLAND) {
                gameWindow.window.draw(elfenlandImage, 0, 0);
            } else if (gameWindow.currentlyShowing == GameWindow.Screen.ELFENGOLD) {
                gameWindow.window.draw(elfengoldImage, 0, 0);
            }

            if (gameWindow.currentlyShowing == GameWindow.Screen.ELFENLAND
                    || gameWindow.currentlyShowing == GameWindow.Screen.ELFENGOLD) {
                // draw boots
                for (Player player : players) {
                    gameWindow.window.draw(player.getIcon(), player.getxPos(),
                            player.getyPos());
                }
                players.get(0).isTurn = true; // only player 1 can move
                while (moveBootQueue.hasNext()) {
                    moveBootQueue.handle();
                }
            }

            window.render();
            Thread.yield();
        }
    }

    /*
     * @pre: pNames is a list of filenames of the boot images
     * 
     * @return: List of images corresponding to the filenames
     */
    private static List<MinuetoImage> getBootImages(List<String> pNames) {
        List<MinuetoImage> toReturn = new ArrayList<>();
        for (String name : pNames) {
            try {
                toReturn.add(new MinuetoImageFile(name)); // name is null, gives exception
            } catch (MinuetoFileException e) {
                System.out.println("Could not load image file " + name);
                e.printStackTrace();
            }
        }
        return toReturn;
    }

    /*
     * @pre: pImages is a list of MinuetoImages which are boots
     * 
     * @post: contents of pImages are changed to be centered at starting town on
     * game window, and rotated and sized properly
     */
    private static void configImages(List<MinuetoImage> pImages) {
        for (int i = 0; i < pImages.size(); i++) {
            pImages.set(i, pImages.get(i).rotate(-90));
            pImages.set(i, pImages.get(i).scale(.125, .125));
        }
    }

    /**
     * Play Music
     * @param soundFile sound file to play
     */
    static void playSound(String soundFile) {
        File f = new File("./" + soundFile);
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch(Exception e) {
            // TODO: was this catch block intended to be empty?
        }

    }

}
