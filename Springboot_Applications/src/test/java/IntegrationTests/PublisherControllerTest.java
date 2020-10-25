package IntegrationTests;

import common.RestAssuredExtension;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@Tag("IntegrationTests")
public class PublisherControllerTest {

    private RestAssuredExtension restAssuredExtension = new RestAssuredExtension("http://localhost", 9098);

    @DisplayName("Get Publisher Single Param Data")
    @MethodSource("com.publisher_service.publisher.PublisherController#updateNameBySave")
    //@ParameterizedTest(name = "{0} {1}")
    @Test
    public void getSingleParamData() {
        Response response = restAssuredExtension.get("/publisher/put/save?id=1000&name=nitin");
        Assertions.assertEquals(200, response.getStatusCode(), response.getBody().toString());
    }
}
