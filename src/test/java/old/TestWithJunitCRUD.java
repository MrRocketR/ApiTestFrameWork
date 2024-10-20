package old;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestWithJunitCRUD {

    String cookie;
    String header;
    int userAuthCookie;

    @BeforeEach
    public void auth() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .queryParams(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();
        this.cookie = responseGetAuth.getCookie("auth_sid");
        this.header = responseGetAuth.header("x-csrf-token");
        this.userAuthCookie = responseGetAuth.jsonPath().getInt("user_id");
    }

    @Test
    public void authTestWithBeforeAll() {
        JsonPath checkAuth = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookies("auth_sid", cookie)
                .get("https://playground.learnqa.ru/api/user/auth")
                .jsonPath();

        int userIdOnCheck = checkAuth.getInt("user_id");
        assertTrue(userIdOnCheck > 0, "Unexpected user id = " + userIdOnCheck);

        assertEquals(userAuthCookie, userIdOnCheck, "Not Equals");
    }



    public void authTest() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .queryParams(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        Map<String, String> cookies = responseGetAuth.getCookies();
        Headers headers = responseGetAuth.headers();
        int userIdOnAuth = responseGetAuth.jsonPath().getInt("user_id");

        assertEquals(200, responseGetAuth.statusCode());
        assertTrue(cookies.containsKey("auth_sid"), "Response doesn't have 'auth_sid'");
        assertTrue(headers.hasHeaderWithName("x-csrf-token"), "Response doesn't have 'x-csrf-token'");
        assertTrue(userIdOnAuth > 0, "User_id should be grater than 0");

        JsonPath checkAuth = RestAssured
                .given()
                .header("x-csrf-token", headers.getValue("x-csrf-token"))
                .cookies("auth_sid", cookies.get("auth_sid"))
                .get("https://playground.learnqa.ru/api/user/auth")
                .jsonPath();

        int userIdOnCheck = checkAuth.getInt("user_id");
        assertTrue(userIdOnCheck > 0, "Unexpected user id = " + userIdOnCheck);

        assertEquals(userIdOnAuth, userIdOnCheck, "Not Equals");
    }
/**
    @ParameterizedTest
    @ValueSource(strings = {"cookie", "headers"})
    public void authTestWithParams(String condition) throws IllegalAccessException {


        RequestSpecification spec = RestAssured
                .given();
        spec.baseUri("https://playground.learnqa.ru/api/user/auth");
        if (condition.equals("cookie")) {
            spec.cookie("auth_sid", cookie);
        } else if (condition.equals("headers")) {
            spec.header("x-csrf-token", header);
        } else {
            throw new IllegalAccessException("Condition value is known: " + condition);
        }
        JsonPath responseForCheck = spec.get().jsonPath();

        assertEquals(0, responseForCheck.getInt("user_id"), "user_id should be 0 for unath request");
    }
    **/
}
