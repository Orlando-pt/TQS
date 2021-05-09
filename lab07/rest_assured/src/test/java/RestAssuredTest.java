import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class RestAssuredTest {
    private static final String URL = "https://jsonplaceholder.typicode.com/todos";

    @Test
    void endpointIsAvailableTest() {

        given()
                .when()
                .get(URL)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void titleTodoTest() {
        String title = "et porro tempora";

        given()
                .when()
                .get(URL + "/4")
                .then()
                .assertThat().statusCode(200)
                .and().body("title", equalTo(title));
    }

    @Test
    void test198And199ToDos() {
        given()
                .when()
                .get(URL)
                .then()
                .assertThat().statusCode(200)
                .and().body("id", hasItems(198, 199));
    }

}
