package testsrc;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.json.simple.parser.ParseException;
import org.junit.*;

import networksrc.Action;
import networksrc.ActionManager;
import networksrc.Client;
import networksrc.DisplayPhaseThreeACK;
import networksrc.Server;
import serversrc.CardType;
import serversrc.Mode;
import serversrc.Player;
import serversrc.Registrator;
import serversrc.ServerGame;
import serversrc.ServerUser;
import serversrc.TownGoldOption;

public class PhaseThreeTest {
    /**
     * Doesn't work because of image creation in instances of travel card and token
     */
    @Test
    public void testTokenPresent() {
        Server server = Server.getInstance();
        ServerGame game = new ServerGame(2, 3, false, false,
                Mode.ELFENLAND, TownGoldOption.NO, "owensgame");
        ServerUser owenUser = null;
        Client testClient = null;
        try {
            testClient = new Client(InetAddress.getLocalHost().toString(), Server.PORT, "owen");
            owenUser = new ServerUser("owen", Registrator.instance().createToken("owen", "abc123-ABC123"));
        } catch (UnknownHostException | ParseException | IllegalAccessException e) {
            e.printStackTrace();
        }
        game.addPlayer(new Player(owenUser, game));
        List<String> tokenStrings = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tokenStrings.add(CardType.values()[ThreadLocalRandom.current().nextInt(0, 7)].toString());
        }
        server.start();
        testClient.start();
        Action phase3 = new DisplayPhaseThreeACK(tokenStrings);
        ActionManager.getInstance().sendToSender(phase3, "owen");
    }
}
