package testsrc;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Queue;

import org.junit.*;

import clientsrc.Client;
import clientsrc.User;
import networksrc.Action;
import networksrc.Server;

public class ClientThreadTest {

    private static int testInt = 0;
    private static String testString = "";

    @Test
    @SuppressWarnings("unchecked")
    public void oneActionToClient() throws NoSuchFieldException, SecurityException,
            InterruptedException {
        User testUser = new User("owen");
        Client testClient = new Client(Server.LOCATION, Server.PORT, testUser.getName());
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

            @Override
            public boolean isValid() {
                return true;
            }

        });
        Thread.sleep(5000);
        assertEquals(69, testInt);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void twoActionsToClient() throws NoSuchFieldException, SecurityException, InterruptedException {
        User testUser = new User("owen");
        Client testClient = new Client(Server.LOCATION, Server.PORT, testUser.getName());
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

            @Override
            public boolean isValid() {
                return true;
            }

        });
        queue.add(new Action() {

            @Override
            public boolean isValid() {
                return true;
            }

            @Override
            public void execute() {
                testString = testUser.getName();
            }

        });
        Thread.sleep(100);
        assertEquals(69, testInt);
        assertEquals(testUser.getName(), testString);
    }
}
