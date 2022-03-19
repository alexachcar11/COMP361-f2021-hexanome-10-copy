package testsrc;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Queue;

import org.junit.*;

import clientsrc.User;
import networksrc.Action;
import networksrc.Client;
import networksrc.Server;

public class ClientThreadTest {

    private static int testInt = 0;

    @Test
    @SuppressWarnings("unchecked")
    public void oneActionToClient() throws NoSuchFieldException, SecurityException,
            InterruptedException {
        User testUser = new User("owen");
        Client testClient = new Client(Server.LOCATION, Server.PORT, testUser);
        testClient.start();
        Field actionQueue = Client.class.getDeclaredField("actionInQueue");
        actionQueue.setAccessible(true);
        Queue<Action> queue = null;
        try {
            queue = (Queue<Action>) actionQueue.get(testClient);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        queue.add(new Action() {

            @Override
            public void execute() {
                testInt = 69;
            }

        });
        wait(5000);
        assertEquals(69, testInt);
    }
}
