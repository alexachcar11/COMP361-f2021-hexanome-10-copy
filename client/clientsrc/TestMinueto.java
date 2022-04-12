package clientsrc;

import org.minueto.*;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.*;
import org.minueto.window.*;

import networksrc.Client;
import networksrc.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import serversrc.ServerGame;
import serversrc.Town;

public class TestMinueto {

    public static void main(String[] args) throws IOException {
        // MINUETO TESTING
        MinuetoWindow window;

        MinuetoImage elfenlandImage;
        MinuetoEventQueue eventQueue;

        window = new MinuetoFrame(1024, 768, true);

        try {
            elfenlandImage = new MinuetoImageFile("images/elfenland.png");
        } catch (MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }

        window.setVisible(true);

        window.draw(elfenlandImage, 0, 0);

        ArrayList<Town> towns = new ArrayList<>();

        Town esselen = new Town("Esselen", 38, 103, 99, 152, 4);
        Town yttar = new Town("Yttar", 35, 98, 222, 274, 4);
        Town wylhien = new Town("Wylhien", 187, 234, 30, 75, 3);
        Town parundia = new Town("Parundia", 172, 241, 172, 227, 4);
        Town jaccaranda = new Town("Jaccaranda", 312, 381, 61, 119, 5);
        Town albaran = new Town("AlBaran", 280, 343, 227, 283, 7);
        Town thortmanni = new Town("Throtmanni", 451, 518, 129, 188, 3);
        Town rivinia = new Town("Rivinia", 555, 621, 205, 256, 3);
        Town tichih = new Town("Tichih", 604, 662, 79, 135, 3);
        Town ergeren = new Town("ErgEren", 716, 776, 210, 259, 5);
        Town grangor = new Town("Grangor", 49, 112, 366, 411, 5);
        Town mahdavikia = new Town("MahDavikia", 57, 136, 482, 533, 5);
        Town kihromah = new Town("Kihromah", 164, 223, 314, 367, 6);
        Town ixara = new Town("Ixara", 257, 322, 489, 534, 3);
        Town dagamura = new Town("DagAmura", 281, 339, 345, 394, 4);
        Town lapphalya = new Town("Lapphalya", 415, 482, 383, 437, 2);
        Town feodori = new Town("Feodori", 411, 472, 259, 317, 4);
        Town virst = new Town("Virst", 478, 536, 491, 541, 3);
        Town elvenhold = new Town("Elvenhold", 575, 666, 290, 370, 0);
        Town beata = new Town("Beata", 724, 779, 407, 456, 2);
        Town strykhaven = new Town("Strkhaven", 616, 679, 463, 502, 4);

        towns.add(esselen);
        towns.add(yttar);
        towns.add(wylhien);
        towns.add(parundia);
        towns.add(jaccaranda);
        towns.add(albaran);
        towns.add(thortmanni);
        towns.add(rivinia);
        towns.add(tichih);
        towns.add(ergeren);
        towns.add(grangor);
        towns.add(mahdavikia);
        towns.add(kihromah);
        towns.add(ixara);
        towns.add(dagamura);
        towns.add(lapphalya);
        towns.add(feodori);
        towns.add(virst);
        towns.add(elvenhold);
        towns.add(beata);
        towns.add(strykhaven);

        MinuetoCircle indicator = new MinuetoCircle(10, MinuetoColor.GREEN, true);
        window.draw(indicator, 90, 55);
        window.draw(indicator, 38, 189);
        window.draw(indicator, 169, 126);
        window.draw(indicator, 121, 162);
        window.draw(indicator, 45, 318);
        window.draw(indicator, 78, 307);
        window.draw(indicator, 119, 231);
        window.draw(indicator, 125, 282);
        window.draw(indicator, 246, 130);
        window.draw(indicator, 259, 210);
        window.draw(indicator, 194, 424);
        window.draw(indicator, 165, 510);
        window.draw(indicator, 283, 442);
        window.draw(indicator, 378, 545);
        window.draw(indicator, 279, 57);
        window.draw(indicator, 381, 199);
        window.draw(indicator, 241, 342);
        window.draw(indicator, 354, 401);
        window.draw(indicator, 368, 462);
        window.draw(indicator, 451, 467);
        window.draw(indicator, 563, 431);
        window.draw(indicator, 577, 483);
        window.draw(indicator, 584, 557);
        window.draw(indicator, 728, 489);
        window.draw(indicator, 620, 171);
        window.draw(indicator, 726, 373);
        window.draw(indicator, 635, 252);
        window.draw(indicator, 49, 450);
        window.draw(indicator, 244, 551);
        window.draw(indicator, 621, 423);
        window.draw(indicator, 443, 219);
        window.draw(indicator, 376, 255);
        window.draw(indicator, 302, 311);
        window.draw(indicator, 699, 395);
        window.draw(indicator, 488, 80);
        window.draw(indicator, 383, 131);
        window.draw(indicator, 302, 313);
        window.draw(indicator, 555, 141);
        window.draw(indicator, 717, 291);
        window.draw(indicator, 450, 339);
        window.draw(indicator, 489, 254);
        window.draw(indicator, 526, 390);
        window.draw(indicator, 364, 315);
        window.draw(indicator, 687, 174);
        window.draw(indicator, 510, 310);
        window.draw(indicator, 149, 102);
        window.draw(indicator, 533, 185);
        window.draw(indicator, 438, 549);
        window.draw(indicator, 536, 185);
        window.draw(indicator, 88, 439);

        window.render();

        MinuetoMouseHandler handler = new MinuetoMouseHandler() {

            @Override
            public void handleMouseMove(int arg0, int arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void handleMousePress(int x, int y, int arg2) {
                // Thread testThread = new Thread(() -> System.out.println("Hello"));
                // testThread.start();
                System.out.println("x coord is : " + x + ", and the y coord is : " + y );
                for(Town t: towns){ 
                    if (x < t.getMaxX() && x > t.getMinX() && y < t.getMaxY() && y > t.getMinY()) {
                        // temporary print statement to make sure we're clicking on a specific town
                        System.out.println("Clicking on " + t.getTownName());
                    }
                }
            }

            @Override
            public void handleMouseRelease(int arg0, int arg1, int arg2) {
                // TODO Auto-generated method stub

            }

        };

        eventQueue = new MinuetoEventQueue();
        window.registerMouseHandler(handler, eventQueue);

        while(true){ 
            while (eventQueue.hasNext()) {
                eventQueue.handle();
            }
        }
        // TestServer server = new TestServer();
        // TestClient client = new TestClient();

        // MINUETO TESTING END
    }

}
