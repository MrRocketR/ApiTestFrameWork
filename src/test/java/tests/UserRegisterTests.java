package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserRegisterTests extends BaseTestCase {

    private final String email = "vinkotov@example.com";
    @Test
    public void testCreateUserWithExistingEmail() {
        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);


        Response responseCreateAuth = RestAssured
                .given()
                .body(DataGenerator.getRegistrationData(userData))
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseExpectedCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseExpectedTextEquals(responseCreateAuth, "Users with email '"+ email+ "' already exists");
    }

    @Test
    public void testCreateNewUser() {
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseExpectedCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");
    }
}
