package old;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsWithJunit {

    @Test
    public void firstJunit200() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/map")
                .andReturn();

        assertEquals(200, response.statusCode(), "Unexpected Status Code");
    }

    @Test
    public void firstJunit404() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/map2")
                .andReturn();

        assertEquals(404, response.statusCode(), "Unexpected Status Code");
    }


    @Test
    public void HelloWithDefaultName() {
        JsonPath jsonPath = RestAssured.get("https://playground.learnqa.ru/api/hello")
                .jsonPath();

        String answer = jsonPath.getString("answer");
        assertEquals("Hello, someone", answer, "Wrong Answer");
    }

    @Test
    public void HelloWithName() {
        String name = "Oleg";
        JsonPath jsonPath = RestAssured
                .given()
                .queryParams("name", name)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();

        String answer = jsonPath.getString("answer");
        assertEquals("Hello, " + name, answer, "Wrong Answer");
    }
/**
    @ParameterizedTest
    @ValueSource(strings = {"", "Peter", "Gray"})
    public void HelloParametrized(String name) {
        Map<String, String> params = new HashMap<>();

        if(name.length() > 0)  {
            params.put("name", name);
        }
        JsonPath jsonPath = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();

        String answer = jsonPath.getString("answer");
        String expected = (name.length() > 0 )? name : "someone";
        assertEquals("Hello, " + expected, answer, "Wrong Answer");
    }
    */
}
