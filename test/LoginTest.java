import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.simple.JSONObject;
import org.junit.*;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class LoginTest {

    // @Test
    // public void testCreateUser() {
    // Class clientMain = ClientMain.class;

    // }

    @Test
    public void testTokenCreation() {
        String token = Registrator.instance().getToken();
        assertNotNull(token);
    }
}
