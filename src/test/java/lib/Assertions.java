package lib;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class Assertions {
    public static void assertJsonByName(Response Response, String name, int expectedValue) {
        Response.then().assertThat().body("$", hasKey(name));
        int value = Response.jsonPath().getInt(name);
        assertEquals(expectedValue, value, "ExpectedValue Is not equals to real JSON");
    }

    public static void assertJsonByName(Response Response, String name, String expectedValue) {
        Response.then().assertThat().body("$", hasKey(name));
        String value = Response.jsonPath().getString(name);
        assertEquals(expectedValue, value, "ExpectedValue Is not equals to real JSON");
    }

    public static void assertResponseExpectedTextEquals(Response response, String expectedAnswer) {
                assertEquals(
                        expectedAnswer,
                        response.asString(),
                        "Response Text Not Equals"
                );
    }

    public static void assertResponseExpectedCodeEquals(Response response, int expectedCode) {
        assertEquals(
                expectedCode,
                response.statusCode(),
                "Response StatusCode Not Equals"
        );
    }

    public static void assertJsonHasField(Response Response, String expectedField) {
        Response.then().assertThat().body("$", hasKey(expectedField));
    }

    public static void assertJsonHasNotField(Response Response, String unexpectedFiledName) {
        Response.then().assertThat().body("$", not(hasKey(unexpectedFiledName)));
    }

    public static void assertJsonHasExpectedFieldNames(Response Response, String [] expectedFields) {
        for (String expectedField: expectedFields) {
            Assertions.assertJsonHasField(Response, expectedField);
        }
    }

}
