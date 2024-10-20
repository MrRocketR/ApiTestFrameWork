package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Epic("Authorization tests")
@Feature("Authorization")
public class UserGetTests extends BaseTestCase {

    @Test
    public void testGetUserDataNotAuth() {

        Response responseCreateAuth = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        Assertions.assertResponseExpectedCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "username");
        Assertions.assertJsonHasNotField(responseCreateAuth, "firstName");
        Assertions.assertJsonHasNotField(responseCreateAuth, "lastName");
        Assertions.assertJsonHasNotField(responseCreateAuth, "email");
    }

    @Test
    @Description("Использую body с почтой и паролем получить куки и токен.")
    @DisplayName("Авторизация и получения заголовков")
    public void getTestAuthBySameName() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");


       Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        String header = responseGetAuth.header("x-csrf-token");
        String cookie = responseGetAuth.getCookie("auth_sid");

        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookies("auth_sid", cookie)
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        String[] field = {"id", "username", "email", "firstName", "lastName"};
        Assertions.assertJsonHasExpectedFieldNames(responseUserData, field);
    }
}
