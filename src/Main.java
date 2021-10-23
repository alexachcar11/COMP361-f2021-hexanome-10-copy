import org.minueto.*;
import org.minueto.handlers.*;
import org.minueto.image.*;
import org.minueto.window.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        // TEMPORARY
        boolean playingElfengold = true;

        // dir containing boot image files
        File bootDir = new File("böppels-and-boots/");

        List<String> bootFileNames = new ArrayList<>();
        // add file names of boot images to the bootFiles list
        for (File file : bootDir.listFiles()) {
            if (file.getName().substring(0, 5).equals("boot-"))
                bootFileNames.add("böppels-and-boots/" + file.getName());
        }

        // make images
        MinuetoImage elfenlandImage;
        MinuetoImage elfengoldImage;
        List<MinuetoImage> bootImages = getBootImages(bootFileNames);

        configImages(bootImages);
        try {
            elfengoldImage = new MinuetoImageFile("elfengold.png");
            elfenlandImage = new MinuetoImageFile("elfenland.png");
        } catch (MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }

        // create player
        List<Player> players = new ArrayList<>();
        Player p1 = new Player(bootImages.get(0), 600 + 20 * (0 % 4), 300 + 20 * (0 / 4));
        Player p2 = new Player(bootImages.get(0), 600 + 20 * (1 % 4), 300 + 20 * (1 / 4));
        players.add(p1);
        players.add(p2);
        boolean starting = true;

        // create window that will contain our game
        MinuetoWindow window = new MinuetoFrame(1024, 768, true);
        MinuetoEventQueue mEventQueue = new MinuetoEventQueue();
        window.registerMouseHandler(new MinuetoMouseHandler() {
            @Override
            public void handleMousePress(int x, int y, int button) {
                System.out.println(x + ", " + y);
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).isTurn) {
                        players.get(i).moveBoot(x, y);
                    }
                }
            }

            @Override
            public void handleMouseRelease(int x, int y, int button) {
            }

            @Override
            public void handleMouseMove(int x, int y) {

            }
        }, mEventQueue);

        // make window visible
        window.setVisible(true);
        // draw on the window
        while (true) {
            if (playingElfengold) {
                window.draw(elfengoldImage, 0, 0);
            } else {
                window.draw(elfenlandImage, 0, 0);
            }
                if (starting)
                {
                    // draw players at starting position
                    for (int i = 0; i < players.size(); i++)
                    {
                        window.draw(players.get(i).getIcon(), players.get(i).getxPos(), players.get(i).getyPos());
                    }
                    players.get(0).isTurn = true;
                } else {
                    for (int i = 0; i < players.size(); i++)
                    {
                        if (players.get(i).isTurn())
                        {
                            window.draw(players.get(i).getIcon(), players.get(i).getxPos(), players.get(i).getyPos());
                        }
                    }
                }


            while (mEventQueue.hasNext()) {
                mEventQueue.handle();
            }

            window.render();
            Thread.yield();
        }
    }




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

    private static void configImages(List<MinuetoImage> pImages) {
        for (int i = 0; i < pImages.size(); i++) {
            pImages.set(i, pImages.get(i).rotate(-90));
            pImages.set(i, pImages.get(i).scale(.125, .125));
        }
    }

}
