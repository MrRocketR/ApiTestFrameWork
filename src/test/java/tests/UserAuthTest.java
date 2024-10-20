package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserAuthTest extends BaseTestCase {

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
        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");
        this.userAuthCookie = this.getIntFromJson(responseGetAuth, "user_id");
    }

    @Test
    public void authTestWithBeforeAll() {
        Response checkAuth = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookies("auth_sid", cookie)
                .get("https://playground.learnqa.ru/api/user/auth")
                .andReturn();

        Assertions.assertJsonByName(checkAuth, "user_id", this.userAuthCookie);
    }

   /** @ParameterizedTest
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
        Response responseForCheck = spec.get().andReturn();

        Assertions.assertJsonByName(responseForCheck, "user_id", 0);
    }
    **/
}