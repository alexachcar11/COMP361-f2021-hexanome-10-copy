import org.junit.*;

import kong.unirest.Unirest;

public class LoginTest {

    // @Test
    // public void testCreateUser() {
    // Class clientMain = ClientMain.class;

    // }

    @Test
    public void shouldReturnOK() {
        HttpResponse<JsonNode> jsonResponse = Unirest.get("http://www.mocky.io/v2/5a9ce37b3100004f00ab5154")
                .header("accept", "application/json").queryString("apiKey", "123").asJson();
        assertNotNull(jsonResponse.getBody());
        assertEquals(200, jsonResponse.getStatus());
    }
}
