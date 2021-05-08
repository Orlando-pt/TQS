import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class RestAssuredTest {

    @Test
    void endpointIsAvailableTest() {
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void titleTodoTest() {
        String title = "et porro tempora";

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos")
                .then()
                .body("$.findAll { it.id < 5 }.title", hasItem(title));
    }

}
